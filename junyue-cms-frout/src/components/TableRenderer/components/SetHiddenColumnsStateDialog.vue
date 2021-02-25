<script type="text/jsx">
export default {
  name: "SetHiddenColumnsStateDialog",
    props: {
        columnsStatus: {
            type: Array,
            require: false
        },
        hiddenColumns: {
            type: Array,
            require: false
        }
    },
    data() {
      return {
          hiddenColumnsList: this.hiddenColumns
      }
    },
  render() {
    return (
      <el-dialog
        title="自定义设置列"
        width="540px"
        append-to-body={true}
        {...{attrs: this.$attrs}} {...{on:this.$listeners}}
      >
        <el-transfer
          value={this.hiddenColumnsList}
          on-change={list => {
            this.hiddenColumnsList = list;
          }}
          data={this.columnsStatus}
          titles={["显示列", "隐藏列"]}
          filterable={true}
          size="mini"
          props={{
            key: "prop"
          }}
        />
        <span slot="footer" class="dialog-footer">
          <el-button
            on-click={() => {
              this.hiddenColumnsList = [];
              // this.isShow.setHiddenColumnsStateDialog = false;
                this.$emit("update:visible", false);
            }}>
            取 消
          </el-button>
          <el-button
            type="primary"
            on-click={() => {
              // this.isShow.setHiddenColumnsStateDialog = false;
              this.$emit("update:visible", false);
              this.$emit("hidden-columns-change", this.hiddenColumnsList);
            }}>
            确 定
          </el-button>
        </span>
      </el-dialog>
    );
  }
};
</script>
