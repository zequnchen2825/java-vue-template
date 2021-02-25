<!--
 * @Author: NikoZhang
 * @Date: 2019-09-03 11:43:46
 * @LastEditTime: 2019-09-03 14:19:25
 * @LastEditors: NikoZhang
 * @Description: 表格生成器
 -->
<script type="text/jsx">
  // components
  import DropdownRenderer from '../DropdownRenderer';
	import SetHiddenColumnsStateDialog from './components/SetHiddenColumnsStateDialog';
export default {
	name: "TableRenderer",
	components: {
		DropdownRenderer,
		SetHiddenColumnsStateDialog,
	},
	render() {
		const {$attrs, $listeners, dataSource, render} = this;
		return (
			<el-table {...{attrs: $attrs}} {...{on: $listeners}} ref="table" data={dataSource}>
				{render.map((column, index) => {
					// 带slot渲染
					if (column.slot) {
						return (this.$scopedSlots[column.slot])();
					}
					// 带tooltip渲染
					if (column.tooltip) {
						return (
							<el-table-column
								align="center"
								key={index}
								{...{attrs: column}}
								{...{
									scopedSlots: {
										header: () => {
											return (
												<el-tooltip {...{attrs: column.tooltip}}>
													<span>{column.label}</span>
												</el-tooltip>
											);
										}
									}
								}}
							/>
						);
					}
					// 常规渲染
					return <el-table-column align="center" key={index} {...{attrs: column}} />;
				})}
				{/*控制显示隐藏*/}
				<set-hidden-columns-state-dialog
					{...{
						attrs: {
							visible: this.isShow.setHiddenColumnsStateDialog,
							columnsStatus: this.originalColumnsStatus,
							hiddenColumns: this.isShow.hiddenColumnsList,
						}
					}}
					{...{
						on: {
							"update:visible": state => {
								this.isShow.setHiddenColumnsStateDialog = state;
							},
							"hidden-columns-change": hiddenColumnsList => {
								this.renderColumns = this.initDefaultColumnsRender(
									this.columns,
									hiddenColumnsList
								);
							}
						}
					}}
				/>
			</el-table>
		);
	},
	props: {
		// 数据源
		dataSource: {
			type: Array,
			require: true
		},
		// 定义列
		columns: {
			type: Array,
			require: true
		},
		// 默认隐藏列
		hiddenColumns: {
			type: Array,
			require: false
		},
		// 每项过滤
		columnsHandler: {
			type: [Object, Function],
			require: false
		},
		// 追加数据
		appendData: {
			type: [Array, Object],
			require: false
		},
		// 自定义操作栏
		actionBar: {
			type: [Array, Object],
			require: false,
		}
	},
	data() {
		return {
			renderHandler: null,
			actionBarElement: {}, // 操作栏待渲染数据
			originalColumnsStatus: [], // 原始状态
			renderColumns: [], // 待渲染列表
			hiddenColumnsList: [], // 隐藏列表
			isShow: {
				setHiddenColumnsStateDialog: false
			}
		};
	},
	created() {
		this.init();
	},
  computed: {
		render() {
			let {renderColumns} = this;
			const {columnsHandler, appendData, actionBarElement, actionBar} = this;
			// 是否传入columnsHandler
			const haveColumnsHandler = columnsHandler instanceof Object ? Object.keys(columnsHandler).length > 0 : false;
			// 是否传入appendData
			const haveAppendData = Array.isArray(appendData) && appendData.length > 0;

            if (Array.isArray(actionBar) || actionBar instanceof Object) {
                renderColumns = [...renderColumns, actionBarElement];
            }
			// 判断是否追加数据
			if (haveColumnsHandler && haveAppendData) {
				renderColumns = this.tableColumnsHandler(renderColumns, columnsHandler, appendData);
			} else {
				if (haveColumnsHandler) {
					renderColumns = this.tableColumnsHandler(renderColumns, columnsHandler, []);
				}
				if (haveAppendData) {
					renderColumns = this.tableColumnsHandler(renderColumns, {}, appendData);
				}
			}

			return renderColumns;
		},
	},
  methods: {
    setTableDoLayout() {
      this.$nextTick(()=>{
        this.$refs.table.doLayout()
      })
    },
    // 统一初始化入口
    init() {
      let {columns} = this;
      const {hiddenColumns, actionBar, createActionBarElement} = this;
      // 初始化隐藏列默认状态
      this.originalColumnsStatus = this.initColumnsStatus(columns);
      this.hiddenColumnsList = hiddenColumns;

      // 存在actionBar操作栏数据则生渲染元素
      if (Array.isArray(actionBar) || actionBar instanceof Object) {
        this.actionBarElement = createActionBarElement(actionBar);
      }
      // 设置默认隐藏列
      if (Array.isArray(hiddenColumns) && hiddenColumns.length > 0) {
        columns = this.initDefaultColumnsRender(columns, hiddenColumns);
      }
      this.renderColumns = columns;
    },
    /**
     * createActionBarElement
     * 创建操作栏元素
     * @param actionBar <Array|Object>
     * */
    createActionBarElement(actionBar = []) {
      let rendererButton = [];
      // 过滤无权限数据
      if (actionBar.constructor === Object) {
        rendererButton = this.filterHasPermissionButtons(actionBar.buttons);
      }
      if (actionBar.constructor === Array) {
        rendererButton = this.filterHasPermissionButtons(actionBar);
      }
      // 生成按钮项与下拉项数据
      let buttons = [];
      let dropdownList = [];
      let actionBarWith = 150;
      let actionBarLabel = actionBar.label || '操作';
      let actionbarIsFixedRight = actionBar.fixed === false ? '' : 'right';


      buttons = rendererButton.slice(0, 2);
      dropdownList = rendererButton.slice(2, rendererButton.length);
      actionBarWith = actionBar.width || (rendererButton.length === 1 ?
          60 : buttons.length === 2 ?
            110 : actionBarWith
      );

      // 如果待渲染buttons===3或dropdown===false则直接展示，不显示更多下拉
      if (buttons.length + dropdownList.length === 3 || actionBar.isDropdown === false) {
        buttons = rendererButton;
        dropdownList = [];
      }
      return {
        label: actionBarLabel,
        width: actionBarWith,
        fixed: actionbarIsFixedRight,
        ...(actionBar.options || {}),
        formatter: (row, column, cellValue, rowIndex) => {
          return <div>
            {/*按钮组*/}
            {buttons.length > 0 && buttons.map(({label, key, props, handle}) => {
              label = typeof label === "string" ? label : "-";
              props = props instanceof Object ? props : {};
              const customProps =
                typeof handle === "function"
                  ? handle({row, column, cellValue, rowIndex})
                  : {};
              return (
                customProps.visible !== false && (
                  <el-button
                    key={key}
                    type="text"
                    {...{attrs: {...props, ...customProps}}}
                    on-click={event => {
                      this.$emit(
                        "operation-click",
                        key,
                        {row, column, cellValue, rowIndex},
                        event
                      );
                    }}>
                    {label}
                  </el-button>
                )
              );
            })}
            {/*dropdown*/}
            {dropdownList.length > 0 &&
            <el-dropdown
              style={{marginLeft: '4px'}}
              trigger="click"
              on-command={command => {
                this.$emit('operation-click', command, {row, column, cellValue, rowIndex});
              }}
              on-visible-change={status => {
                this.$emit('dropdown-visible-change', status);
              }}>
              <el-button type="text" size="mini" class="el-dropdown-link">
                更多
                <i class="el-icon-arrow-down el-icon--right"/>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                {dropdownList.map(({label, key, props, handle}) => {
                  label = typeof label === 'string' ? label : '-';
                  props = props instanceof Object ? props : {};
                  const customProps = typeof handle === 'function' ? handle({
                    row,
                    column,
                    cellValue,
                    rowIndex
                  }) : {};
                  return customProps.visible !== false && <el-dropdown-item
                    divided={true}
                    key={key}
                    command={key}
                    type="text"
                    {...{attrs: {...props, ...customProps}}}
                  >
                    {label}
                  </el-dropdown-item>
                })}
              </el-dropdown-menu>
            </el-dropdown>}
          </div>;
        }
      };
    },
    /**
     * initColumnsStatus
     * 初始化列表显示状态值
     * @param columns {Array} 待处理的columns
     * @return Object[{key:String,label:String}] {Array}
     * */
    initColumnsStatus(columns) {
      return columns.reduce((result, item) => {
        if (!item.prop) {
          return result;
        }
        return [
          ...result,
          {key: item.prop, label: item.label, disabled: false}
        ];
      }, []);
    },
    /**
     * initDefaultColumnsRender
     * 过滤需要隐藏的columns
     * @param sourceColumns {Array} 原始数据
     * @param hiddenColumns {Array} 待过滤数据
     * @return *[] {Array} 过滤后数据
     * */
    initDefaultColumnsRender(sourceColumns, hiddenColumns = []) {
      return sourceColumns.filter(column => {
        return !hiddenColumns.includes(column.prop);
      });
    },
    /**
     * 表格columns处理方法
     * @param source{Array} <Object> 数据源
     * @param config{{}} 需要处理prop内容
     * @param appendData{Array} <Object> 首尾追加数据，prefix为首部，suffix为尾部
     * @return {Array} <Object>
     */
    tableColumnsHandler(
      source,
      config = {},
      appendData = {
        prefix: [],
        suffix: []
      }
    ) {
      if (!Array.isArray(source)) {
        return [];
      }
      // create hashMap data
      const hashMap = source.reduce((hashMap, item) => ({...hashMap, [item.prop]: item}), {});
      let result = {};

      if (Object.keys(config).length > 0) {
        for (const key in hashMap) {
          // eslint-disable-next-line no-prototype-builtins
          if (hashMap.hasOwnProperty(key)) {
            if (key in config) {
              result[key] = {...hashMap[key], ...config[key]};
            } else {
              result[key] = hashMap[key];
            }
          }
        }
      }


      // 默认尾部追加
      if (Array.isArray(appendData) && appendData.length > 0) {
        console.log(result);
        return [...Object.values(result), ...appendData];
      }
      // 头部追加
      if (Array.isArray(appendData.prefix) && appendData.prefix.length > 0) {
        return [...appendData.prefix, ...Object.values(result)];
      }
      // 尾部追加
      if (Array.isArray(appendData.suffix) && appendData.suffix.length > 0) {
        return [...Object.values(result), ...appendData.suffix];
      }
      return Object.values(result);
    },
    /**
     * 过滤返回只有操作权限的按钮
     * @param buttons{Array} <Object> 待过滤按钮组
     * @return {Array} <Object> 过滤后按钮组
     */
    filterHasPermissionButtons(buttons) {
      return buttons.filter(item => {
        const checkRole = typeof item.role === "undefined" ? true :
          (typeof item.role === 'string' || Array.isArray(item.role)) && this.$_has(item.role);
        return checkRole !== false;
      });
    },
    showHandleHiddenColumnsStateDialog() {
      this.isShow.setHiddenColumnsStateDialog = true;
    },
    setActionBarState(actionBar) {
      this.actionBarElement = this.createActionBarElement(actionBar || this.actionBarElement);
    }
  },
};
</script>
