import '@/utils/validate'

export const noblankValidator=(rule, value, callback)=>{
    if(!value || value == ''){
      return callback(new Error("不能为空"));
    }
    return callback();
}
export const numberValidator=(rule, value, callback)=>{
    if(!Number.isInteger(value)){
      return callback(new Error("请输入数字"));
    }
    return callback();
}
export const positiveNumber=(rule, value, callback)=>{
  if(value < 0){
    return callback(new Error("不能小于0"));
  }
  return callback();
}
export const checkNumberValidator = (rule, value, callback) => {
	value = String(value).trim()
	if(value.length == 0) {
		return callback(new Error("请输入数字"));
	}
	if (isNaN(Number(value))) {
		return callback(new Error("请输入数字"));
	}
	if(!Number.isInteger(Number(value))){
		return callback(new Error("请输入整数"));
	}

	return callback();
}

export const checkPositiveNumberValidator = (rule, value, callback) => {
	value = String(value).trim()
	if(value.length == 0) {
		return callback(new Error("请输入数字"));
	}
	if (isNaN(Number(value))) {
		return callback(new Error("请输入数字"));
	}
	if(!Number.isInteger(Number(value))){
		return callback(new Error("请输入正整数"));
	}
	if(Number(value) <= 0){
		return callback(new Error("请输入正整数"));
	}

	return callback();
}

export const checkChinese=(rule, value, callback)=>{
	if(!value || value == '') {
      return callback(new Error("不能为空"));
    }
	if(escape(value).indexOf( "%u" ) >= 0) {
		return callback(new Error("不能包含中文"));
	}
	return callback();
}
export const arrayValidator = (rule, value, callback)=>{
    if(!value.length){
      return callback(new Error("不能为空"));
    }

    return callback();
}

export const choiceArrayValidator = (rule, value,callback) => {
	if(!value.length){
		return callback(new Error("可供选择题目不能为空！"));
	}

	return callback();
}

export const extraOptions = (rule, value, callback) => {
	if (value.length <= 0) {
		return callback(new Error("选项不能为空"));
	} else if (value.length < 2) {
		return callback(new Error("选项必须增加干扰项！"));
	}
	return callback();
}

// 选择项必须选
export const radioValidator = (rule, value, callback) => {
	if(value < 0){
    return callback(new Error("必须选择一项！"));
  }
  return callback();
}


/**
 *
 * @param {object} [options] 选项配置
 * @param {number} [options.max] 最大值
 * @param {number} [options.min] 最小值
 * @param {boolean} [options.isInteger = true] 是否是整数
 * @param {boolean} [options.required = true] 是否不能为空
 * @returns  void
 *
 */
export const numberRangeValidator= (rule,value,callback,options = {}) => {
	const {max, min, isInteger = true, required = true} = options;
    if (required && (value == undefined || value.length == 0)){
		return callback(new Error("不能为空！"));
	}
	if(isNaN(value)){
		return callback(new Error("请输入数字！"));
	}
	if(((min === 200 || min) && (value < min)) || ((max === 200 || max) && (value > max)) ){
		return callback(new Error("请输入有效范围内的数字！"))
	}
	if(isInteger && !Number.isInteger(+value)){
		return callback(new Error("请输入整数！"));
	}
	callback();
}

export const produceLengthValidator = (max,min) => {
	return (rule,value,callback) => {
		if(value >= min && value <= max) {
			callback();
		}else{
			callback(new Error("请输入有效长度！"))
		}
	}
}


// export const wordLengthValidator = (max,min) => {
//     return (rule,value,callback) => {
//         if(value.length() >= min && value.length() <= max) {
//             callback();
//         }else{
//             callback(new Error("输入字数超出范围！"))
//         }
//     }
// }
