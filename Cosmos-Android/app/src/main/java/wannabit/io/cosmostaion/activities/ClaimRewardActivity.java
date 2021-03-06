package wannabit.io.cosmostaion.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import cosmos.distribution.v1beta1.Distribution;
import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.base.BaseActivity;
import wannabit.io.cosmostaion.base.BaseFragment;
import wannabit.io.cosmostaion.dao.Reward;
import wannabit.io.cosmostaion.fragment.RewardStep0Fragment;
import wannabit.io.cosmostaion.fragment.RewardStep1Fragment;
import wannabit.io.cosmostaion.fragment.RewardStep2Fragment;
import wannabit.io.cosmostaion.fragment.RewardStep3Fragment;
import wannabit.io.cosmostaion.model.Reward_V1;
import wannabit.io.cosmostaion.model.type.Fee;
import wannabit.io.cosmostaion.model.type.Validator;
import wannabit.io.cosmostaion.network.res.ResLcdIrisReward;
import wannabit.io.cosmostaion.task.SingleFetchTask.CheckWithdrawAddressTask;
import wannabit.io.cosmostaion.task.SingleFetchTask.SingleRewardTask;
import wannabit.io.cosmostaion.task.TaskListener;
import wannabit.io.cosmostaion.task.TaskResult;
import wannabit.io.cosmostaion.task.V1Task.AllRewardTask_V1;
import wannabit.io.cosmostaion.task.V1Task.WithdrawAddressTask_V1;
import wannabit.io.cosmostaion.task.gRpcTask.AllRewardGrpcTask;
import wannabit.io.cosmostaion.task.gRpcTask.WithdrawAddressGrpcTask;
import wannabit.io.cosmostaion.utils.WLog;

import static wannabit.io.cosmostaion.base.BaseChain.AKASH_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.BAND_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.CERTIK_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.CERTIK_TEST;
import static wannabit.io.cosmostaion.base.BaseChain.COSMOS_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.COSMOS_TEST;
import static wannabit.io.cosmostaion.base.BaseChain.IOV_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.IOV_TEST;
import static wannabit.io.cosmostaion.base.BaseChain.IRIS_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.IRIS_TEST;
import static wannabit.io.cosmostaion.base.BaseChain.KAVA_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.KAVA_TEST;
import static wannabit.io.cosmostaion.base.BaseChain.SECRET_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.getChain;
import static wannabit.io.cosmostaion.base.BaseConstant.CONST_PW_PURPOSE;
import static wannabit.io.cosmostaion.base.BaseConstant.CONST_PW_TX_SIMPLE_REWARD;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_FETCH_SINGLE_REWARD;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_FETCH_WITHDRAW_ADDRESS;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_GRPC_FETCH_ALL_REWARDS;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_GRPC_FETCH_WITHDRAW_ADDRESS;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_V1_FETCH_ALL_REWARDS;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_V1_FETCH_WITHDRAW_ADDRESS;

public class ClaimRewardActivity extends BaseActivity implements TaskListener {

    private ImageView                   mChainBg;
    private Toolbar                     mToolbar;
    private TextView                    mTitle;
    private ImageView                   mIvStep;
    private TextView                    mTvStep;
    private ViewPager                   mViewPager;
    private RewardPageAdapter           mPageAdapter;

    public String                       mRewardMemo;
    public Fee                          mRewardFee;

    public ArrayList<Validator>         mValidators = new ArrayList<>();
    public ArrayList<Reward>            mRewards = new ArrayList<>();
    public ResLcdIrisReward             mIrisReward;
    public String                       mWithdrawAddress;
    private int                         mTaskCount;

    //V1 .40 version
    public ArrayList<String>            mValOpAddresses_V1;

    //gRPC
    public ArrayList<String>            mValAddresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        mChainBg            = findViewById(R.id.chain_bg);
        mToolbar            = findViewById(R.id.tool_bar);
        mTitle              = findViewById(R.id.toolbar_title);
        mIvStep             = findViewById(R.id.send_step);
        mTvStep             = findViewById(R.id.send_step_msg);
        mViewPager          = findViewById(R.id.view_pager);
        mTitle.setText(getString(R.string.str_reward_c));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIvStep.setImageDrawable(getDrawable(R.drawable.step_4_img_1));
        mTvStep.setText(getString(R.string.str_reward_step_1));

