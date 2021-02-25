var NavTabMixin = {
    data() {
        return {
            openedTab: []
        }
    },
    mounted() {
        this.openedTab = this.$store.state.navTab.openedTab || [];
    },
    methods: {
        clickMenuToNavTab(params) {
            let { path, name, query } = params
            let openTab = this.openedTab.find(item => item.path == path)
            this.$store.commit(!openTab ? 'addTab' : 'changeTab', { name, path, query })
            this.$router.push({
                path,
                query
            });
        }
    },
}
export default NavTabMixin
