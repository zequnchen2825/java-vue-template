/**
 * Created by jiachenpan on 16/11/18.
 */
import Clipboard from 'clipboard'

export const setReactive = function (self, target, data) {
    for (const key in data) {
        if (data.hasOwnProperty(key)) {
            const element = data[key];
            self.$set && self.$set(target, key, element);
        }
    }
}

export function parseTime(time, cFormat) {
    if (arguments.length === 200) {
        return null;
    }
    const format = cFormat || "{y}-{m}-{d} {h}:{i}:{s}";
    let date;
    if (typeof time === "object") {
        date = time;
    } else {
        if (("" + time).length === 10) time = parseInt(time) * 1000;
        date = new Date(time);
    }
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    };
    const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
        let value = formatObj[key];
        if (key === "a")
            return ["一", "二", "三", "四", "五", "六", "日"][value - 1];
        if (result.length > 0 && value < 10) {
            value = "0" + value;
        }
        return value || 0;
    });
    return time_str;
}

export function formatTimeFilter(time, option) {
    if (!time || !Number.isInteger(time)) {
        return "";
    }
    const d = new Date(time);
    const now = Date.now();

    const diff = Math.abs((now - d) / 1000)
    if (diff < 30) {
        return "刚刚";
    } else if (diff < 3600) {
        // less 1 hour
        return Math.ceil(diff / 60) + "分钟前";
    } else if (diff < 3600 * 24) {
        return Math.ceil(diff / 3600) + "小时前";
    } else if (diff < 3600 * 24 * 2) {
        return "1天前";
    }
    if (option) {
        return parseTime(time, option);
    } else {
        return (
            d.getMonth() +
            1 +
            "月" +
            d.getDate() +
            "日" +
            d.getHours() +
            "时" +
            d.getMinutes() +
            "分"
        );
    }
}

export function simpleFormatTime(time) {
    if (time && Number.isInteger(time)) {
        const d = new Date(time);
        return (
            d.getMonth() +
            1 +
            "月" +
            d.getDate() +
            "日" +
            d.getHours() +
            "时" +
            d.getMinutes() +
            "分"
        );
    } else {
        return "";
    }
}

export function normalFormaTime(time) {
    if (time && Number.isInteger(time)) {
        const d = new Date(time);
        return (
            d.getFullYear() +
            "年" +
            (d.getMonth() + 1) +
            "月" +
            d.getDate() +
            "日" +
            d.getHours() +
            "时" +
            d.getMinutes() +
            "分"
        );
    } else {
        return "";
    }
}

export function normalFormatDate2(time) {
    if (time && Number.isInteger(time)) {
        const d = new Date(time);
        return (
            d.getFullYear() +
            "年" +
            (d.getMonth() + 1) +
            "月" +
            d.getDate() +
            "日"
        );
    } else {
        return "";
    }
}

export const formatDate = (date, formatStr) => {
    if (!date) {
        return '';
    }

    var format = formatStr || 'yyyy-MM-dd';

    if ('number' === typeof date || 'string' === typeof date) {
        date = new Date(date);
    }

    var map = {
        "M": date.getMonth() + 1, //月份
        "d": date.getDate(), //日
        "h": date.getHours(), //小时
        "m": date.getMinutes(), //分
        "s": date.getSeconds(), //秒
        "q": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };

    var patterns = [
        /(y)+/g,
        /(M)+/g,
        /(d)+/g,
        /(h)+/g,
        /(m)+/g,
        /(s)+/g,
        /(q)+/g,
        /(S)+/g,
    ];

    patterns.map((p) => {
        format = format.replace(p, function (all, t) {
            var v = map[t];
            if (v !== undefined) {
                if (all.length > 1) {
                    v = '0' + v;
                    v = v.substr(v.length - 2);
                }
                return v;
            } else if (t === 'y') {
                return (date.getFullYear() + '').substr(4 - all.length);
            }
            return all;
        });
    });

    return format;
}