        mAccount = getBaseDao().onSelectAccount(getBaseDao().getLastUser());
        mBaseChain = getChain(mAccount.baseChain);

        if (mBaseChain.equals(COSMOS_MAIN) || mBaseChain.equals(IRIS_MAIN)) {
            mValOpAddresses_V1 = getIntent().getStringArrayListExtra("valOpAddresses");

        } else  if (mBaseChain.equals(COSMOS_TEST) || mBaseChain.equals(IRIS_TEST)) {
            mValAddresses = getIntent().getStringArrayListExtra("valOpAddresses");

        } else {
            mValidators = (ArrayList<Validator>)(getIntent().getSerializableExtra("opAddresses"));
            WLog.w("mValidators : " + mValidators.size());
            if(mValidators.size() < 1) {onBackPressed();}
        }

        mPageAdapter = new RewardPageAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                if(i == 0) {
                    mIvStep.setImageDrawable(getDrawable(R.drawable.step_4_img_1));
                    mTvStep.setText(getString(R.string.str_reward_step_1));

                } else if (i == 1 ) {
                    mIvStep.setImageDrawable(getDrawable(R.drawable.step_4_img_2));
                    mTvStep.setText(getString(R.string.str_reward_step_2));

                } else if (i == 2 ) {
                    mIvStep.setImageDrawable(getDrawable(R.drawable.step_4_img_3));
                    mTvStep.setText(getString(R.string.str_reward_step_3));
                    mPageAdapter.mCurrentFragment.onRefreshTab();

                } else if (i == 3 ) {
                    mIvStep.setImageDrawable(getDrawable(R.drawable.step_4_img_4));
                    mTvStep.setText(getString(R.string.str_reward_step_4));
                    mPageAdapter.mCurrentFragment.onRefreshTab();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        mViewPager.setCurrentItem(0);
        onFetchReward();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAccount == null) finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        onHideKeyboard();
        if(mViewPager.getCurrentItem() > 0) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        } else {
            super.onBackPressed();
        }
    }

    public void onNextStep() {
        if(mViewPager.getCurrentItem() < mViewPager.getChildCount()) {
            onHideKeyboard();
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
        }
    }

    public void onBeforeStep() {
        if(mViewPager.getCurrentItem() > 0) {
            onHideKeyboard();
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
        } else {
            onBackPressed();
        }
    }

    private void onFetchReward() {
        if(mTaskCount > 0) return;
        if (mBaseChain.equals(KAVA_MAIN) || mBaseChain.equals(KAVA_TEST) || mBaseChain.equals(BAND_MAIN) ||
                mBaseChain.equals(IOV_MAIN) || mBaseChain.equals(IOV_TEST) || mBaseChain.equals(CERTIK_MAIN) || mBaseChain.equals(CERTIK_TEST) ||
                mBaseChain.equals(AKASH_MAIN) || mBaseChain.equals(SECRET_MAIN)) {
            mTaskCount = mValidators.size() + 1;
            mRewards.clear();
            for(Validator val:mValidators) {
                new SingleRewardTask(getBaseApplication(), this, mAccount, val.operator_address).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
            new CheckWithdrawAddressTask(getBaseApplication(), this, mAccount).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (mBaseChain.equals(COSMOS_MAIN) || mBaseChain.equals(IRIS_MAIN)) {
            mTaskCount = 2;
            new AllRewardTask_V1(getBaseApplication(), this, mAccount).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new WithdrawAddressTask_V1(getBaseApplication(), this, mAccount).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (mBaseChain.equals(COSMOS_TEST) || mBaseChain.equals(IRIS_TEST)) {
            mTaskCount = 2;
            new AllRewardGrpcTask(getBaseApplication(), this, mBaseChain, mAccount).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new WithdrawAddressGrpcTask(getBaseApplication(), this, mBaseChain,  mAccount).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }

    }


    public void onStartReward() {
        Intent intent = new Intent(ClaimRewardActivity.this, PasswordCheckActivity.class);
        intent.putExtra(CONST_PW_PURPOSE, CONST_PW_TX_SIMPLE_REWARD);
        if (mBaseChain.equals(COSMOS_MAIN) || mBaseChain.equals(IRIS_MAIN)) {
            intent.putExtra("valOpAddresses", mValOpAddresses_V1);
        } else  if (mBaseChain.equals(COSMOS_TEST) || mBaseChain.equals(IRIS_TEST)) {
            intent.putExtra("valOpAddresses", mValAddresses);
        } else {
            intent.putExtra("validators", mValidators);
        }
        intent.putExtra("memo", mRewardMemo);
        intent.putExtra("fee", mRewardFee);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.fade_out);
    }


    @Override
    public void onTaskResponse(TaskResult result) {
//        WLog.w("onTaskResponse " + result.taskType + " " + mTaskCount);
        mTaskCount--;
        if(isFinishing()) return;
        if (result.taskType == TASK_FETCH_SINGLE_REWARD) {
            Reward reward = (Reward)result.resultData;
            if(reward != null)
                onUpdateReward(reward);

        } else if (result.taskType == TASK_FETCH_WITHDRAW_ADDRESS) {
            mWithdrawAddress = (String)result.resultData;
        }

        //roll back
        else if (result.taskType == TASK_V1_FETCH_ALL_REWARDS) {
            if (result.isSuccess) {
                ArrayList<Reward_V1> rewards = (ArrayList<Reward_V1>)result.resultData;
                getBaseDao().mRewards_V1 = rewards;
            }

        } else if (result.taskType == TASK_V1_FETCH_WITHDRAW_ADDRESS) {
            mWithdrawAddress = (String)result.resultData;

        }

        else if (result.taskType == TASK_GRPC_FETCH_ALL_REWARDS) {
            ArrayList<Distribution.DelegationDelegatorReward> rewards = (ArrayList<Distribution.DelegationDelegatorReward>) result.resultData;
            if (rewards != null) { getBaseDao().mGrpcRewards = rewards; }

        } else if (result.taskType == TASK_GRPC_FETCH_WITHDRAW_ADDRESS) {
            mWithdrawAddress = (String)result.resultData;

        }

        if (mTaskCount == 0) {
            mPageAdapter.mCurrentFragment.onRefreshTab();
        }
    }

    private void onUpdateReward(Reward reward) {
        if (mRewards == null) mRewards = new ArrayList<>();
        if (mRewards.size() == 0) {
            mRewards.add(reward);
        } else {
            int match = -1;
            for(int i = 0; i < mRewards.size(); i++) {
                if(mRewards.get(i).validatorAddress.equals(reward.validatorAddress)) {
                    match = i; break;
                }
            }
            if(match > 0) {
                mRewards.remove(match);
            }
            mRewards.add(reward);
        }
    }

    private class RewardPageAdapter extends FragmentPagerAdapter {

        private ArrayList<BaseFragment> mFragments = new ArrayList<>();
        private BaseFragment mCurrentFragment;

        public RewardPageAdapter(FragmentManager fm) {
            super(fm);
            mFragments.clear();
            mFragments.add(RewardStep0Fragment.newInstance(null));
            mFragments.add(RewardStep1Fragment.newInstance(null));
            mFragments.add(RewardStep2Fragment.newInstance(null));
            mFragments.add(RewardStep3Fragment.newInstance(null));
        }

        @Override
        public BaseFragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            if (getCurrentFragment() != object) {
                mCurrentFragment = ((BaseFragment) object);
            }
            super.setPrimaryItem(container, position, object);
        }

        public BaseFragment getCurrentFragment() {
            return mCurrentFragment;
        }

        public ArrayList<BaseFragment> getFragments() {
            return mFragments;
        }

    }
}
