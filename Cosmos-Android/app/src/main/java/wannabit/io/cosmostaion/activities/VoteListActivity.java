package wannabit.io.cosmostaion.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cosmos.distribution.v1beta1.Distribution;
import cosmos.gov.v1beta1.Gov;
import cosmos.params.v1beta1.Params;
import cosmos.upgrade.v1beta1.Upgrade;
import ibc.core.client.v1.Client;
import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.base.BaseActivity;
import wannabit.io.cosmostaion.base.BaseChain;
import wannabit.io.cosmostaion.base.BaseConstant;
import wannabit.io.cosmostaion.model.Proposal_V1;
import wannabit.io.cosmostaion.model.type.Proposal;
import wannabit.io.cosmostaion.model.type.Validator;
import wannabit.io.cosmostaion.task.FetchTask.ProposalTask;
import wannabit.io.cosmostaion.task.TaskListener;
import wannabit.io.cosmostaion.task.TaskResult;
import wannabit.io.cosmostaion.task.V1Task.ProposalsTask_V1;
import wannabit.io.cosmostaion.task.gRpcTask.ProposalsGrpcTask;
import wannabit.io.cosmostaion.utils.WLog;
import wannabit.io.cosmostaion.utils.WUtil;

import static wannabit.io.cosmostaion.base.BaseChain.COSMOS_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.COSMOS_TEST;
import static wannabit.io.cosmostaion.base.BaseChain.IRIS_MAIN;
import static wannabit.io.cosmostaion.base.BaseChain.IRIS_TEST;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_GRPC_FETCH_PROPOSALS;
import static wannabit.io.cosmostaion.base.BaseConstant.TASK_V1_FETCH_PROPOSALS;

public class VoteListActivity extends BaseActivity implements TaskListener {

    private Toolbar             mToolbar;
    private SwipeRefreshLayout  mSwipeRefreshLayout;
    private RecyclerView        mRecyclerView;
    private TextView            mEmptyProposal;
    private VoteAdapter         mVoteAdapter;
    private GrpcProposalsAdapter mGrpcProposalsAdapter;


