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
import androidx.lifecycle.LiveData;
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

    /*@OnClick(R.id.back_toolbar)
    void onBackClick() {

    }*/

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
        commentRecyclerView.setVisibility(View.GONE);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        Observer<List<CommentBody>> observer = commentBodies -> {
            updateCommentAdapter();
            Log.d(TAG, "onCreateView : mCommentList.size:" + mCommentList.size());
            Log.d(TAG, "onCreateView : mCommentList.size:" + getLiveCommentList().getValue().size());

        };
        Log.d(TAG, "onCreateView : mCommentList.size:" + mCommentList.size());
        getLiveCommentList().observe(this , observer);
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
       commentRecyclerView.setAdapter(mCommentAdapter);
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

        public List<CommentBody> getCommentList() {
            return mCommentList;
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
            progressBar.setVisibility(View.GONE);
            getLiveCommentList().setValue(mCommentList);
            if (mCommentList == null || mCommentList.isEmpty()) {
                emptyComment.setVisibility(View.VISIBLE);
                parentRelative.setVisibility(View.GONE);
            } else {
                commentRecyclerView.setVisibility(View.VISIBLE);
                parentRelative.setVisibility(View.VISIBLE);
            }


        }
    }
}
