package wannabit.io.cosmostaion.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;

import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.activities.UndelegateActivity;
import wannabit.io.cosmostaion.base.BaseFragment;
import wannabit.io.cosmostaion.model.type.Coin;
import wannabit.io.cosmostaion.utils.WDp;

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
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_AKASH;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_ATOM;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_BAND;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_CERTIK;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_COSMOS_TEST;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_IOV;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_IOV_TEST;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_IRIS_ATTO;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_IRIS_TEST;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_KAVA;
import static wannabit.io.cosmostaion.base.BaseConstant.TOKEN_SECRET;

public class UndelegateStep0Fragment extends BaseFragment implements View.OnClickListener {

    private Button      mCancel, mNextBtn;
    private EditText    mAmountInput;
    private TextView    mAvailableAmount;
    private TextView    mDenomTitle;
    private ImageView   mClearAll;
    private Button      mAdd01, mAdd1, mAdd10, mAdd100, mAddHalf, mAddMax;
    private BigDecimal  mMaxAvailable = BigDecimal.ZERO;

    public static UndelegateStep0Fragment newInstance(Bundle bundle) {
        UndelegateStep0Fragment fragment = new UndelegateStep0Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_undelegate_step0, container, false);
        mCancel = rootView.findViewById(R.id.btn_cancel);
        mNextBtn = rootView.findViewById(R.id.btn_next);
        mAmountInput = rootView.findViewById(R.id.et_amount_coin);
        mAvailableAmount = rootView.findViewById(R.id.tv_max_coin);
        mDenomTitle = rootView.findViewById(R.id.tv_symbol_coin);
        mClearAll = rootView.findViewById(R.id.clearAll);
        mAdd01 = rootView.findViewById(R.id.btn_add_01);
        mAdd1 = rootView.findViewById(R.id.btn_add_1);
        mAdd10 = rootView.findViewById(R.id.btn_add_10);
        mAdd100 = rootView.findViewById(R.id.btn_add_100);
        mAddHalf = rootView.findViewById(R.id.btn_add_half);
        mAddMax = rootView.findViewById(R.id.btn_add_all);
        mCancel.setOnClickListener(this);
        mNextBtn.setOnClickListener(this);
        mClearAll.setOnClickListener(this);
        mAdd01.setOnClickListener(this);
        mAdd1.setOnClickListener(this);
        mAdd10.setOnClickListener(this);
        mAdd100.setOnClickListener(this);
        mAddHalf.setOnClickListener(this);
        mAddMax.setOnClickListener(this);

        mAmountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable et) {
                String es = et.toString().trim();
                if(es == null || es.length() == 0) {
                    mAmountInput.setBackground(getResources().getDrawable(R.drawable.edittext_box_error));
                } else if (es.startsWith(".")) {
                    mAmountInput.setBackground(getResources().getDrawable(R.drawable.edittext_box));
                    mAmountInput.setText("");
                } else if (es.endsWith(".")) {
                    mAmountInput.setBackground(getResources().getDrawable(R.drawable.edittext_box_error));
                    mAmountInput.setVisibility(View.VISIBLE);
                } else if(es.length() > 1 && es.startsWith("0") && !es.startsWith("0.")) {
                    mAmountInput.setText("0");
                    mAmountInput.setSelection(1);
                }

                if(es.equals("0.000000")) {
                    mAmountInput.setText("0.00000");
                    mAmountInput.setSelection(7);
                } else {
                    try {
                        final BigDecimal inputAmount = new BigDecimal(es);
                        if (BigDecimal.ZERO.compareTo(inputAmount) >= 0 ){
                            mAmountInput.setBackground(getResources().getDrawable(R.drawable.edittext_box_error));
                            return;
                        }
                        BigDecimal checkPosition = inputAmount.movePointRight(6);
                        try {
                            Long.parseLong(checkPosition.toPlainString());
                        } catch (Exception e) {
                            String recover = es.substring(0, es.length() - 1);
                            mAmountInput.setText(recover);
                            mAmountInput.setSelection(recover.length());
                            return;
                        }
                        if(inputAmount.compareTo(mMaxAvailable.movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) {
                            mAmountInput.setBackground(getResources().getDrawable(R.drawable.edittext_box_error));
                        } else {
                            mAmountInput.setBackground(getResources().getDrawable(R.drawable.edittext_box));
                        }
                        mAmountInput.setSelection(mAmountInput.getText().length());
                    } catch (Exception e) { }
                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isAdded() || getSActivity() == null || getSActivity().mAccount == null) getSActivity().onBackPressed();
        WDp.DpMainDenom(getContext(), getSActivity().mAccount.baseChain, mDenomTitle);

        if (getSActivity().mBaseChain.equals(COSMOS_MAIN) || getSActivity().mBaseChain.equals(IRIS_MAIN)) {
            mMaxAvailable = WDp.getDelegation(getBaseDao(), getSActivity().mValOpAddress_V1);
            mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

        } else if (getSActivity().mBaseChain.equals(COSMOS_TEST) || getSActivity().mBaseChain.equals(IRIS_TEST)) {
            mMaxAvailable = getBaseDao().getDelegation(getSActivity().mValOpAddress);
            mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

        } else {
            mMaxAvailable = getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator);
            if (getSActivity().mBaseChain.equals(KAVA_MAIN) || getSActivity().mBaseChain.equals(KAVA_TEST)) {
                mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

            } else if (getSActivity().mBaseChain.equals(BAND_MAIN)) {
                mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

            } else if (getSActivity().mBaseChain.equals(IOV_MAIN) || getSActivity().mBaseChain.equals(IOV_TEST)) {
                mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

            } else if (getSActivity().mBaseChain.equals(CERTIK_MAIN) || getSActivity().mBaseChain.equals(CERTIK_TEST)) {
                mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

            } else if (getSActivity().mBaseChain.equals(SECRET_MAIN)) {
                mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

            } else if (getSActivity().mBaseChain.equals(AKASH_MAIN)) {
                mAvailableAmount.setText(WDp.getDpAmount2(getContext(), mMaxAvailable, 6, 6));

            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.equals(mCancel)) {
            getSActivity().onBeforeStep();

        } else if (v.equals(mNextBtn)) {
            if (isValidateUnDelegateAmount()) {
                getSActivity().onNextStep();
            } else {
                Toast.makeText(getContext(), R.string.error_invalid_amounts, Toast.LENGTH_SHORT).show();
            }

        } else if (v.equals(mAdd01)) {
            BigDecimal existed = BigDecimal.ZERO;
            String es = mAmountInput.getText().toString().trim();
            if(es.length() > 0) {
                existed = new BigDecimal(es);
            }
            mAmountInput.setText(existed.add(new BigDecimal("0.1")).toPlainString());

        } else if (v.equals(mAdd1)) {
            BigDecimal existed = BigDecimal.ZERO;
            String es = mAmountInput.getText().toString().trim();
            if(es.length() > 0) {
                existed = new BigDecimal(es);
            }
            mAmountInput.setText(existed.add(new BigDecimal("1")).toPlainString());

        } else if (v.equals(mAdd10)) {
            BigDecimal existed = BigDecimal.ZERO;
            String es = mAmountInput.getText().toString().trim();
            if(es.length() > 0) {
                existed = new BigDecimal(es);
            }
            mAmountInput.setText(existed.add(new BigDecimal("10")).toPlainString());

        } else if (v.equals(mAdd100)) {
            BigDecimal existed = BigDecimal.ZERO;
            String es = mAmountInput.getText().toString().trim();
            if(es.length() > 0) {
                existed = new BigDecimal(es);
            }
            mAmountInput.setText(existed.add(new BigDecimal("100")).toPlainString());

        } else if (v.equals(mAddHalf)) {
            mAmountInput.setText(mMaxAvailable.divide(new BigDecimal("2000000"), 6, RoundingMode.DOWN).toPlainString());

        } else if (v.equals(mAddMax)) {
            mAmountInput.setText(mMaxAvailable.divide(new BigDecimal("1000000"), 6, RoundingMode.DOWN).toPlainString());

        } else if (v.equals(mClearAll)) {
            mAmountInput.setText("");

        }
    }

    private boolean isValidateUnDelegateAmount() {
        try {
            if (getSActivity().mBaseChain.equals(KAVA_MAIN) || getSActivity().mBaseChain.equals(KAVA_TEST)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_KAVA, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            } else if (getSActivity().mBaseChain.equals(BAND_MAIN)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_BAND, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            } else if (getSActivity().mBaseChain.equals(IOV_MAIN)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_IOV, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            } else if (getSActivity().mBaseChain.equals(IOV_TEST)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_IOV_TEST, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            } else if (getSActivity().mBaseChain.equals(CERTIK_MAIN) || getSActivity().mBaseChain.equals(CERTIK_TEST)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_CERTIK, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            } else if (getSActivity().mBaseChain.equals(SECRET_MAIN)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_SECRET, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            } else if (getSActivity().mBaseChain.equals(AKASH_MAIN)) {
                BigDecimal atomTemp = new BigDecimal(mAmountInput.getText().toString().trim());
                if(atomTemp.compareTo(BigDecimal.ZERO) <= 0) return false;
                if(atomTemp.compareTo(getSActivity().mBondingState.getBondingAmount(getSActivity().mValidator).movePointLeft(6).setScale(6, RoundingMode.DOWN)) > 0) return false;
                Coin coin = new Coin(TOKEN_AKASH, atomTemp.movePointRight(6).setScale(0).toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            }

            else if (getSActivity().mBaseChain.equals(COSMOS_MAIN) || getSActivity().mBaseChain.equals(COSMOS_TEST) || getSActivity().mBaseChain.equals(IRIS_MAIN) || getSActivity().mBaseChain.equals(IRIS_TEST)) {
                BigDecimal userInput = new BigDecimal(mAmountInput.getText().toString().trim()).movePointRight(6).setScale(0);
                if (userInput.compareTo(BigDecimal.ZERO) <= 0) return false;
                if (userInput.compareTo(mMaxAvailable) > 0) return false;
                Coin coin = new Coin(WDp.mainDenom(getSActivity().mBaseChain), userInput.toPlainString());
                getSActivity().mUnDelegateAmount = coin;
                return true;

            }
            return false;

        } catch (Exception e) {
            getSActivity().mUnDelegateAmount = null;
            return false;
        }
    }

    private UndelegateActivity getSActivity() {
        return (UndelegateActivity)getBaseActivity();
    }
}
