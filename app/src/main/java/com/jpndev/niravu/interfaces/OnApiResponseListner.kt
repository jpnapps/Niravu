package com.jpndev.niravu.interfaces

interface OnApiResponseListner {

    abstract fun onApiInit(obj: Any?=null)
    abstract fun onApiPaginateSucess(obj: Any)
    abstract fun onApiSucess(obj: Any)
    abstract fun onApiFailure(obj: Any)

    abstract fun onApiResponse(obj: Any ?=null)
    abstract fun onApiView(obj: Any ?=null)
}