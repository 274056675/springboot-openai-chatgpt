

//******************************** Input框 正则表达式验证 **********************************

//inpu框，只能输入数字和小数点，【两位小数，正数】
export const clearNum = (obj) => {
  obj.value = obj.value.replace(/[^\d.]/g, '').replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');

}

//inpu框，只能输入数字和小数点，【三位小数，正数】
export const clearNumThreeDecimal = (obj) => {
  obj.value = obj.value.replace(/[^\d.]/g, '').replace(/^(\-)*(\d+)\.(\d\d\d).*$/, '$1$2.$3');

}


//inpu框，只能输入数字和小数点，【两位小数，正、负数】
export const clearNumMin = (obj) => {
  obj.value = obj.value.replace(/[^-*\d.]/g, '').replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3');

}
//inpu框，只能输入数字和小数点，【两位小数，正、负数】
export const clearNumMinNoPer = (obj) => {
  obj.value = obj.value.replace(/[^-*\d.]/g, '').replace(/^(\-)*(\d+)\.(\d+).*$/, '$1$2.$3');

}

//inpu框，只能输入数字，【正整数和零】
export const clearNumIntegar = (obj) => {
  obj.value = obj.value.replace(/\D/g, '');
}

//inpu框，只能输入数字，【正整数（大于零）】
export const clearNumPosIntegar = (obj) => {
  if (obj.value.length == 1) {
    obj.value = obj.value.replace(/[^1-9]/g, '');
  }
  else {
    obj.value = obj.value.replace(/\D/g, '');
  }
}

//inpu框，只能输入数字，【正整数（大于零）】
export const clearNumPosIntegarOne = (obj) => {
  if (obj.value.length == 1) {
    obj.value = obj.value.replace(/[^1-9]/g, '1');
  }
  else {
    obj.value = obj.value.replace(/\D/g, '1');
  }
}

//inpu框，只能输入数字英文
export const clearNumCap = (obj) => {
  obj.value = obj.value.replace(/[^A-Z0-9]/g, '');
}

