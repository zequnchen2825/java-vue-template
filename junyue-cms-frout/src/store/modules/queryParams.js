const queryParams = {
    state: {
        servingListQuery1: {},
        resignedListQuery1: {},
        unassignedListQuery1: {},
        orderQueryParams: {},
    },
    mutations: {
        changeParams(state, moduleData) {
            state.servingListQuery1 = moduleData.servingListQuery;
            state.resignedListQuery1 = moduleData.resignedListQuery;
            state.unassignedListQuery1 = moduleData.unassignedListQuery;
        },
        changeOrderQueryParams(state, moduleData) {
            state.orderQueryParams = moduleData.orderQueryParams;
        },
    }
}


export default queryParams
