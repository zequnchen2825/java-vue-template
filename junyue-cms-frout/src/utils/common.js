export const ArrayUtil = {
    /**
     * 数组按对象属性值去重
     * 
     * @param {Array} arr 数组
     * @param {String} name 数组对象属性名
     */
    arrayUnique1 : function (arr, name) {
        var hash = {};
        return arr.reduce(function (item, next) {
            hash[next[name]] ? '' : hash[next[name]] = true && item.push(next);
            return item;
        }, []);
    },
    /**
     * 数组按对象属性值去重
     * 
     * @param {Array} arr 数组
     * @param {Array} name 数组对象属性名
     */
    arrayUnique2 : function (arr, name) {
        const res = new Map();
        return arr.filter((a) => !res.has(a[name]) && res.set(a[name], 1))
    }
      
}