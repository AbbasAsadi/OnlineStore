package com.example.onlinestore.controller;


import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinestore.R;
import com.example.onlinestore.model.comment.CommentBody;
import com.example.onlinestore.network.WoocommerceRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends Fragment {
    @BindView(R.id.comments_recyclerView)
    RecyclerView commentRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.empty_comment)
    TextView emptyComment;
    @BindView(R.id.parent_relative)
    RelativeLayout parentRelative;
    @BindView(R.id.add_comment_fab)
    FloatingActionButton addCommentFab;

    private WoocommerceRepository mRepository;
    private CommentAdapter mCommentAdapter;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance() {

        Bundle args = new Bundle();

        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepository = WoocommerceRepository.getInstance();
        mRepository.setCommentList(mRepository.getClickedProductId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        ButterKnife.bind(this, view);
        if (mRepository.getCommentList() == null || mRepository.getCommentList().isEmpty()) {
            emptyComment.setVisibility(View.VISIBLE);
            parentRelative.setVisibility(View.GONE);
        } else {
            emptyComment.setVisibility(View.GONE);
            parentRelative.setVisibility(View.VISIBLE);
        }

        updateCommentAdapter();
        progressBar.setVisibility(View.GONE);
        commentRecyclerView.setAdapter(mCommentAdapter);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity() , RecyclerView.VERTICAL , true));

        return view;
    }

    private void updateCommentAdapter() {
        if (mCommentAdapter == null) {
            mCommentAdapter = new CommentAdapter(mRepository.getCommentList());
        }else {
            mCommentAdapter.setCommentList(mRepository.getCommentList());
            mCommentAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        updateCommentAdapter();
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
        private List<CommentBody> mCommentList;

        public CommentAdapter(List<CommentBody> commentList) {
            mCommentList = commentList;
        }

        public List<CommentBody> getCommentList() {
            return mCommentList;
        }

        public void setCommentList(List<CommentBody> commentList) {
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
            holder.nameReviewer.setText(mCommentList.get(position).getReviewer());
            holder.dateReview.setText(mCommentList.get(position).getDateCreated());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.review.setText(Html.fromHtml(mCommentList.get(position).getReview(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.review.setText(Html.fromHtml(mCommentList.get(position).getReview()));
            }
            String ratingCommentTxt = "امتیاز: " + mCommentList.get(position).getRating() + " از 5";
            holder.ratingComment.setText(ratingCommentTxt);
            holder.editComment.setVisibility(View.GONE);
            holder.deleteComment.setVisibility(View.GONE);

        }

        @Override
        public int getItemCount() {
            return mCommentList.size();
        }

        class CommentViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.name_reviewer)
            TextView nameReviewer;
            @BindView(R.id.date_review)
            TextView dateReview;
            @BindView(R.id.review_txt)
            TextView review;
            @BindView(R.id.rating_comment)
            TextView ratingComment;
            @BindView(R.id.edit_comment)
            ImageView editComment;
            @BindView(R.id.delete_comment)
            ImageView deleteComment;

            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

}