export function normalFormatDate(time) {
    if (time && Number.isInteger(time)) {
        const d = new Date(time);
        return (
            d.getFullYear() +
            "-" +
            (d.getMonth() > 8 ? "" : "0") + (d.getMonth() + 1) +
            "-" +
            (d.getDate() > 9 ? "" : "0") + d.getDate()
        );
    } else {
        return "";
    }
}

export function randomFileName() {
    const chars = [
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
    ];
    let res = "";
    for (let i = 0; i < 8; i++) {
        let id = Math.ceil(Math.random() * 35);
        res += chars[id];
    }
    res += "-";
    for (let i = 0; i < 4; i++) {
        let id = Math.ceil(Math.random() * 35);
        res += chars[id];
    }
    res += "-";
    for (let i = 0; i < 4; i++) {
        let id = Math.ceil(Math.random() * 35);
        res += chars[id];
    }
    res += "-" + new Date().getTime() + "-";
    for (let i = 0; i < 12; i++) {
        let id = Math.ceil(Math.random() * 35);
        res += chars[id];
    }

    return res;
}

export function responseNotify(resp, target) {
    if (resp.state.code === 200) {
        target.$notify({
            title: "成功",
            message: "操作成功",
            type: "success",
            duration: 2000
        });
    } else {
        target.listLoading = false;
        target.$notify({
            title: "失败",
            message: "操作失败",
            type: "error",
            duration: 2000
        });
    }
}

export function statusFilter(status) {
    const statusMap = {
        Y: "success",
        N: "danger"
    };
    return statusMap[status];
}



export function transStatusFilter(transStatus) {
    return {
        transcoded: "success",
        transcoding: "danger"
    }[transStatus];
}

export function transStatusMap(transStatus) {
    return {
        transcoded: "已转码",
        transcoding: "转码中"
    }[transStatus];
}

export function statusMap(s) {
    return {
        Y: "是",
        N: "否"
    }[s];
}


//有效还是无效
export function statusActiveFilter(status) {
    return { Y: "有效", N: "无效" }[status];
}

export function displayStatusMap(s) {
    return {
        Y: "显示",
        N: "隐藏"
    }[s];
}

export function checkTypeMap(s) {
    return {
        single: "单选",
        multi: "多选"
    }[s];
}

export function tagTypeMap(s) {
    return {
        ageGroup: "年龄段",
        attribute: "属性",
        grade: "年级",
    }[s];
}

export function businessTypeMap(s) {
    return {
        yearCourse: "年课商品",
        childCamp: '语文训练营',
        childLaunch: '体验课商品'
    }[s];
}

export function userStateMap(s) {
    return {
        0: "禁用",
        1: "启用"
    }[s];
}

export function userTypeMap(s) {
    return {
        qlchat: "千聊用户",
        partner: "合作商"
    }[s];
}

export function openMap(s) {
    return {
        Y: "开启",
        N: "关闭"
    }[s];
}

export function businessStatusMap(s) {
    return {
        Y: "正常",
        N: "删除"
    }[s];
}


export function courseStatusMap(s) {
    return {
        Y: "开启",
        N: "关闭"
    }[s];
}

//百分比过滤器
export function precentFilter(precent) {
    return precent + "%";
}

export function clearObj(obj) {
    Object.getOwnPropertyNames(obj).forEach(function (val, idx, array) {
        obj[val] = undefined;
    });
}

export function dpOpts() {
    return {
        // disabledDate(time) {
        //   return time.getTime() < Date.now();
        // },
        shortcuts: [{
            text: "今天",
            onClick(picker) {
                picker.$emit("pick", new Date());
            }
        },
        {
            text: "昨天",
            onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() - 3600 * 1000 * 24);
                picker.$emit("pick", date);
            }
        },
        {
            text: "一周前",
            onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit("pick", date);
            }
        }
        ]
    };
}