    private ArrayList<Proposal> mProposals = new ArrayList<>();
    private ArrayList<Gov.Proposal> mGrpcProposals = new ArrayList<>();
    //roll back
    private ArrayList<Proposal_V1> mProposals_V1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_list);
        mToolbar                = findViewById(R.id.tool_bar);
        mSwipeRefreshLayout     = findViewById(R.id.layer_refresher);
        mRecyclerView           = findViewById(R.id.recycler);
        mEmptyProposal          = findViewById(R.id.empty_proposal);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onFetchProposals();
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAccount = getBaseDao().onSelectAccount(getBaseDao().getLastUser());
        mBaseChain = BaseChain.getChain(mAccount.baseChain);
        onFetchProposals();
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

    private void onFetchProposals() {
        if (mAccount == null) return;
        if (mBaseChain.equals(BaseChain.KAVA_MAIN) || mBaseChain.equals(BaseChain.BAND_MAIN) ||
                mBaseChain.equals(BaseChain.CERTIK_MAIN) || mBaseChain.equals(BaseChain.CERTIK_TEST) || mBaseChain.equals(BaseChain.AKASH_MAIN) ||
                mBaseChain.equals(BaseChain.IOV_MAIN) || mBaseChain.equals(BaseChain.SECRET_MAIN)) {
            mVoteAdapter = new VoteAdapter();
            mRecyclerView.setAdapter(mVoteAdapter);
            new ProposalTask(getBaseApplication(), this, mBaseChain).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (mBaseChain.equals(COSMOS_MAIN) || mBaseChain.equals(IRIS_MAIN)) {
            mVoteAdapter = new VoteAdapter();
            mRecyclerView.setAdapter(mVoteAdapter);
            new ProposalsTask_V1(getBaseApplication(), this, mBaseChain).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else if (mBaseChain.equals(COSMOS_TEST) || mBaseChain.equals(IRIS_TEST) ) {
            mGrpcProposalsAdapter = new GrpcProposalsAdapter();
            mRecyclerView.setAdapter(mGrpcProposalsAdapter);
            new ProposalsGrpcTask(getBaseApplication(), this, mBaseChain).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }

    }

    @Override
    public void onTaskResponse(TaskResult result) {
        if(isFinishing()) return;
        if (result.taskType == BaseConstant.TASK_FETCH_ALL_PROPOSAL) {
            mProposals.clear();
            if (result.isSuccess) {
                ArrayList<Proposal> temp = (ArrayList<Proposal>)result.resultData;
                if(temp != null && temp.size() > 0) {
                    mProposals = temp;
                    WUtil.onSortingProposal(mProposals, mBaseChain);
                    mVoteAdapter.notifyDataSetChanged();
                    mEmptyProposal.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    mEmptyProposal.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            } else {
                mEmptyProposal.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }

        } else if (result.taskType == TASK_GRPC_FETCH_PROPOSALS) {
            mGrpcProposals.clear();
            List<Gov.Proposal> temp = (List<Gov.Proposal>)result.resultData;
            if (temp != null && temp.size() > 0) {
                mGrpcProposals.addAll(temp);
                WUtil.onSortingGrpcProposals(mGrpcProposals);
                mGrpcProposalsAdapter.notifyDataSetChanged();
                mEmptyProposal.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

            } else {
                mEmptyProposal.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }

        } else if (result.taskType == TASK_V1_FETCH_PROPOSALS) {
            mProposals_V1.clear();
            List<Proposal_V1> temp = (List<Proposal_V1>)result.resultData;
            if (temp != null && temp.size() > 0) {
                mProposals_V1.addAll(temp);
                WUtil.onSortingProposalsV1(mProposals_V1);
                mVoteAdapter.notifyDataSetChanged();
                mEmptyProposal.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

            } else {
                mEmptyProposal.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);

            }

        }
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.VoteHolder> {
        @NonNull
        @Override
        public VoteAdapter.VoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new VoteAdapter.VoteHolder(getLayoutInflater().inflate(R.layout.item_proposal, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VoteAdapter.VoteHolder voteHolder, int position) {

            if (mBaseChain.equals(COSMOS_MAIN) || mBaseChain.equals(IRIS_MAIN)) {
                final Proposal_V1 proposal = mProposals_V1.get(position);
                voteHolder.proposal_id.setText("# " + proposal.proposal_id);
                voteHolder.proposal_status.setText(proposal.getStatusText(getBaseContext()));
                voteHolder.proposal_title.setText(proposal.content.title);
                voteHolder.proposal_details.setText(proposal.content.description);
                voteHolder.proposal_status_img.setImageDrawable(proposal.getStatusImg(getBaseContext()));
                voteHolder.card_proposal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent webintent = new Intent(VoteListActivity.this, WebActivity.class);
                        webintent.putExtra("voteId", proposal.proposal_id);
                        webintent.putExtra("chain", mAccount.baseChain);
                        startActivity(webintent);
                    }
                });


            } else if (mBaseChain.equals(BaseChain.KAVA_MAIN)) {
                final Proposal proposal = mProposals.get(position);
                voteHolder.proposal_id.setText("# " + proposal.id);
                voteHolder.proposal_status.setText(proposal.proposal_status);
                voteHolder.proposal_title.setText(proposal.content.value.title);
                voteHolder.proposal_details.setText(proposal.content.value.description);
                if (proposal.proposal_status.equals("DepositPeriod")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_deposit_img));
                } else if (proposal.proposal_status.equals("VotingPeriod")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_voting_img));
                } else if (proposal.proposal_status.equals("Rejected")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_rejected_img));
                } else if (proposal.proposal_status.equals("Passed")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_passed_img));
                } else {
                    voteHolder.proposal_status_img.setVisibility(View.GONE);
                }
                voteHolder.card_proposal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(proposal.id) >= 3) {
                            Intent voteIntent = new Intent(VoteListActivity.this, VoteDetailsActivity.class);
                            voteIntent.putExtra("proposalId", proposal.id);
                            startActivity(voteIntent);

                        } else {
                            Intent webintent = new Intent(VoteListActivity.this, WebActivity.class);
                            webintent.putExtra("voteId", proposal.id);
                            webintent.putExtra("chain", mAccount.baseChain);
                            startActivity(webintent);

                        }
                    }
                });

            } else if (mBaseChain.equals(BaseChain.CERTIK_MAIN) || mBaseChain.equals(BaseChain.CERTIK_TEST)) {
                final Proposal proposal = mProposals.get(position);
                voteHolder.proposal_id.setText("# " + proposal.id);
                voteHolder.proposal_status.setText(proposal.proposal_status);
                voteHolder.proposal_title.setText(proposal.content.value.title);
                voteHolder.proposal_details.setText(proposal.content.value.description);
                if (proposal.proposal_status.equals("DepositPeriod")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_deposit_img));
                } else if (proposal.proposal_status.equals("VotingPeriod")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_voting_img));
                } else if (proposal.proposal_status.equals("Rejected")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_rejected_img));
                } else if (proposal.proposal_status.equals("Passed")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_passed_img));
                } else {
                    voteHolder.proposal_status_img.setVisibility(View.GONE);
                }
                voteHolder.card_proposal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent voteIntent = new Intent(VoteListActivity.this, VoteDetailsActivity.class);
                        voteIntent.putExtra("proposalId", proposal.id);
                        startActivity(voteIntent);
                    }
                });

            } else if (mBaseChain.equals(BaseChain.BAND_MAIN) || mBaseChain.equals(BaseChain.IOV_MAIN) || mBaseChain.equals(BaseChain.AKASH_MAIN) || mBaseChain.equals(BaseChain.SECRET_MAIN)) {
                final Proposal proposal = mProposals.get(position);
                voteHolder.proposal_id.setText("# " + proposal.id);
                voteHolder.proposal_status.setText(proposal.proposal_status);
                voteHolder.proposal_title.setText(proposal.content.value.title);
                voteHolder.proposal_details.setText(proposal.content.value.description);
                if (proposal.proposal_status.equals("DepositPeriod")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_deposit_img));
                } else if (proposal.proposal_status.equals("VotingPeriod")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_voting_img));
                } else if (proposal.proposal_status.equals("Rejected")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_rejected_img));
                } else if (proposal.proposal_status.equals("Passed")) {
                    voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_passed_img));
                } else {
                    voteHolder.proposal_status_img.setVisibility(View.GONE);
                }
                voteHolder.card_proposal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent voteIntent = new Intent(VoteListActivity.this, VoteDetailsActivity.class);
                        voteIntent.putExtra("proposalId", proposal.id);
                        startActivity(voteIntent);
                    }
                });

            }
        }

        @Override
        public int getItemCount() {
            if (mBaseChain.equals(COSMOS_MAIN) || mBaseChain.equals(IRIS_MAIN)) {
                return mProposals_V1.size();
            } else {
                return mProposals.size();
            }
        }

        public class VoteHolder extends RecyclerView.ViewHolder {
            private CardView card_proposal;
            private TextView proposal_id, proposal_status, proposal_title, proposal_details;
            private ImageView proposal_status_img;

            public VoteHolder(@NonNull View itemView) {
                super(itemView);
                card_proposal               = itemView.findViewById(R.id.card_proposal);
                proposal_id                 = itemView.findViewById(R.id.proposal_id);
                proposal_status             = itemView.findViewById(R.id.proposal_status);
                proposal_title              = itemView.findViewById(R.id.proposal_title);
                proposal_details            = itemView.findViewById(R.id.proposal_details);
                proposal_status_img         = itemView.findViewById(R.id.proposal_status_img);

            }
        }
    }

    private class GrpcProposalsAdapter extends RecyclerView.Adapter<GrpcProposalsAdapter.VoteHolder> {

        @NonNull
        @Override
        public VoteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            return new GrpcProposalsAdapter.VoteHolder(getLayoutInflater().inflate(R.layout.item_proposal, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VoteHolder voteHolder, int position) {
            final Gov.Proposal proposal = mGrpcProposals.get(position);
            voteHolder.proposal_id.setText("# " + proposal.getProposalId());
            if (proposal.getStatus().equals(Gov.ProposalStatus.PROPOSAL_STATUS_DEPOSIT_PERIOD)) {
                voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_deposit_img));
                voteHolder.proposal_status.setText("DepositPeriod");
            } else if (proposal.getStatus().equals(Gov.ProposalStatus.PROPOSAL_STATUS_VOTING_PERIOD)) {
                voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_voting_img));
                voteHolder.proposal_status.setText("VotingPeriod");
            } else if (proposal.getStatus().equals(Gov.ProposalStatus.PROPOSAL_STATUS_REJECTED)) {
                voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_rejected_img));
                voteHolder.proposal_status.setText("Rejected");
            } else if (proposal.getStatus().equals(Gov.ProposalStatus.PROPOSAL_STATUS_PASSED)) {
                voteHolder.proposal_status_img.setImageDrawable(getResources().getDrawable(R.drawable.ic_passed_img));
                voteHolder.proposal_status.setText("Passed");
            } else {
                voteHolder.proposal_status_img.setVisibility(View.GONE);
                voteHolder.proposal_status.setText("unKnown");
            }

            try {
                if (proposal.getContent().getTypeUrl().equals("/cosmos.gov.v1beta1.TextProposal")) {
                    Gov.TextProposal textProposal = Gov.TextProposal.parseFrom(proposal.getContent().getValue());
                    voteHolder.proposal_title.setText(textProposal.getTitle());
                    voteHolder.proposal_details.setText(textProposal.getDescription());

                } else if (proposal.getContent().getTypeUrl().equals("/cosmos.params.v1beta1.ParameterChangeProposal")) {
                    Params.ParameterChangeProposal parameterChangeProposal = Params.ParameterChangeProposal.parseFrom(proposal.getContent().getValue());
                    voteHolder.proposal_title.setText(parameterChangeProposal.getTitle());
                    voteHolder.proposal_details.setText(parameterChangeProposal.getDescription());

                } else if (proposal.getContent().getTypeUrl().equals("/ibc.core.client.v1.ClientUpdateProposal")) {
                    Client.ClientUpdateProposal clientUpdateProposal = Client.ClientUpdateProposal.parseFrom(proposal.getContent().getValue());
                    voteHolder.proposal_title.setText(clientUpdateProposal.getTitle());
                    voteHolder.proposal_details.setText(clientUpdateProposal.getDescription());

                } else if (proposal.getContent().getTypeUrl().equals("/cosmos.distribution.v1beta1.CommunityPoolSpendProposal")) {
                    Distribution.CommunityPoolSpendProposal communityPoolSpendProposal = Distribution.CommunityPoolSpendProposal.parseFrom(proposal.getContent().getValue());
                    voteHolder.proposal_title.setText(communityPoolSpendProposal.getTitle());
                    voteHolder.proposal_details.setText(communityPoolSpendProposal.getDescription());

                } else if (proposal.getContent().getTypeUrl().equals("/cosmos.upgrade.v1beta1.SoftwareUpgradeProposal")) {
                    Upgrade.SoftwareUpgradeProposal softwareUpgradeProposal = Upgrade.SoftwareUpgradeProposal.parseFrom(proposal.getContent().getValue());
                    voteHolder.proposal_title.setText(softwareUpgradeProposal.getTitle());
                    voteHolder.proposal_details.setText(softwareUpgradeProposal.getDescription());

                } else if (proposal.getContent().getTypeUrl().equals("/cosmos.upgrade.v1beta1.CancelSoftwareUpgradeProposal")) {
                    Upgrade.CancelSoftwareUpgradeProposal cancelSoftwareUpgradeProposal = Upgrade.CancelSoftwareUpgradeProposal.parseFrom(proposal.getContent().getValue());
                    voteHolder.proposal_title.setText(cancelSoftwareUpgradeProposal.getTitle());
                    voteHolder.proposal_details.setText(cancelSoftwareUpgradeProposal.getDescription());
                }

            } catch (Exception e){WLog.w("Ex " +e.getMessage());}

            voteHolder.card_proposal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent webintent = new Intent(VoteListActivity.this, WebActivity.class);
                    webintent.putExtra("voteId", "" + proposal.getProposalId());
                    webintent.putExtra("chain", mAccount.baseChain);
                    startActivity(webintent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mGrpcProposals.size();
        }

        public class VoteHolder extends RecyclerView.ViewHolder {
            private CardView card_proposal;
            private TextView proposal_id, proposal_status, proposal_title, proposal_details;
            private ImageView proposal_status_img;

            public VoteHolder(@NonNull View itemView) {
                super(itemView);
                card_proposal               = itemView.findViewById(R.id.card_proposal);
                proposal_id                 = itemView.findViewById(R.id.proposal_id);
                proposal_status             = itemView.findViewById(R.id.proposal_status);
                proposal_title              = itemView.findViewById(R.id.proposal_title);
                proposal_details            = itemView.findViewById(R.id.proposal_details);
                proposal_status_img         = itemView.findViewById(R.id.proposal_status_img);

            }
        }
    }

}