export const clearNumCap2 = (obj) => {
  obj.value = obj.value.replace(/[^a-zA-Z0-9]/g, '');
}
//只能输入数字英文中文
export const clearSpecialAll = (obj) => {
  obj.value = obj.value.replace(/[^\u4E00-\u9FA5A-Za-z0-9]/g, '');
}
//inpu框,不能输入特殊字符
export const clearSpecial = (obj) => {
  obj.value = obj.value.replace(/[\。\，\、\’\；\：\“\”\_\‘\’\！\,\.\!\|\~\`\(\)\#\@\%\+\=\/\'\$\%\^\&\*\{\}\:\;\"\<\>\?\\]/g, '');
}
//不能输入英文双引号
export const CheckContent = (obj) => {
  obj.value = obj.value.replace(/[\"]/g, '');
}
export const clearInputAll = (obj) => {
  obj.value = '';
}
//********************************** 为空和NULL的处理 ****************************

//为空数值处理
export const IsNullEmptyNum = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "null" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return 0;
  }
  return str;
}

//为空文字处理
export const IsNullEmpty = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "null" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str;
}

//时间为空处理（精确到：分）
export const IsNullEmptyDataTime = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str.replace('T', ' ').substring(0, 16);
}

//时间为空处理（精确到：秒）
export const IsNullEmptyDataTimeSecond = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str.replace('T', ' ').substring(0, 19);
}

//时间为空处理（精确到：天）
export const IsNullEmptyDataTimeDay = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str.replace('T', ' ').substring(0, 10);
}
//时间为空处理（精确到：天 空格）
export const IsNullEmptyDataTimeDayKg = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  var aa = str.replace('T', ' ').substring(0, 10);
  aa = aa.replace(/-/g, '  ');
  return aa;
}
//时间为空处理（精确到：天 空格1）
export const IsNullEmptyDataTimeDayKg1 = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str.replace('T', ' ').substring(0, 10).replace(/-/g, '  ');
}
//时间为空处理（精确到：月）
export const IsNullEmptyDataTimeMonth = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str.replace('T', ' ').substring(0, 7);
}

//时间为空处理（精确到：年）
export const IsNullEmptyDataTimeYear = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }
  return str.substring(0, 4);
}

//时间为空处理（精确到：月-日-小时-分，格式：MM-dd HH:mm）
export const IsNullEmptyDataTimeMonthMinute = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }

  return str.replace('T', ' ').substring(5, 16);
}
//时间为空处理（精确到：小时-分，格式：HH:mm）
export const IsNullEmptyDataTimeHoureMinute = (str, nullRtnStr, isRtnStr) => {
  if (str == null || str == "" || str == "undefined" || str == undefined) {
    if (isRtnStr) {
      return nullRtnStr;
    }
    return "";
  }

  return str.replace('T', ' ').substring(11, 16);
}

export const timeSplictYMD = (startDate) => {
  var times = IsNullEmptyDataTimeDay(startDate);
  if (times != "") {
    var startTimeList = times.split("-");
    if (startTimeList.length == 3) {
      times = startTimeList[0] + "年" + startTimeList[1] + "月" + startTimeList[2] + "日";
    }
  }
  return times;
}
//创建唯一字符串
export const newGuid = () => {
  //var guid = "";
  //var n = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  //for (var i = 1; i <= 8; i++) {
  //    guid += n;
  //}
  //return guid;

  var guid = "";
  for (var i = 1; i <= 32; i++) {
    var n = Math.floor(Math.random() * 16.0).toString(16);
    guid += n;
    //if (isMiddline && (i == 8 || i == 12 || i == 16 || i == 20)) {
    //    guid += "-";
    //}
  }
  return guid;

}


//邮箱验证
export const fChkMail = (szMail) => {
  var szReg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
  //var szReg = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
  //var szReg = /w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/;

  var bChk = szReg.test(szMail);
  return bChk;
}

//去除字符串两端空格
export const StrTirm = (Str) => {
  //.replace(/^\s+|\s+$/g, "")
  if (Str.length > 0) {
    return Str.replace(/^\s+|\s+$/g, "");
  }
  return Str;
}

/******************* 身份证号验证 Start ***********************/
//身份证号码验证
export const isIdCardNo = (num) => {
  num = num.toUpperCase();
  var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
  var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
  var varArray = new Array();
  var intValue;
  var lngProduct = 0;
  var intCheckDigit;
  var intStrLen = num.length;
  var idNumber = num;
  // initialize
  if ((intStrLen != 15) && (intStrLen != 18)) {
    return false;
  }
  // check and set value
  for (i = 0; i < intStrLen; i++) {
    varArray[i] = idNumber.charAt(i);
    if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
      return false;
    } else if (i < 17) {
      varArray[i] = varArray[i] * factorArr[i];
    }
  }
  if (intStrLen == 18) {
    //check date
    var date8 = idNumber.substring(6, 14);
    if (isDate8(date8) == false) {
      return false;
    }
    // calculate the sum of the products
    for (i = 0; i < 17; i++) {
      lngProduct = lngProduct + varArray[i];
    }
    // calculate the check digit
    intCheckDigit = parityBit[lngProduct % 11];
    // check last digit
    if (varArray[17] != intCheckDigit) {
      return false;
    }
  }
  else {       //length is 15
    //check date
    var date6 = idNumber.substring(6, 12);
    if (isDate6(date6) == false) {
      return false;
    }
  }
  return true;
}
export const isDate6 = (sDate) => {
  if (!/^[0-9]{6}$/.test(sDate)) {
    return false;
  }
  var year, month, day;
  year = sDate.substring(0, 4);
  month = sDate.substring(4, 6);
  if (year < 1700 || year > 2500) return false
  if (month < 1 || month > 12) return false
  return true
}

export const isDate8 = (sDate) => {
  if (!/^[0-9]{8}$/.test(sDate)) {
    return false;
  }
  var year, month, day;
  year = sDate.substring(0, 4);
  month = sDate.substring(4, 6);
  day = sDate.substring(6, 8);
  var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
  if (year < 1700 || year > 2500) return false
  if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
  if (month < 1 || month > 12) return false
  if (day < 1 || day > iaMonthDays[month - 1]) return false
  return true
}

/******************** 身份证号验证 END ******************************/


/**---------金额大写转换（转化为中文）------------**/
export const ToCapitalized = (n) => { //金额大写转换函数
  if (n <= 0) {
    return "";
  }
  var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
  n += "00";
  var p = n.indexOf('.');
  if (p >= 0)
    n = n.substring(0, p) + n.substr(p + 1, 2);
  unit = unit.substr(unit.length - n.length);
  for (var i = 0; i < n.length; i++) {

    str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
  }
  return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}

export const ToCapitalizedDe = (n) => { //金额大写转换函数
  if (n <= 0) {
    return "";
  }
  var str = "";
  n += "00";
  var p = n.indexOf('.');
  if (p >= 0)
    n = n.substring(0, p) + n.substr(p + 1, 2);
  for (var i = 0; i < (9 - n.length); i++) {
    str += '零   ';
  }
  for (var i = 0; i < n.length; i++) {

    str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + '   ';
  }
  return str;
}
export const ToCapitalizedCb = (n) => { //金额大写转换函数
  if (n <= 0) {
    return "";
  }
  var dxMongy = "";
  var str = "";
  n += "00";
  var p = n.indexOf('.');
  if (p >= 0)
    n = n.substring(0, p) + n.substr(p + 1, 2);
  for (var i = 0; i < (8 - n.length); i++) {
    dxMongy += '零';
  }
  for (var i = 0; i < n.length; i++) {

    dxMongy += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i));// + '     ';
  }

  for (var i = 0; i < dxMongy.length; i++) {
    if (i < 5) {
      str += dxMongy[i] + '    ';
    } else {
      str += dxMongy[i] + '     ';
    }
  }

  return str;
}



//手机和电话验证
export const IsTelPhone = (str) => {
  var mobile = /^1[3-9]+\d{9}$/;
  var phone = /^(0\d{2,3})?-?\d{7,8}$/;
  if (mobile.test(str) || phone.test(str)) {
    return true;
  } else {
    return false;
  }
}

//表号验证 （数字英文横杠）
export const IsMeterNo = (str) => {
  var regx = /^[A-Za-z0-9/-]+$/;
  if (regx.test(str)) {
    return true;
  } else {
    return false;
  }
}


export const IsNumber = (val) => {
  var regPos = /^\d+(\.\d+)?$/; //非负浮点数
  if (regPos.test(val)) {
    return true;
  } else {
    return false;
  }
}

//开始时间 stime
//结束时间 etime
export const CheckTimeOneYear = (stime, etime) => {
  var timemils = Date.parse(delimiterConvert(etime)) - Date.parse(delimiterConvert(stime)); //开始时间到结束时间的时间差。
  if (timemils < 0) {
    return false;
  }
  var yearmils = (1000 * 60 * 60 * 24 * 366);//一年的时间差
  if (timemils > yearmils) {

    return false;
  }
  return true;
}
export const delimiterConvert = (val) => {  //格式话数据
  return val.replace('-', '/').replace('-', '/')
}
//获取时间 
//时间 PassTime 无值时，去当前时间
//时间格式 FormatType 0 yyyy-MM-dd,1 yyyy-MM-dd HH:mm:ss
//差距年份 YearNum 0是当前年 1是加1年 -1 是减1年
///getFormatDate("",0,-1);//前面一年的时间
///getFormatDate("", 0, 0);//当前时间
export const getFormatDate = (PassTime, FormatType, YearNum) => {
  var nowDate = new Date();
  if (IsNullEmpty(PassTime) != "") {
    nowDate = new Date(PassTime);
  }
  var year = nowDate.getFullYear() + parseInt(IsNullEmptyNum(YearNum));
  var month = nowDate.getMonth() + 1 < 10 ? "0" + (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
  var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate.getDate();
  var hour = nowDate.getHours() < 10 ? "0" + nowDate.getHours() : nowDate.getHours();
  var minute = nowDate.getMinutes() < 10 ? "0" + nowDate.getMinutes() : nowDate.getMinutes();
  var second = nowDate.getSeconds() < 10 ? "0" + nowDate.getSeconds() : nowDate.getSeconds();
  var retime = year + "-" + month + "-" + date;
  if (FormatType == 1) {
    retime = year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;;
  }
  return retime;
}
