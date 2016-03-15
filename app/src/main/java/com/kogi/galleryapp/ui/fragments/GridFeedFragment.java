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

import java.util.ArrayList;
import java.util.List;

public class GridFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String FEED = "FEED";
    private int mColumnCount = 3;
    private OnGridFragmentInteractionListener mListener;
    private GridRecyclerViewAdapter gridRecyclerViewAdapter;
    private  SwipeRefreshLayout swipeRefreshLayout;
    private List<Feed> mFeed;

    public GridFeedFragment() {
    }

    public void notifyDataSetChanged() {
        gridRecyclerViewAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public static GridFeedFragment newInstance(List<Feed> feed) {
        GridFeedFragment fragment = new GridFeedFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(FEED, (ArrayList<? extends Parcelable>) feed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFeed = getArguments().getParcelableArrayList(FEED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);

        // Set the adapter
        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            gridRecyclerViewAdapter = new GridRecyclerViewAdapter(mFeed, mListener);

            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            recyclerView.setAdapter(gridRecyclerViewAdapter);

            swipeRefreshLayout = (SwipeRefreshLayout) view;
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGridFragmentInteractionListener) {
            mListener = (OnGridFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        mListener.onRefreshGrid();
    }

}