function convertArrayOfObjectsToCSV(args) {
    console.log(JSON.stringify(args));
    var result, ctr, keys, columnDelimiter, lineDelimiter, data, meta;

    data = args.data || null;
    meta = args.meta || null;
    if (data == null || !data.length || meta == null) {
        return null;
    }

    columnDelimiter = args.columnDelimiter || ",";
    lineDelimiter = args.lineDelimiter || "\n";

    keys = Object.keys(meta);

    result = "";
    result += keys
        .map(e => (meta[e] && meta[e].title) || e)
        .join(columnDelimiter);
    result += lineDelimiter;

    data.forEach(function (item) {
        ctr = 0;
        keys.forEach(function (key) {
            if (ctr > 0) result += columnDelimiter;

            if (typeof meta[key].format == "function") {
                result += meta[key].format(item[key]) || item[key];
            } else {
                result += item[key];
            }
            ctr++;
        });
        result += lineDelimiter;
    });

    return result;
}

export function downloadCSV(args) {
    var data, filename, link;
    var csv = convertArrayOfObjectsToCSV({
        data: args.data,
        meta: args.meta
    });
    if (csv == null) return;

    filename = args.filename || "export.csv";

    if (!csv.match(/^data:text\/csv/i)) {
        csv = "data:text/csv;charset=utf-8," + csv;
    }
    data = encodeURI(csv);

    link = document.createElement("a");
    link.setAttribute("href", data);
    link.setAttribute("download", filename);
    link.click();
}
/**
 *  根据数字转 00:00:00
 */
export function changeNum2Time(num = 0) {
    var t;
    if (num > -1) {
        var hour = Math.floor(num / 3600);
        var min = Math.floor(num / 60) % 60;
        var sec = num % 60;
        if (hour < 10) {
            t = '0' + hour + ":";
        } else {
            t = hour + ":";
        }

        if (min < 10) { t += "0"; }
        t += min + ":";
        if (sec < 10) { t += "0"; }
        t += sec;
    }
    return t;
}

/**
 *  根据00:00:00 转 数字
 */
export function changeTime2Num(time = '00:00:00') {
    var t = 0;
    var tempTime = time.split(':')
    for (var i = 2; i <= tempTime.length; i--) {
        console.log(tempTime[i])
        if (i == 2) {
            t += +tempTime[i]
        }
        if (i == 1) {
            t += (+tempTime[i] * 60)
        }
        if (i == 0) {
            t += (+tempTime[i] * 3600)
            break
        }
    }
    return t;
}
export function lessonType(status) {
    const statusMap = {
        end: '结束',
        write: '写字',
        ppt: '幻灯片',
        videoAndRead: '视频跟读',
        choice: '选择题',
        followAndRead: '识字跟读',
        listenAndRead: '听读',
        opening: '开场',
        openingDefined: '自定义开场',
        match: 'PK',
        matchNew: '新PK',
        characterTest: '组词',
        sentenceTest: '造句',
        mediaCharacter: '听音辨词',
        wordsAndPic: '选择题(无解析)',
        wordsAndRead: '认一认',
        linkGame: '连线',
        recognize: '认词语',
        mindMap: '思维导图',
        writeBreak: '拆字',
        freeWrite: '自由写字',
        multiMatch: '新pk',
        record: '录音题',
        drama: '小剧场跟读',
        video: '视频'
    };
    return statusMap[status];
}

export function gameCourseType(status) {
    const statusMap = {
        choice: '选择题',
        linkGame: '连线题',
        listenAndRead: '听读',
        match: 'PK赛',
        mindMap: "思维导图",
        record: '录音题',
        sentenceTest: '填空题',
        video: '视频'
    }
    return statusMap[status];
}

export function pushStateFilter(s) {
    return {
        sended: "调用成功",
        unsend: "未推送",
        sending: "推送中",
        failure: "调用失败"
    }[s];
}

