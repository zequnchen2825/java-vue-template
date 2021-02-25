var now = new Date(); // 当前日期
var nowDayOfWeek = now.getDay(); // 今天本周的第几天
var nowDay = now.getDate(); // 当前日
var nowMonth = now.getMonth(); // 当前月
var nowYear = now.getYear(); // 当前年
nowYear += (nowYear < 2000) ? 1900 : 0;

var lastMonthDate = new Date(); //上月日期
lastMonthDate.setDate(1);
lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
var lastYear = lastMonthDate.getYear();
var lastMonth = lastMonthDate.getMonth();
 
export const DateUtil = {
	/**
	 * 获得当前日期
	 * 
	 * @returns
	 */
	getNowDay : function() {
		return this.formatDate(new Date());
	},
	/**
	 * 获得本周的开始时间
	 * 
	 * @returns
	 */
	getStartDayOfWeek : function() {
		var day = nowDayOfWeek || 7;
		return this.formatDate(new Date(now.getFullYear(), nowMonth, nowDay + 1 - day));
	},
	/**
	 * 获得本周的结束时间
	 * 
	 * @returns
	 */
	getEndDayOfWeek : function() {
		var day = nowDayOfWeek || 7;
		return this.formatDate(new Date(now.getFullYear(), nowMonth, nowDay + 7 - day));
	},
	/**
	 * 获得本月的开始时间
	 * 
	 * @returns
	 */
	getStartDayOfMonth : function() {
		var monthStartDate = new Date(nowYear, nowMonth, 1);
		return this.formatDate(monthStartDate);
	},
	/**
	 * 获得本月的结束时间
	 * 
	 * @returns
	 */
	getEndDayOfMonth : function() {
		var monthEndDate = new Date(nowYear, nowMonth, this.getMonthDays());
		return this.formatDate(monthEndDate);
	},
	//获得上月开始时间
	getLastMonthStartDate : function() {
		var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
		console.log('lastMonthStartDate{}', lastMonthStartDate);
		return this.formatDate(lastMonthStartDate);
	},
	//获得上月结束时间
	getLastMonthEndDate : function() {
		var lastMonthEndDate = new Date(nowYear, lastMonth, this.getMonthDaysOther(lastMonth));
		console.log('lastMonthEndDate{}', lastMonthEndDate)
		return this.formatDate(lastMonthEndDate);
	},
	//获得本季度的开始日期
	getQuarterStartDate : function() {
		var quarterStartDate = new Date(nowYear, this.getQuarterStartMonth(), 1);
		return this.formatDate(quarterStartDate);
	},
	//获得本季度的结束日期
	getQuarterEndDate : function() {
		var quarterEndMonth = this.getQuarterStartMonth() + 2;
		var quarterStartDate = new Date(nowYear, quarterEndMonth, this.getMonthDaysOther(quarterEndMonth));
		return this.formatDate(quarterStartDate);
	},
	/**
	 * 获得本月天数
	 * 
	 * @returns
	 */
	getMonthDays : function() {
		var monthStartDate = new Date(nowYear, nowMonth, 1);
		var monthEndDate = new Date(nowYear, nowMonth + 1, 1);
		var days = (monthEndDate - monthStartDate) / (1000 * 60 * 60 * 24);
		return days;
	},
	//获得某月的天数
	getMonthDaysOther : function(myMonth) {
		var monthStartDate = new Date(nowYear, myMonth, 1);
		var monthEndDate = new Date(nowYear, myMonth + 1, 1);
		var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
		return days;
	},
	//获得本季度的开始月份
	getQuarterStartMonth : function() {
		var quarterStartMonth = 0;
		if(nowMonth<3){
		quarterStartMonth = 0;
		}
		if(2<nowMonth && nowMonth<6){
		quarterStartMonth = 3;
		}
		if(5<nowMonth && nowMonth<9){
		quarterStartMonth = 6;
		}
		if(nowMonth>8){
		quarterStartMonth = 9;
		}
		return quarterStartMonth;
	},
	/**
     * 日期格式化
     * 
	 * @param 日期
	 * @returns {String} yyyy-MM-dd
	 */
	formatDate : function(date) {
		var myyear = date.getFullYear();
		var mymonth = date.getMonth() + 1;
		var myweekday = date.getDate();
 
		if (mymonth < 10) {
			mymonth = "0" + mymonth;
		}
		if (myweekday < 10) {
			myweekday = "0" + myweekday;
		}
		return (myyear + "-" + mymonth + "-" + myweekday);
	},
	/**
	 * 计算两个日期的相差天数
	 * 
	 * @param {string} satrtDate 
	 * @param {string} endDate 
	 */
	getDateDiff : function(satrtDate, endDate) {
		return (Date.parse(endDate) - Date.parse(satrtDate))/(1*24*60*60*1000);
	}
};