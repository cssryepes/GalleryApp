package com.kogi.galleryapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kogi.galleryapp.R;
import com.kogi.galleryapp.domain.entities.Feed;
import com.kogi.galleryapp.ui.FeedActivity;
import com.kogi.galleryapp.ui.fragments.adapters.GridRecyclerViewAdapter;
import com.kogi.galleryapp.ui.listeners.OnFragmentInteractionListener;
import com.kogi.galleryapp.ui.listeners.OnGridFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

public class GridFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private int mColumnCount = 3;
    private OnGridFragmentInteractionListener mGridListener;
    private OnFragmentInteractionListener mInteractionListener;
    private RecyclerView recyclerView;
    private GridRecyclerViewAdapter gridRecyclerViewAdapter;
    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Feed> mFeed;

    public GridFeedFragment() {
    }

    public void notifyDataSetChanged() {
        gridRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void showFeed(int position) {
        if (gridLayoutManager != null) {
            int positionTemp = gridRecyclerViewAdapter.selectedPosition;
            gridRecyclerViewAdapter.selectedPosition = position;
            gridRecyclerViewAdapter.notifyItemChanged(positionTemp);
            gridRecyclerViewAdapter.notifyItemChanged(position);

            gridLayoutManager.scrollToPosition(position);
            gridLayoutManager.smoothScrollToPosition(recyclerView,
                    new RecyclerView.State(), position);
        }
    }

    public static GridFeedFragment newInstance(List<Feed> feed) {
        GridFeedFragment fragment = new GridFeedFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FeedActivity.FEED, (ArrayList<? extends Parcelable>) feed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFeed = getArguments().getParcelableArrayList(FeedActivity.FEED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);

        // Set the adapter
        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            gridRecyclerViewAdapter = new GridRecyclerViewAdapter(mFeed, mInteractionListener);
            gridLayoutManager = new GridLayoutManager(context, mColumnCount);
            recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(gridRecyclerViewAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) view;
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGridFragmentInteractionListener && context instanceof OnFragmentInteractionListener) {
            mGridListener = (OnGridFragmentInteractionListener) context;
            mInteractionListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGridFragmentInteractionListener & OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGridListener = null;
    }

    @Override
    public void onRefresh() {
        mGridListener.onRefreshGrid();
    }

}
