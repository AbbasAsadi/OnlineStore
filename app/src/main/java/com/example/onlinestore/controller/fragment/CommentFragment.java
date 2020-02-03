package com.example.onlinestore.controller.fragment;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.model.comment.CommentBody;
import com.example.onlinestore.repository.WoocommerceRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    private static final String TAG = "CommentFragment";
    @BindView(R.id.comments_recyclerView_comment_fragment)
    RecyclerView mCommentRecyclerView;
    @BindView(R.id.progress_bar_comment_fragment)
    ProgressBar mProgressBar;
    @BindView(R.id.empty_comment_text_comment_fragment)
    TextView mEmptyCommentText;
    @BindView(R.id.mParent_relativelayout_comment_fragment)
    RelativeLayout mParentRelativeLayout;
    @BindView(R.id.add_comment_fab_comment_fragment)
    FloatingActionButton mAddCommentFab;

    private CommentAdapter mCommentAdapter;
    private int mProductId;
    private List<CommentBody> mCommentList;
    private MutableLiveData<List<CommentBody>> mLiveDataCommentList;

    public CommentFragment() {
        // Required empty public constructor
    }

    private CommentFragment(int productId) {
        mProductId = productId;
    }

    public static CommentFragment newInstance(int productId) {

        Bundle args = new Bundle();

        CommentFragment fragment = new CommentFragment(productId);
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.back_toolbar)
    void onBackPress() {
        getFragmentManager().popBackStack();
    }

    private MutableLiveData<List<CommentBody>> getLiveCommentList() {
        if (mLiveDataCommentList == null) {
            mLiveDataCommentList = new MutableLiveData<>();
        }
        return mLiveDataCommentList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCommentList = new ArrayList<>();
        getLiveCommentList().setValue(mCommentList);
        LoadCommentAsync async = new LoadCommentAsync();
        async.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        mCommentRecyclerView.setVisibility(View.GONE);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        Observer<List<CommentBody>> observer = commentBodies -> {
            updateCommentAdapter();
            Log.d(TAG, "onCreateView : mCommentList.size:" + mCommentList.size());
            Log.d(TAG, "onCreateView : mCommentList.size:" + getLiveCommentList().getValue().size());

        };
        Log.d(TAG, "onCreateView : mCommentList.size:" + mCommentList.size());
        getLiveCommentList().observe(this, observer);
        updateCommentAdapter();

        return view;
    }

    private void updateCommentAdapter() {
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(getLiveCommentList().getValue());
        } else {
            mCommentAdapter.setCommentList(getLiveCommentList().getValue());
            mCommentAdapter.notifyDataSetChanged();
        }
        mCommentRecyclerView.setAdapter(mCommentAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCommentAdapter();
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
        private List<CommentBody> mCommentList;

        CommentAdapter(List<CommentBody> commentList) {
            mCommentList = commentList;
        }

        void setCommentList(List<CommentBody> commentList) {
            mCommentList = commentList;
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CommentViewHolder(LayoutInflater.from(getActivity())
                    .inflate(R.layout.comment_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            holder.mNameReviewer.setText(mCommentList.get(position).getReviewer());
            holder.mDateReview.setText(mCommentList.get(position).getDateCreated());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.mReviewText.setText(Html.fromHtml(mCommentList.get(position).getReview(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.mReviewText.setText(Html.fromHtml(mCommentList.get(position).getReview()));
            }
            String ratingCommentTxt = "امتیاز: " + mCommentList.get(position).getRating() + " از 5";
            holder.mRatingComment.setText(ratingCommentTxt);
            holder.mEditComment.setVisibility(View.GONE);
            holder.mDeleteComment.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return mCommentList.size();
        }

        class CommentViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.name_reviewer)
            TextView mNameReviewer;
            @BindView(R.id.date_review)
            TextView mDateReview;
            @BindView(R.id.review_txt)
            TextView mReviewText;
            @BindView(R.id.rating_comment)
            TextView mRatingComment;
            @BindView(R.id.edit_comment)
            ImageView mEditComment;
            @BindView(R.id.delete_comment)
            ImageView mDeleteComment;

            CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


    class LoadCommentAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mCommentList = WoocommerceRepository.getInstance()
                        .getCommentList(mProductId);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            getLiveCommentList().setValue(mCommentList);
            if (mCommentList == null || mCommentList.isEmpty()) {
                mEmptyCommentText.setVisibility(View.VISIBLE);
                mParentRelativeLayout.setVisibility(View.GONE);
            } else {
                mCommentRecyclerView.setVisibility(View.VISIBLE);
                mParentRelativeLayout.setVisibility(View.VISIBLE);
            }


        }
    }
}
