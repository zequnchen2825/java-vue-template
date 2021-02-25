const navTab = {
    state: {
        openedTab: [{ name: '首页', path: '/dashboard' }],
        activeTab: { name: '首页', path: '/dashboard' }
    },
    mutations: {
        addTab(state, tabData) {
            state.openedTab.push(tabData)
            if (state.activeTab.name != tabData.name) {
                state.activeTab = tabData;
            }
        },
        changeTab(state, tabData) {
            state.activeTab = tabData;
            // 需要多做一步将储存的数据  query更改
            let pathObj = state.openedTab.find(item => item.path == tabData.path)
            if (pathObj && pathObj.query) {
                pathObj.query = tabData.query
            }
        },
        deductTab(state, tabPath) {
            let index = 0;
            let tabs = state.openedTab;
            if (tabs.length > 0) {
                for (var i = 0; i < tabs.length; i++) {
                    if (tabs[i].path == tabPath) {
                        index = i;
                    }
                }
            }
            state.openedTab.splice(index, 1)
        }
    }
}


export default navTab