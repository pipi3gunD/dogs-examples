package com.eduard.dogs.dogs.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eduard.dogs.DogsApp;
import com.eduard.dogs.R;
import com.eduard.dogs.dogs.activity.MainActivity;
import com.eduard.dogs.dogs.adapter.DogsImgAdapter;
import com.eduard.dogs.dogs.base.BaseFragment;
import com.eduard.dogs.dogs.di.DaggerDogsComponent;
import com.eduard.dogs.dogs.di.PresentationModule;
import com.eduard.dogs.dogs.presenter.DogsDetailPresenter;
import com.eduard.dogs.dogs.presenter.contracts.DogsDetailContract;

import java.util.List;

import javax.inject.Inject;

import static com.eduard.dogs.dogs.constants.DogsConstants.BREED;
import static com.eduard.dogs.dogs.constants.DogsConstants.SUBBREED;

public class DogsDetailFragment extends BaseFragment implements DogsDetailContract.View {

    @Inject
    DogsDetailPresenter presenter;
    @Inject
    DogsImgAdapter imgAdapter;

    public static final String TAG = DogsDetailFragment.class.getSimpleName();
    private String breedName = "";
    private String subbreedName = "";
    private RecyclerView recyclerView;
    private DialogFragment loadingFragment = LoadingDialogFragment.getInstance();

    @Override
    public void onPreparePresenter() {
        attachPresenter(presenter, this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dogs_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgAdapter = new DogsImgAdapter(getActivity());
        recyclerView = view.findViewById(R.id.rv_container_img);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter.getImageDetail(this.breedName, this.subbreedName);
    }

    @Override
    protected void onInjection() {
        DaggerDogsComponent.builder()
                .appComponent(DogsApp.getComponent())
                .presentationModule(new PresentationModule())
                .build()
                .inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String breedName = getArguments().getString(BREED, "");
        String subbreedName = getArguments().getString(SUBBREED, "");

        ActionBar toolbar = ((MainActivity) getActivity()).getSupportActionBar();
        toolbar.setTitle(breedName + " " + subbreedName);
        toolbar.setDisplayHomeAsUpEnabled(true);

        if (breedName != null && !breedName.isEmpty()) {
            this.breedName = breedName;
        }
        if (subbreedName != null && !subbreedName.isEmpty()) {
            this.subbreedName = subbreedName;
        }
    }

    public static DogsDetailFragment newInstance(String breed, String subbreed) {
        DogsDetailFragment breeedDetailFragment = new DogsDetailFragment();
        Bundle args = new Bundle();

        args.putString(BREED, breed);
        args.putString(SUBBREED, subbreed);

        breeedDetailFragment.setArguments(args);
        return breeedDetailFragment;
    }

    @Override
    public void setBreedDetail(final List<String> imgList) {

        recyclerView.setAdapter(imgAdapter);
        imgAdapter.setImgList(imgList);
        imgAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading() {
        if (!loadingFragment.isVisible()) {
            loadingFragment.show(getActivity().getSupportFragmentManager(), "LOADING");
        }
    }

    @Override
    public void hideLoading() {
        if (loadingFragment.isVisible()) {
            loadingFragment.dismiss();
        }
    }

    @Override
    public void showError() {

    }

}