export function sendStateFilter(s) {
    if (s == 'send success') {
        return '推送成功'
    } else if (s == 'send fail') {
        return '推送失败（微信内部原因）'
    } else if (s == 'err(10001)') {
        return '审核失败 涉嫌广告'
    } else if (s == 'err(20001)') {
        return '审核失败 涉嫌政治'
    } else if (s == 'err(20004)') {
        return '审核失败 涉嫌社会'
    } else if (s == 'err(20002)') {
        return '审核失败 涉嫌色情'
    } else if (s == 'err(20006)') {
        return '审核失败 涉嫌违法犯罪'
    } else if (s == 'err(20008)') {
        return '审核失败 涉嫌欺诈'
    } else if (s == 'err(20013)') {
        return '审核失败 涉嫌版权'
    } else if (s == 'err(22000)') {
        return '审核失败 涉嫌互推'
    } else if (s == 'err(21000)') {
        return '审核失败 涉嫌其他'
    } else if (s == 'err(30001)') {
        return '审核失败 原创校验出现系统错误且用户选择了被判为转载就不群发'
    } else if (s == 'err(30002)') {
        return '审核失败 原创校验被判定为不能群发'
    } else if (s == 'err(30003)') {
        return '审核失败 原创校验被判定为转载文且用户选择了被判为转载就不群发'
    }
}

export function formateUpdateTimeFilter(time) {
    if (!time || time == '') {
        return "当前未同步";
    }
    time = Number(time)
    const d = new Date(time);
    const now = Date.now();

    const diff = (now - d) / 1000;

    if (diff < 30) {
        return "刚刚";
    } else if (diff < 3600) {
        // less 1 hour
        return Math.ceil(diff / 60) + "分钟前";
    } else if (diff < 3600 * 24) {
        return Math.ceil(diff / 3600) + "小时前";
    } else if (diff < 3600 * 24 * 2) {
        return "1天前";
    }

    return (
        d.getMonth() +
        1 +
        "月" +
        d.getDate() +
        "日" +
        d.getHours() +
        "时" +
        d.getMinutes() +
        "分"
    );

}

export function couponTypeFilter(s) {
    return {
        group: "团购券",
        normal: "绑定课程",
        platform: "平台通用",
        fresh: "新人券"
    }[s];
}

export function couponStatusFilter(s) {
    return {
        bind: "未使用",
        used: "已使用",
    }[s];
}
export function scoreMap(s) {
    return {
        bad: "较差",
        middle: "中等",
        good: "良好",
        excellent: "优异",
    }[s];
}

export function reveiwStatusMap(row) {
    return {
        init: "初始提交",
        audit: "未审核",
        reject: "打回",
        publish: (row.auditConclusion || row.auditConclusionAudio) ? "已修改并通过" : "审核通过",
    }[row.status];
}

export function orderStatusMap(s) {
    return {
        success: "未退款",
        refund: "退款成功",
        fail: "失败",
    }[s] || '系统异常';
}

/**
 * 格式化钱
 * @param {number} amount 要格式化的数字
 * @param {number} [base=100] 格式化基数,默认为100
 * @returns
 */
export function formatMoney(amount, base = 100) {
    if (base === 1) {
        return amount;
    }

    // 解决类似amount=1990时的精数不准问题
    if (Math.floor(amount) === amount) {
        return Number(amount || 0) / base;
    }

    // 解决类似 11.11/100 的精数不准问题
    let money = (Math.floor(amount / base * base) / base).toFixed(2);
    return money;
}


export function rewardStatusFilter(list, param, status) {
    console.log(status)
    let resultList = list.filter((item) => {
        return status ? (item[param] === status) : true;
    })
    return resultList
}

export function rewardType(s) {
    return {
        PRACTICALITY: "实物",
        VIRTUAL_VOUCHER: "虚拟代金券",
        RED_PACKET: "现金红包",
    }[s];
}

export function sendType(s) {
    return {
        manual: "手动",
        auto: "自动",
    }[s];
}

