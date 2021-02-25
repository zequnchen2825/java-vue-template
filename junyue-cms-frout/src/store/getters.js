const getters = {
  sidebar: state => state.app.sidebar,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  buttons: state => state.user.buttons,
  userName: state => state.user.userName,
  currentUserId: state => state.user.currentUserId,
  roles: state => state.user.roles,
  permissions: state => state.user.permissions,

  launchModuleData: state => state.launchModule.launchModuleData,

  servingListQuery1: state => state.queryParams.servingListQuery1,
  resignedListQuery1: state => state.queryParams.resignedListQuery1,
  unassignedListQuery1: state => state.queryParams.unassignedListQuery1,
  orderQueryParams:  state => state.queryParams.orderQueryParams,
  launchEvaluate:  state => state.launchModule.launchEvaluate,
}
export default getters
