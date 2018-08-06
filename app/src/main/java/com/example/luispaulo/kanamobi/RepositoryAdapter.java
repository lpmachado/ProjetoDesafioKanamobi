package com.example.luispaulo.kanamobi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luispaulo.javapopularongithub.R;
import com.example.luispaulo.kanamobi.model.Repository;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private List<Repository> mDataset;

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvRepoName, mTvRepoDescription, mTvRepoOwnerName, mTvRepoForks, mTvRepoStars;
        public ImageView mIvRepoOwner;

        public RepositoryViewHolder(View itemView) {
            super(itemView);
            mTvRepoName = (TextView) itemView.findViewById(R.id.tv_repo_name);
            mTvRepoDescription = (TextView) itemView.findViewById(R.id.tv_repo_description);
            mTvRepoOwnerName = (TextView) itemView.findViewById(R.id.tv_repo_owner_name);
            mTvRepoForks = (TextView) itemView.findViewById(R.id.tv_repo_forks);
            mTvRepoStars = (TextView) itemView.findViewById(R.id.tv_repo_stars);
            mIvRepoOwner = (ImageView) itemView.findViewById(R.id.iv_repo_owner);
        }
    }

    public RepositoryAdapter(List<Repository> dataSet) {
        this.mDataset = dataSet;
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.repository_row, parent, false);
        return new RepositoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        Repository repository = (Repository) mDataset.toArray()[position];

        if(repository != null){
            holder.mTvRepoName.setText(repository.getName());

            Picasso.with(holder.mIvRepoOwner.getContext())
                    .load(repository.getOwner().getAvatarUrl())
                    .resize(32, 32)
                    .into(holder.mIvRepoOwner);

            holder.mTvRepoDescription.setText(repository.getDescription());
            holder.mTvRepoOwnerName.setText(repository.getOwner().getLogin());
            holder.mTvRepoForks.setText(String.valueOf(repository.getForks()));
            holder.mTvRepoStars.setText(String.valueOf(repository.getStars()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