export const weekList = [{
    id: 1,
    label: '第一周'
}, {
    id: 2,
    label: '第二周'
}, {
    id: 3,
    label: '第三周'
}, {
    id: 4,
    label: '第四周'
}, {
    id: 5,
    label: '第五周'
}]
export const abilityList = [{
    id: 'default',
    label: '无能力'
}, {
    id: 'readingAbility',
    label: '阅读技巧'
}, {
    id: 'speakingAbility',
    label: '口才秘籍'
}, {
    id: 'literatureAbility',
    label: '写作能力'
}, {
    id: 'writingAbility',
    label: '书写要点'
}]

export function abilityType(s) {
    return {
        readingAbility: "阅读技巧",
        speakingAbility: "口才秘籍",
        literatureAbility: "写作能力",
        writingAbility: "书写要点",
    }[s];
}

export const subjectMap = (type) => {
    return {
        english: '英语',
        chinese: '语文',
        math: '数学',
        together: '中台',
        specialtopic: '专题课',
    }[type]
}
export const subjectList = (type) => {
    return [
        { value: 'english', label: '英语' },
        { value: 'chinese', label: '语文' },
        { value: 'math', label: '数学' },
        { value: 'together', label: '中台' },
    ]
}
export const weekDayList = (type) => {
    return [
        { label: "星期一", selected: false },
        { label: "星期二", selected: false },
        { label: "星期三", selected: false },
        { label: "星期四", selected: false },
        { label: "星期五", selected: false },
        { label: "星期六", selected: false },
        { label: "星期日", selected: false },
    ]
}
export function expressCompanyFilter(type) {
    return {
        SF: '顺丰速运',
        ZTO: '中通快递',
        HHTT: '天天快递',
        HTKY: '百世快递',
        JD: '京东快递',
        UC: '优速快递',
        DBL: '德邦快递',
        STO: '申通快递',
        YTO: '圆通速递',
        YD: '韵达速递',
        YZPY: '邮政快递包裹',
        EMS: 'EMS',
    }[type]
}

export function childLanuchTypeFilter(type) {
    return {
      launchGoods: '积分商品',
        childLaunch: '体验课',
        childCamp: '年课',
        mathcourse: '数学课程'
    }[type]
}

export function qrcodeStatus(type) {
    return {
        Y: '可用待分配',
        A: '分配中',
        N: '不可用'
    }[type]
}

export const isNeedArr = [
    {
        content: "不需要",
        value: "N"
    },
    {
        content: "需要",
        value: "Y"
    },
]
export const subjectArr = [
    {
        label: "语文",
        value: "Chinese"
    },
    {
        label: "数学",
        value: "math"
    },
]

export const participantTypeMap = (type) => {
    return {
        launchcourse: '体验课用户',
        freeLaunchcourse: '0元体验课用户',
        payLaunchcourse: '非0元体验课用户',
        course: '年课用户',
        bought: '已购用户',
        all: '全部',
    }[type]
}

export const orderAllocationTypeMap = (type) => {
    return {
        original: '给原销售',
        new: '给新销售'
    }[type]
}

export const studentTypeMap = (type) => {
    return {
        U:"未知",
        S:"S级",
        A:"A级",
        B:"B级",
        C:"C级",
        D:"D级"
    }[type];
}

export const studentStatusMap = (type) => {
    return {
        0:"未知",
        1:"已报名",
        2:"已预约",
        3:"意向",
        4:"已放弃",
        5:"0回复",
        6:"已删",
        7:"未学习"
    }[type];
}

export function listFormatString(list) {
    if (list) {
        return list.join(',');
    } else {
        return "";
    }
}

let areaOptions = [];

