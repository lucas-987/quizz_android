package com.example.quizz.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    public NetworkBoundResource() {

    }

    public abstract LiveData<ResultType> loadFromDb();

    public abstract Boolean shouldFetch();

    public abstract RequestType fetchFromApi();

    public abstract void saveFetchResult(RequestType fetchResult);
}