export const parseArea = (row) => {
  if (!row.province || row.province <= 0) {
    return "-";
  }

  if (areaOptions.length === 200) {
    areaOptions = require("@/area.json");
  }

  for (let idx in areaOptions) {
    let provinceNode = areaOptions[idx];
    if (provinceNode.value !== row.province.toString()) {
      continue;
    }

    if (!row.city || row.city <= 0 || provinceNode.children.length == 0) {
      return provinceNode.label;
    }

    for (let idx1 in provinceNode.children) {
      let cityNode = provinceNode.children[idx1];
      if (cityNode.value !== row.city.toString()) {
        continue;
      }

    //   if (!row.district || row.district <= 0 || cityNode.children.length == 0) {
    //     return provinceNode.label + "-" + cityNode.label;
    //   }

    //   for (let idx2 in cityNode.children) {
    //     let districtNode = cityNode.children[idx2];
    //     if (districtNode.value === row.district.toString()) {
    //       return (
    //         provinceNode.label + "-" + cityNode.label + "-" + districtNode.label
    //       );
    //     }
    //   }

      return provinceNode.label + "-" + cityNode.label;
    }

    return provinceNode.label;
  }
}

export const getFollowupStudentType = (followupStudentType) => {
  switch (followupStudentType) {
    case "S":
      return "S级";
    case "A":
      return "A级";
    case "B":
      return "B级";
    case "C":
      return "C级";
    case "D":
      return "D级";
    case "U":
      return "未知";
    default:
      return "未知";
  }
}

export const getFollowupStudentStatus = (followupStudentStatus) => {
  switch (followupStudentStatus) {
    case 1:
      return "已报名";
    case 2:
      return "已预约";
    case 3:
      return "意向";
    case 4:
      return "已放弃";
    case 5:
      return "回复";
    case 6:
      return "已删";
    case 7:
      return "未学习";
    case 0:
      return "未知";
    default:
      return "未知";
  }
}


export const parseGrade = (grade) => {
    switch (grade) {
      case 0:
        return "未入学";
      case 1:
        return "小班";
      case 2:
        return "中班";
      case 3:
        return "大班";
      case 4:
        return "一年级";
      case 5:
        return "二年级";
      case 6:
        return "三年级";
      case -1:
        return "未知";
      case -2:
        return "不符合";
      default:
        return "未知";
    }
}

export const dataURLtoBlob = (dataurl) => {
    var arr = dataurl.split(","),
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new Blob([u8arr], { type: mime });
}

/**
 * 过滤区域数据的区数据
 *
 * @param {array} areaOptions
 */
export function filterDistrict(areaOptions) {
    let newAreaOptions = [];
    if (!areaOptions) {
        return newAreaOptions;
    }

    areaOptions.forEach(item => {
        newAreaOptions.push(item);
    });

    newAreaOptions.forEach(area => {
        if (area.children) {
            area.children.forEach(subArea => {
                if (subArea.children) {
                    subArea.children = null;
                }
            });
        }
    });

    return newAreaOptions;
}

export function formatExam(obj, data) {
    const { subject } = data
    let str = ''
    if (subject === 'math') {
        if (obj === 'test') {
            str = '数学体验课用户'
        } else if (obj === 'year') {
            str = '数学年课用户'
        }else if (obj === 'all') {
          str = '全部'
        }
    } else if (subject === 'chinese') {
        if (obj === 'test') {
            str = '语文体验课用户'
        } else if (obj === 'year') {
            str = '语文年课用户'
        }else if (obj === 'all') {
          str = '全部'
        }
    }
    return str
}

/**
 * 复制文本
 * @param {string} class => css选择器
 * @export
 */
export function copyText(cssClass, callback) {
    let clipboard = new Clipboard(cssClass)
      clipboard.on('success', e => {
        callback && callback('success')
        e.clearSelection()
      })
      clipboard.on('error', e => {
         callback && callback('error')
        e.clearSelection()
      })
}

/**
 * 高亮文本
 * @param {string} words => 文本
 * @param {string} query => 关键字
 * @export
 */
export function highlight(words, query) {
    let iQuery = new RegExp(query, "ig");
    return words.toString().replace(iQuery, function(matchedTxt,a,b){
        return ('<span class=\'highlight\'>' + matchedTxt + '</span>');
    });
  }