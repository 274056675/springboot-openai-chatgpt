import { setTreeDataUtil } from '@/research/util/myUtil'

let cityAllData = [
  {
    "area_name": "北京",
    "pinyin": "Beijing",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1,
    "area_id": 110000
  },
  {
    "area_name": "北京市",
    "pinyin": "Beijing",
    "level": 2,
    "area_code": 10,
    "post_code": 100000,
    "pid": 110000,
    "id": 2,
    "area_id": 110100
  },
  {
    "area_name": "东城区",
    "pinyin": "Dongcheng",
    "level": 3,
    "area_code": 10,
    "post_code": 100010,
    "pid": 110100,
    "id": 3,
    "area_id": 110101
  },
  {
    "area_name": "西城区",
    "pinyin": "Xicheng",
    "level": 3,
    "area_code": 10,
    "post_code": 100032,
    "pid": 110100,
    "id": 4,
    "area_id": 110102
  },
  {
    "area_name": "朝阳区",
    "pinyin": "Chaoyang",
    "level": 3,
    "area_code": 10,
    "post_code": 100020,
    "pid": 110100,
    "id": 5,
    "area_id": 110105
  },
  {
    "area_name": "丰台区",
    "pinyin": "Fengtai",
    "level": 3,
    "area_code": 10,
    "post_code": 100071,
    "pid": 110100,
    "id": 6,
    "area_id": 110106
  },
  {
    "area_name": "石景山区",
    "pinyin": "Shijingshan",
    "level": 3,
    "area_code": 10,
    "post_code": 100043,
    "pid": 110100,
    "id": 7,
    "area_id": 110107
  },
  {
    "area_name": "海淀区",
    "pinyin": "Haidian",
    "level": 3,
    "area_code": 10,
    "post_code": 100089,
    "pid": 110100,
    "id": 8,
    "area_id": 110108
  },
  {
    "area_name": "门头沟区",
    "pinyin": "Mentougou",
    "level": 3,
    "area_code": 10,
    "post_code": 102300,
    "pid": 110100,
    "id": 9,
    "area_id": 110109
  },
  {
    "area_name": "房山区",
    "pinyin": "Fangshan",
    "level": 3,
    "area_code": 10,
    "post_code": 102488,
    "pid": 110100,
    "id": 10,
    "area_id": 110111
  },
  {
    "area_name": "通州区",
    "pinyin": "Tongzhou",
    "level": 3,
    "area_code": 10,
    "post_code": 101149,
    "pid": 110100,
    "id": 11,
    "area_id": 110112
  },
  {
    "area_name": "顺义区",
    "pinyin": "Shunyi",
    "level": 3,
    "area_code": 10,
    "post_code": 101300,
    "pid": 110100,
    "id": 12,
    "area_id": 110113
  },
  {
    "area_name": "昌平区",
    "pinyin": "Changping",
    "level": 3,
    "area_code": 10,
    "post_code": 102200,
    "pid": 110100,
    "id": 13,
    "area_id": 110114
  },
  {
    "area_name": "大兴区",
    "pinyin": "Daxing",
    "level": 3,
    "area_code": 10,
    "post_code": 102600,
    "pid": 110100,
    "id": 14,
    "area_id": 110115
  },
  {
    "area_name": "怀柔区",
    "pinyin": "Huairou",
    "level": 3,
    "area_code": 10,
    "post_code": 101400,
    "pid": 110100,
    "id": 15,
    "area_id": 110116
  },
  {
    "area_name": "平谷区",
    "pinyin": "Pinggu",
    "level": 3,
    "area_code": 10,
    "post_code": 101200,
    "pid": 110100,
    "id": 16,
    "area_id": 110117
  },
  {
    "area_name": "密云县",
    "pinyin": "Miyun",
    "level": 3,
    "area_code": 10,
    "post_code": 101500,
    "pid": 110100,
    "id": 17,
    "area_id": 110228
  },
  {
    "area_name": "延庆县",
    "pinyin": "Yanqing",
    "level": 3,
    "area_code": 10,
    "post_code": 102100,
    "pid": 110100,
    "id": 18,
    "area_id": 110229
  },
  {
    "area_name": "天津",
    "pinyin": "Tianjin",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 19,
    "area_id": 120000
  },
  {
    "area_name": "天津市",
    "pinyin": "Tianjin",
    "level": 2,
    "area_code": 22,
    "post_code": 300000,
    "pid": 120000,
    "id": 20,
    "area_id": 120100
  },
  {
    "area_name": "和平区",
    "pinyin": "Heping",
    "level": 3,
    "area_code": 22,
    "post_code": 300041,
    "pid": 120100,
    "id": 21,
    "area_id": 120101
  },
  {
    "area_name": "河东区",
    "pinyin": "Hedong",
    "level": 3,
    "area_code": 22,
    "post_code": 300171,
    "pid": 120100,
    "id": 22,
    "area_id": 120102
  },
  {
    "area_name": "河西区",
    "pinyin": "Hexi",
    "level": 3,
    "area_code": 22,
    "post_code": 300202,
    "pid": 120100,
    "id": 23,
    "area_id": 120103
  },
  {
    "area_name": "南开区",
    "pinyin": "Nankai",
    "level": 3,
    "area_code": 22,
    "post_code": 300110,
    "pid": 120100,
    "id": 24,
    "area_id": 120104
  },
  {
    "area_name": "河北区",
    "pinyin": "Hebei",
    "level": 3,
    "area_code": 22,
    "post_code": 300143,
    "pid": 120100,
    "id": 25,
    "area_id": 120105
  },
  {
    "area_name": "红桥区",
    "pinyin": "Hongqiao",
    "level": 3,
    "area_code": 22,
    "post_code": 300131,
    "pid": 120100,
    "id": 26,
    "area_id": 120106
  },
  {
    "area_name": "东丽区",
    "pinyin": "Dongli",
    "level": 3,
    "area_code": 22,
    "post_code": 300300,
    "pid": 120100,
    "id": 27,
    "area_id": 120110
  },
  {
    "area_name": "西青区",
    "pinyin": "Xiqing",
    "level": 3,
    "area_code": 22,
    "post_code": 300380,
    "pid": 120100,
    "id": 28,
    "area_id": 120111
  },
  {
    "area_name": "津南区",
    "pinyin": "Jinnan",
    "level": 3,
    "area_code": 22,
    "post_code": 300350,
    "pid": 120100,
    "id": 29,
    "area_id": 120112
  },
  {
    "area_name": "北辰区",
    "pinyin": "Beichen",
    "level": 3,
    "area_code": 22,
    "post_code": 300400,
    "pid": 120100,
    "id": 30,
    "area_id": 120113
  },
  {
    "area_name": "武清区",
    "pinyin": "Wuqing",
    "level": 3,
    "area_code": 22,
    "post_code": 301700,
    "pid": 120100,
    "id": 31,
    "area_id": 120114
  },
  {
    "area_name": "宝坻区",
    "pinyin": "Baodi",
    "level": 3,
    "area_code": 22,
    "post_code": 301800,
    "pid": 120100,
    "id": 32,
    "area_id": 120115
  },
  {
    "area_name": "滨海新区",
    "pinyin": "Binhaixinqu",
    "level": 3,
    "area_code": 22,
    "post_code": 300451,
    "pid": 120100,
    "id": 33,
    "area_id": 120116
  },
  {
    "area_name": "宁河县",
    "pinyin": "Ninghe",
    "level": 3,
    "area_code": 22,
    "post_code": 301500,
    "pid": 120100,
    "id": 34,
    "area_id": 120221
  },
  {
    "area_name": "静海县",
    "pinyin": "Jinghai",
    "level": 3,
    "area_code": 22,
    "post_code": 301600,
    "pid": 120100,
    "id": 35,
    "area_id": 120223
  },
  {
    "area_name": "蓟县",
    "pinyin": "Jixian",
    "level": 3,
    "area_code": 22,
    "post_code": 301900,
    "pid": 120100,
    "id": 36,
    "area_id": 120225
  },
  {
    "area_name": "河北省",
    "pinyin": "Hebei",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 37,
    "area_id": 130000
  },
  {
    "area_name": "石家庄市",
    "pinyin": "Shijiazhuang",
    "level": 2,
    "area_code": 311,
    "post_code": 50011,
    "pid": 130000,
    "id": 38,
    "area_id": 130100
  },
  {
    "area_name": "长安区",
    "pinyin": "Chang'an",
    "level": 3,
    "area_code": 311,
    "post_code": 50011,
    "pid": 130100,
    "id": 39,
    "area_id": 130102
  },
  {
    "area_name": "桥西区",
    "pinyin": "Qiaoxi",
    "level": 3,
    "area_code": 311,
    "post_code": 50091,
    "pid": 130100,
    "id": 40,
    "area_id": 130104
  },
  {
    "area_name": "新华区",
    "pinyin": "Xinhua",
    "level": 3,
    "area_code": 311,
    "post_code": 50051,
    "pid": 130100,
    "id": 41,
    "area_id": 130105
  },
  {
    "area_name": "井陉矿区",
    "pinyin": "Jingxingkuangqu",
    "level": 3,
    "area_code": 311,
    "post_code": 50100,
    "pid": 130100,
    "id": 42,
    "area_id": 130107
  },
  {
    "area_name": "裕华区",
    "pinyin": "Yuhua",
    "level": 3,
    "area_code": 311,
    "post_code": 50031,
    "pid": 130100,
    "id": 43,
    "area_id": 130108
  },
  {
    "area_name": "藁城区",
    "pinyin": "Gaocheng",
    "level": 3,
    "area_code": 311,
    "post_code": 52160,
    "pid": 130100,
    "id": 44,
    "area_id": 130109
  },
  {
    "area_name": "鹿泉区",
    "pinyin": "Luquan",
    "level": 3,
    "area_code": 311,
    "post_code": 50200,
    "pid": 130100,
    "id": 45,
    "area_id": 130110
  },
  {
    "area_name": "栾城区",
    "pinyin": "Luancheng",
    "level": 3,
    "area_code": 311,
    "post_code": 51430,
    "pid": 130100,
    "id": 46,
    "area_id": 130111
  },
  {
    "area_name": "井陉县",
    "pinyin": "Jingxing",
    "level": 3,
    "area_code": 311,
    "post_code": 50300,
    "pid": 130100,
    "id": 47,
    "area_id": 130121
  },
  {
    "area_name": "正定县",
    "pinyin": "Zhengding",
    "level": 3,
    "area_code": 311,
    "post_code": 50800,
    "pid": 130100,
    "id": 48,
    "area_id": 130123
  },
  {
    "area_name": "行唐县",
    "pinyin": "Xingtang",
    "level": 3,
    "area_code": 311,
    "post_code": 50600,
    "pid": 130100,
    "id": 49,
    "area_id": 130125
  },
  {
    "area_name": "灵寿县",
    "pinyin": "Lingshou",
    "level": 3,
    "area_code": 311,
    "post_code": 50500,
    "pid": 130100,
    "id": 50,
    "area_id": 130126
  },
  {
    "area_name": "高邑县",
    "pinyin": "Gaoyi",
    "level": 3,
    "area_code": 311,
    "post_code": 51330,
    "pid": 130100,
    "id": 51,
    "area_id": 130127
  },
  {
    "area_name": "深泽县",
    "pinyin": "Shenze",
    "level": 3,
    "area_code": 311,
    "post_code": 52560,
    "pid": 130100,
    "id": 52,
    "area_id": 130128
  },
  {
    "area_name": "赞皇县",
    "pinyin": "Zanhuang",
    "level": 3,
    "area_code": 311,
    "post_code": 51230,
    "pid": 130100,
    "id": 53,
    "area_id": 130129
  },
  {
    "area_name": "无极县",
    "pinyin": "Wuji",
    "level": 3,
    "area_code": 311,
    "post_code": 52460,
    "pid": 130100,
    "id": 54,
    "area_id": 130130
  },
  {
    "area_name": "平山县",
    "pinyin": "Pingshan",
    "level": 3,
    "area_code": 311,
    "post_code": 50400,
    "pid": 130100,
    "id": 55,
    "area_id": 130131
  },
  {
    "area_name": "元氏县",
    "pinyin": "Yuanshi",
    "level": 3,
    "area_code": 311,
    "post_code": 51130,
    "pid": 130100,
    "id": 56,
    "area_id": 130132
  },
  {
    "area_name": "赵县",
    "pinyin": "Zhaoxian",
    "level": 3,
    "area_code": 311,
    "post_code": 51530,
    "pid": 130100,
    "id": 57,
    "area_id": 130133
  },
  {
    "area_name": "辛集市",
    "pinyin": "Xinji",
    "level": 3,
    "area_code": 311,
    "post_code": 52360,
    "pid": 130100,
    "id": 58,
    "area_id": 130181
  },
  {
    "area_name": "晋州市",
    "pinyin": "Jinzhou",
    "level": 3,
    "area_code": 311,
    "post_code": 52260,
    "pid": 130100,
    "id": 59,
    "area_id": 130183
  },
  {
    "area_name": "新乐市",
    "pinyin": "Xinle",
    "level": 3,
    "area_code": 311,
    "post_code": 50700,
    "pid": 130100,
    "id": 60,
    "area_id": 130184
  },
  {
    "area_name": "唐山市",
    "pinyin": "Tangshan",
    "level": 2,
    "area_code": 315,
    "post_code": 63000,
    "pid": 130000,
    "id": 61,
    "area_id": 130200
  },
  {
    "area_name": "路南区",
    "pinyin": "Lunan",
    "level": 3,
    "area_code": 315,
    "post_code": 63000,
    "pid": 130200,
    "id": 62,
    "area_id": 130202
  },
  {
    "area_name": "路北区",
    "pinyin": "Lubei",
    "level": 3,
    "area_code": 315,
    "post_code": 63000,
    "pid": 130200,
    "id": 63,
    "area_id": 130203
  },
  {
    "area_name": "古冶区",
    "pinyin": "Guye",
    "level": 3,
    "area_code": 315,
    "post_code": 63100,
    "pid": 130200,
    "id": 64,
    "area_id": 130204
  },
  {
    "area_name": "开平区",
    "pinyin": "Kaiping",
    "level": 3,
    "area_code": 315,
    "post_code": 63021,
    "pid": 130200,
    "id": 65,
    "area_id": 130205
  },
  {
    "area_name": "丰南区",
    "pinyin": "Fengnan",
    "level": 3,
    "area_code": 315,
    "post_code": 63300,
    "pid": 130200,
    "id": 66,
    "area_id": 130207
  },
  {
    "area_name": "丰润区",
    "pinyin": "Fengrun",
    "level": 3,
    "area_code": 315,
    "post_code": 64000,
    "pid": 130200,
    "id": 67,
    "area_id": 130208
  },
  {
    "area_name": "曹妃甸区",
    "pinyin": "Caofeidian",
    "level": 3,
    "area_code": 315,
    "post_code": 63200,
    "pid": 130200,
    "id": 68,
    "area_id": 130209
  },
  {
    "area_name": "滦县",
    "pinyin": "Luanxian",
    "level": 3,
    "area_code": 315,
    "post_code": 63700,
    "pid": 130200,
    "id": 69,
    "area_id": 130223
  },
  {
    "area_name": "滦南县",
    "pinyin": "Luannan",
    "level": 3,
    "area_code": 315,
    "post_code": 63500,
    "pid": 130200,
    "id": 70,
    "area_id": 130224
  },
  {
    "area_name": "乐亭县",
    "pinyin": "Laoting",
    "level": 3,
    "area_code": 315,
    "post_code": 63600,
    "pid": 130200,
    "id": 71,
    "area_id": 130225
  },
  {
    "area_name": "迁西县",
    "pinyin": "Qianxi",
    "level": 3,
    "area_code": 315,
    "post_code": 64300,
    "pid": 130200,
    "id": 72,
    "area_id": 130227
  },
  {
    "area_name": "玉田县",
    "pinyin": "Yutian",
    "level": 3,
    "area_code": 315,
    "post_code": 64100,
    "pid": 130200,
    "id": 73,
    "area_id": 130229
  },
  {
    "area_name": "遵化市",
    "pinyin": "Zunhua",
    "level": 3,
    "area_code": 315,
    "post_code": 64200,
    "pid": 130200,
    "id": 74,
    "area_id": 130281
  },
  {
    "area_name": "迁安市",
    "pinyin": "Qian'an",
    "level": 3,
    "area_code": 315,
    "post_code": 64400,
    "pid": 130200,
    "id": 75,
    "area_id": 130283
  },
  {
    "area_name": "秦皇岛市",
    "pinyin": "Qinhuangdao",
    "level": 2,
    "area_code": 335,
    "post_code": 66000,
    "pid": 130000,
    "id": 76,
    "area_id": 130300
  },
  {
    "area_name": "海港区",
    "pinyin": "Haigang",
    "level": 3,
    "area_code": 335,
    "post_code": 66000,
    "pid": 130300,
    "id": 77,
    "area_id": 130302
  },
  {
    "area_name": "山海关区",
    "pinyin": "Shanhaiguan",
    "level": 3,
    "area_code": 335,
    "post_code": 66200,
    "pid": 130300,
    "id": 78,
    "area_id": 130303
  },
  {
    "area_name": "北戴河区",
    "pinyin": "Beidaihe",
    "level": 3,
    "area_code": 335,
    "post_code": 66100,
    "pid": 130300,
    "id": 79,
    "area_id": 130304
  },
  {
    "area_name": "青龙满族自治县",
    "pinyin": "Qinglong",
    "level": 3,
    "area_code": 335,
    "post_code": 66500,
    "pid": 130300,
    "id": 80,
    "area_id": 130321
  },
  {
    "area_name": "昌黎县",
    "pinyin": "Changli",
    "level": 3,
    "area_code": 335,
    "post_code": 66600,
    "pid": 130300,
    "id": 81,
    "area_id": 130322
  },
  {
    "area_name": "抚宁县",
    "pinyin": "Funing",
    "level": 3,
    "area_code": 335,
    "post_code": 66300,
    "pid": 130300,
    "id": 82,
    "area_id": 130323
  },
  {
    "area_name": "卢龙县",
    "pinyin": "Lulong",
    "level": 3,
    "area_code": 335,
    "post_code": 66400,
    "pid": 130300,
    "id": 83,
    "area_id": 130324
  },
  {
    "area_name": "邯郸市",
    "pinyin": "Handan",
    "level": 2,
    "area_code": 310,
    "post_code": 56002,
    "pid": 130000,
    "id": 84,
    "area_id": 130400
  },
  {
    "area_name": "邯山区",
    "pinyin": "Hanshan",
    "level": 3,
    "area_code": 310,
    "post_code": 56001,
    "pid": 130400,
    "id": 85,
    "area_id": 130402
  },
  {
    "area_name": "丛台区",
    "pinyin": "Congtai",
    "level": 3,
    "area_code": 310,
    "post_code": 56002,
    "pid": 130400,
    "id": 86,
    "area_id": 130403
  },
  {
    "area_name": "复兴区",
    "pinyin": "Fuxing",
    "level": 3,
    "area_code": 310,
    "post_code": 56003,
    "pid": 130400,
    "id": 87,
    "area_id": 130404
  },
  {
    "area_name": "峰峰矿区",
    "pinyin": "Fengfengkuangqu",
    "level": 3,
    "area_code": 310,
    "post_code": 56200,
    "pid": 130400,
    "id": 88,
    "area_id": 130406
  },
  {
    "area_name": "邯郸县",
    "pinyin": "Handan",
    "level": 3,
    "area_code": 310,
    "post_code": 56101,
    "pid": 130400,
    "id": 89,
    "area_id": 130421
  },
  {
    "area_name": "临漳县",
    "pinyin": "Linzhang",
    "level": 3,
    "area_code": 310,
    "post_code": 56600,
    "pid": 130400,
    "id": 90,
    "area_id": 130423
  },
  {
    "area_name": "成安县",
    "pinyin": "Cheng'an",
    "level": 3,
    "area_code": 310,
    "post_code": 56700,
    "pid": 130400,
    "id": 91,
    "area_id": 130424
  },
  {
    "area_name": "大名县",
    "pinyin": "Daming",
    "level": 3,
    "area_code": 310,
    "post_code": 56900,
    "pid": 130400,
    "id": 92,
    "area_id": 130425
  },
  {
    "area_name": "涉县",
    "pinyin": "Shexian",
    "level": 3,
    "area_code": 310,
    "post_code": 56400,
    "pid": 130400,
    "id": 93,
    "area_id": 130426
  },
  {
    "area_name": "磁县",
    "pinyin": "Cixian",
    "level": 3,
    "area_code": 310,
    "post_code": 56500,
    "pid": 130400,
    "id": 94,
    "area_id": 130427
  },
  {
    "area_name": "肥乡县",
    "pinyin": "Feixiang",
    "level": 3,
    "area_code": 310,
    "post_code": 57550,
    "pid": 130400,
    "id": 95,
    "area_id": 130428
  },
  {
    "area_name": "永年县",
    "pinyin": "Yongnian",
    "level": 3,
    "area_code": 310,
    "post_code": 57150,
    "pid": 130400,
    "id": 96,
    "area_id": 130429
  },
  {
    "area_name": "邱县",
    "pinyin": "Qiuxian",
    "level": 3,
    "area_code": 310,
    "post_code": 57450,
    "pid": 130400,
    "id": 97,
    "area_id": 130430
  },
  {
    "area_name": "鸡泽县",
    "pinyin": "Jize",
    "level": 3,
    "area_code": 310,
    "post_code": 57350,
    "pid": 130400,
    "id": 98,
    "area_id": 130431
  },
  {
    "area_name": "广平县",
    "pinyin": "Guangping",
    "level": 3,
    "area_code": 310,
    "post_code": 57650,
    "pid": 130400,
    "id": 99,
    "area_id": 130432
  },
  {
    "area_name": "馆陶县",
    "pinyin": "Guantao",
    "level": 3,
    "area_code": 310,
    "post_code": 57750,
    "pid": 130400,
    "id": 100,
    "area_id": 130433
  },
  {
    "area_name": "魏县",
    "pinyin": "Weixian",
    "level": 3,
    "area_code": 310,
    "post_code": 56800,
    "pid": 130400,
    "id": 101,
    "area_id": 130434
  },
  {
    "area_name": "曲周县",
    "pinyin": "Quzhou",
    "level": 3,
    "area_code": 310,
    "post_code": 57250,
    "pid": 130400,
    "id": 102,
    "area_id": 130435
  },
  {
    "area_name": "武安市",
    "pinyin": "Wu'an",
    "level": 3,
    "area_code": 310,
    "post_code": 56300,
    "pid": 130400,
    "id": 103,
    "area_id": 130481
  },
  {
    "area_name": "邢台市",
    "pinyin": "Xingtai",
    "level": 2,
    "area_code": 319,
    "post_code": 54001,
    "pid": 130000,
    "id": 104,
    "area_id": 130500
  },
  {
    "area_name": "桥东区",
    "pinyin": "Qiaodong",
    "level": 3,
    "area_code": 319,
    "post_code": 54001,
    "pid": 130500,
    "id": 105,
    "area_id": 130502
  },
  {
    "area_name": "桥西区",
    "pinyin": "Qiaoxi",
    "level": 3,
    "area_code": 319,
    "post_code": 54000,
    "pid": 130500,
    "id": 106,
    "area_id": 130503
  },
  {
    "area_name": "邢台县",
    "pinyin": "Xingtai",
    "level": 3,
    "area_code": 319,
    "post_code": 54001,
    "pid": 130500,
    "id": 107,
    "area_id": 130521
  },
  {
    "area_name": "临城县",
    "pinyin": "Lincheng",
    "level": 3,
    "area_code": 319,
    "post_code": 54300,
    "pid": 130500,
    "id": 108,
    "area_id": 130522
  },
  {
    "area_name": "内丘县",
    "pinyin": "Neiqiu",
    "level": 3,
    "area_code": 319,
    "post_code": 54200,
    "pid": 130500,
    "id": 109,
    "area_id": 130523
  },
  {
    "area_name": "柏乡县",
    "pinyin": "Baixiang",
    "level": 3,
    "area_code": 319,
    "post_code": 55450,
    "pid": 130500,
    "id": 110,
    "area_id": 130524
  },
  {
    "area_name": "隆尧县",
    "pinyin": "Longyao",
    "level": 3,
    "area_code": 319,
    "post_code": 55350,
    "pid": 130500,
    "id": 111,
    "area_id": 130525
  },
  {
    "area_name": "任县",
    "pinyin": "Renxian",
    "level": 3,
    "area_code": 319,
    "post_code": 55150,
    "pid": 130500,
    "id": 112,
    "area_id": 130526
  },
  {
    "area_name": "南和县",
    "pinyin": "Nanhe",
    "level": 3,
    "area_code": 319,
    "post_code": 54400,
    "pid": 130500,
    "id": 113,
    "area_id": 130527
  },
  {
    "area_name": "宁晋县",
    "pinyin": "Ningjin",
    "level": 3,
    "area_code": 319,
    "post_code": 55550,
    "pid": 130500,
    "id": 114,
    "area_id": 130528
  },
  {
    "area_name": "巨鹿县",
    "pinyin": "Julu",
    "level": 3,
    "area_code": 319,
    "post_code": 55250,
    "pid": 130500,
    "id": 115,
    "area_id": 130529
  },
  {
    "area_name": "新河县",
    "pinyin": "Xinhe",
    "level": 3,
    "area_code": 319,
    "post_code": 55650,
    "pid": 130500,
    "id": 116,
    "area_id": 130530
  },
  {
    "area_name": "广宗县",
    "pinyin": "Guangzong",
    "level": 3,
    "area_code": 319,
    "post_code": 54600,
    "pid": 130500,
    "id": 117,
    "area_id": 130531
  },
  {
    "area_name": "平乡县",
    "pinyin": "Pingxiang",
    "level": 3,
    "area_code": 319,
    "post_code": 54500,
    "pid": 130500,
    "id": 118,
    "area_id": 130532
  },
  {
    "area_name": "威县",
    "pinyin": "Weixian",
    "level": 3,
    "area_code": 319,
    "post_code": 54700,
    "pid": 130500,
    "id": 119,
    "area_id": 130533
  },
  {
    "area_name": "清河县",
    "pinyin": "Qinghe",
    "level": 3,
    "area_code": 319,
    "post_code": 54800,
    "pid": 130500,
    "id": 120,
    "area_id": 130534
  },
  {
    "area_name": "临西县",
    "pinyin": "Linxi",
    "level": 3,
    "area_code": 319,
    "post_code": 54900,
    "pid": 130500,
    "id": 121,
    "area_id": 130535
  },
  {
    "area_name": "南宫市",
    "pinyin": "Nangong",
    "level": 3,
    "area_code": 319,
    "post_code": 55750,
    "pid": 130500,
    "id": 122,
    "area_id": 130581
  },
  {
    "area_name": "沙河市",
    "pinyin": "Shahe",
    "level": 3,
    "area_code": 319,
    "post_code": 54100,
    "pid": 130500,
    "id": 123,
    "area_id": 130582
  },
  {
    "area_name": "保定市",
    "pinyin": "Baoding",
    "level": 2,
    "area_code": 312,
    "post_code": 71052,
    "pid": 130000,
    "id": 124,
    "area_id": 130600
  },
  {
    "area_name": "新市区",
    "pinyin": "Xinshi",
    "level": 3,
    "area_code": 312,
    "post_code": 71051,
    "pid": 130600,
    "id": 125,
    "area_id": 130602
  },
  {
    "area_name": "北市区",
    "pinyin": "Beishi",
    "level": 3,
    "area_code": 312,
    "post_code": 71000,
    "pid": 130600,
    "id": 126,
    "area_id": 130603
  },
  {
    "area_name": "南市区",
    "pinyin": "Nanshi",
    "level": 3,
    "area_code": 312,
    "post_code": 71001,
    "pid": 130600,
    "id": 127,
    "area_id": 130604
  },
  {
    "area_name": "满城县",
    "pinyin": "Mancheng",
    "level": 3,
    "area_code": 312,
    "post_code": 72150,
    "pid": 130600,
    "id": 128,
    "area_id": 130621
  },
  {
    "area_name": "清苑县",
    "pinyin": "Qingyuan",
    "level": 3,
    "area_code": 312,
    "post_code": 71100,
    "pid": 130600,
    "id": 129,
    "area_id": 130622
  },
  {
    "area_name": "涞水县",
    "pinyin": "Laishui",
    "level": 3,
    "area_code": 312,
    "post_code": 74100,
    "pid": 130600,
    "id": 130,
    "area_id": 130623
  },
  {
    "area_name": "阜平县",
    "pinyin": "Fuping",
    "level": 3,
    "area_code": 312,
    "post_code": 73200,
    "pid": 130600,
    "id": 131,
    "area_id": 130624
  },
  {
    "area_name": "徐水县",
    "pinyin": "Xushui",
    "level": 3,
    "area_code": 312,
    "post_code": 72550,
    "pid": 130600,
    "id": 132,
    "area_id": 130625
  },
  {
    "area_name": "定兴县",
    "pinyin": "Dingxing",
    "level": 3,
    "area_code": 312,
    "post_code": 72650,
    "pid": 130600,
    "id": 133,
    "area_id": 130626
  },
  {
    "area_name": "唐县",
    "pinyin": "Tangxian",
    "level": 3,
    "area_code": 312,
    "post_code": 72350,
    "pid": 130600,
    "id": 134,
    "area_id": 130627
  },
  {
    "area_name": "高阳县",
    "pinyin": "Gaoyang",
    "level": 3,
    "area_code": 312,
    "post_code": 71500,
    "pid": 130600,
    "id": 135,
    "area_id": 130628
  },
  {
    "area_name": "容城县",
    "pinyin": "Rongcheng",
    "level": 3,
    "area_code": 312,
    "post_code": 71700,
    "pid": 130600,
    "id": 136,
    "area_id": 130629
  },
  {
    "area_name": "涞源县",
    "pinyin": "Laiyuan",
    "level": 3,
    "area_code": 312,
    "post_code": 74300,
    "pid": 130600,
    "id": 137,
    "area_id": 130630
  },
  {
    "area_name": "望都县",
    "pinyin": "Wangdu",
    "level": 3,
    "area_code": 312,
    "post_code": 72450,
    "pid": 130600,
    "id": 138,
    "area_id": 130631
  },
  {
    "area_name": "安新县",
    "pinyin": "Anxin",
    "level": 3,
    "area_code": 312,
    "post_code": 71600,
    "pid": 130600,
    "id": 139,
    "area_id": 130632
  },
  {
    "area_name": "易县",
    "pinyin": "Yixian",
    "level": 3,
    "area_code": 312,
    "post_code": 74200,
    "pid": 130600,
    "id": 140,
    "area_id": 130633
  },
  {
    "area_name": "曲阳县",
    "pinyin": "Quyang",
    "level": 3,
    "area_code": 312,
    "post_code": 73100,
    "pid": 130600,
    "id": 141,
    "area_id": 130634
  },
  {
    "area_name": "蠡县",
    "pinyin": "Lixian",
    "level": 3,
    "area_code": 312,
    "post_code": 71400,
    "pid": 130600,
    "id": 142,
    "area_id": 130635
  },
  {
    "area_name": "顺平县",
    "pinyin": "Shunping",
    "level": 3,
    "area_code": 312,
    "post_code": 72250,
    "pid": 130600,
    "id": 143,
    "area_id": 130636
  },
  {
    "area_name": "博野县",
    "pinyin": "Boye",
    "level": 3,
    "area_code": 312,
    "post_code": 71300,
    "pid": 130600,
    "id": 144,
    "area_id": 130637
  },
  {
    "area_name": "雄县",
    "pinyin": "Xiongxian",
    "level": 3,
    "area_code": 312,
    "post_code": 71800,
    "pid": 130600,
    "id": 145,
    "area_id": 130638
  },
  {
    "area_name": "涿州市",
    "pinyin": "Zhuozhou",
    "level": 3,
    "area_code": 312,
    "post_code": 72750,
    "pid": 130600,
    "id": 146,
    "area_id": 130681
  },
  {
    "area_name": "定州市",
    "pinyin": "Dingzhou",
    "level": 3,
    "area_code": 312,
    "post_code": 73000,
    "pid": 130600,
    "id": 147,
    "area_id": 130682
  },
  {
    "area_name": "安国市",
    "pinyin": "Anguo",
    "level": 3,
    "area_code": 312,
    "post_code": 71200,
    "pid": 130600,
    "id": 148,
    "area_id": 130683
  },
  {
    "area_name": "高碑店市",
    "pinyin": "Gaobeidian",
    "level": 3,
    "area_code": 312,
    "post_code": 74000,
    "pid": 130600,
    "id": 149,
    "area_id": 130684
  },
  {
    "area_name": "张家口市",
    "pinyin": "Zhangjiakou",
    "level": 2,
    "area_code": 313,
    "post_code": 75000,
    "pid": 130000,
    "id": 150,
    "area_id": 130700
  },
  {
    "area_name": "桥东区",
    "pinyin": "Qiaodong",
    "level": 3,
    "area_code": 313,
    "post_code": 75000,
    "pid": 130700,
    "id": 151,
    "area_id": 130702
  },
  {
    "area_name": "桥西区",
    "pinyin": "Qiaoxi",
    "level": 3,
    "area_code": 313,
    "post_code": 75061,
    "pid": 130700,
    "id": 152,
    "area_id": 130703
  },
  {
    "area_name": "宣化区",
    "pinyin": "Xuanhua",
    "level": 3,
    "area_code": 313,
    "post_code": 75100,
    "pid": 130700,
    "id": 153,
    "area_id": 130705
  },
  {
    "area_name": "下花园区",
    "pinyin": "Xiahuayuan",
    "level": 3,
    "area_code": 313,
    "post_code": 75300,
    "pid": 130700,
    "id": 154,
    "area_id": 130706
  },
  {
    "area_name": "宣化县",
    "pinyin": "Xuanhua",
    "level": 3,
    "area_code": 313,
    "post_code": 75100,
    "pid": 130700,
    "id": 155,
    "area_id": 130721
  },
  {
    "area_name": "张北县",
    "pinyin": "Zhangbei",
    "level": 3,
    "area_code": 313,
    "post_code": 76450,
    "pid": 130700,
    "id": 156,
    "area_id": 130722
  },
  {
    "area_name": "康保县",
    "pinyin": "Kangbao",
    "level": 3,
    "area_code": 313,
    "post_code": 76650,
    "pid": 130700,
    "id": 157,
    "area_id": 130723
  },
  {
    "area_name": "沽源县",
    "pinyin": "Guyuan",
    "level": 3,
    "area_code": 313,
    "post_code": 76550,
    "pid": 130700,
    "id": 158,
    "area_id": 130724
  },
  {
    "area_name": "尚义县",
    "pinyin": "Shangyi",
    "level": 3,
    "area_code": 313,
    "post_code": 76750,
    "pid": 130700,
    "id": 159,
    "area_id": 130725
  },
  {
    "area_name": "蔚县",
    "pinyin": "Yuxian",
    "level": 3,
    "area_code": 313,
    "post_code": 75700,
    "pid": 130700,
    "id": 160,
    "area_id": 130726
  },
  {
    "area_name": "阳原县",
    "pinyin": "Yangyuan",
    "level": 3,
    "area_code": 313,
    "post_code": 75800,
    "pid": 130700,
    "id": 161,
    "area_id": 130727
  },
  {
    "area_name": "怀安县",
    "pinyin": "Huai'an",
    "level": 3,
    "area_code": 313,
    "post_code": 76150,
    "pid": 130700,
    "id": 162,
    "area_id": 130728
  },
  {
    "area_name": "万全县",
    "pinyin": "Wanquan",
    "level": 3,
    "area_code": 313,
    "post_code": 76250,
    "pid": 130700,
    "id": 163,
    "area_id": 130729
  },
  {
    "area_name": "怀来县",
    "pinyin": "Huailai",
    "level": 3,
    "area_code": 313,
    "post_code": 75400,
    "pid": 130700,
    "id": 164,
    "area_id": 130730
  },
  {
    "area_name": "涿鹿县",
    "pinyin": "Zhuolu",
    "level": 3,
    "area_code": 313,
    "post_code": 75600,
    "pid": 130700,
    "id": 165,
    "area_id": 130731
  },
  {
    "area_name": "赤城县",
    "pinyin": "Chicheng",
    "level": 3,
    "area_code": 313,
    "post_code": 75500,
    "pid": 130700,
    "id": 166,
    "area_id": 130732
  },
  {
    "area_name": "崇礼县",
    "pinyin": "Chongli",
    "level": 3,
    "area_code": 313,
    "post_code": 76350,
    "pid": 130700,
    "id": 167,
    "area_id": 130733
  },
  {
    "area_name": "承德市",
    "pinyin": "Chengde",
    "level": 2,
    "area_code": 314,
    "post_code": 67000,
    "pid": 130000,
    "id": 168,
    "area_id": 130800
  },
  {
    "area_name": "双桥区",
    "pinyin": "Shuangqiao",
    "level": 3,
    "area_code": 314,
    "post_code": 67000,
    "pid": 130800,
    "id": 169,
    "area_id": 130802
  },
  {
    "area_name": "双滦区",
    "pinyin": "Shuangluan",
    "level": 3,
    "area_code": 314,
    "post_code": 67001,
    "pid": 130800,
    "id": 170,
    "area_id": 130803
  },
  {
    "area_name": "鹰手营子矿区",
    "pinyin": "Yingshouyingzikuangqu",
    "level": 3,
    "area_code": 314,
    "post_code": 67200,
    "pid": 130800,
    "id": 171,
    "area_id": 130804
  },
  {
    "area_name": "承德县",
    "pinyin": "Chengde",
    "level": 3,
    "area_code": 314,
    "post_code": 67400,
    "pid": 130800,
    "id": 172,
    "area_id": 130821
  },
  {
    "area_name": "兴隆县",
    "pinyin": "Xinglong",
    "level": 3,
    "area_code": 314,
    "post_code": 67300,
    "pid": 130800,
    "id": 173,
    "area_id": 130822
  },
  {
    "area_name": "平泉县",
    "pinyin": "Pingquan",
    "level": 3,
    "area_code": 314,
    "post_code": 67500,
    "pid": 130800,
    "id": 174,
    "area_id": 130823
  },
  {
    "area_name": "滦平县",
    "pinyin": "Luanping",
    "level": 3,
    "area_code": 314,
    "post_code": 68250,
    "pid": 130800,
    "id": 175,
    "area_id": 130824
  },
  {
    "area_name": "隆化县",
    "pinyin": "Longhua",
    "level": 3,
    "area_code": 314,
    "post_code": 68150,
    "pid": 130800,
    "id": 176,
    "area_id": 130825
  },
  {
    "area_name": "丰宁满族自治县",
    "pinyin": "Fengning",
    "level": 3,
    "area_code": 314,
    "post_code": 68350,
    "pid": 130800,
    "id": 177,
    "area_id": 130826
  },
  {
    "area_name": "宽城满族自治县",
    "pinyin": "Kuancheng",
    "level": 3,
    "area_code": 314,
    "post_code": 67600,
    "pid": 130800,
    "id": 178,
    "area_id": 130827
  },
  {
    "area_name": "围场满族蒙古族自治县",
    "pinyin": "Weichang",
    "level": 3,
    "area_code": 314,
    "post_code": 68450,
    "pid": 130800,
    "id": 179,
    "area_id": 130828
  },
  {
    "area_name": "沧州市",
    "pinyin": "Cangzhou",
    "level": 2,
    "area_code": 317,
    "post_code": 61001,
    "pid": 130000,
    "id": 180,
    "area_id": 130900
  },
  {
    "area_name": "新华区",
    "pinyin": "Xinhua",
    "level": 3,
    "area_code": 317,
    "post_code": 61000,
    "pid": 130900,
    "id": 181,
    "area_id": 130902
  },
  {
    "area_name": "运河区",
    "pinyin": "Yunhe",
    "level": 3,
    "area_code": 317,
    "post_code": 61001,
    "pid": 130900,
    "id": 182,
    "area_id": 130903
  },
  {
    "area_name": "沧县",
    "pinyin": "Cangxian",
    "level": 3,
    "area_code": 317,
    "post_code": 61000,
    "pid": 130900,
    "id": 183,
    "area_id": 130921
  },
  {
    "area_name": "青县",
    "pinyin": "Qingxian",
    "level": 3,
    "area_code": 317,
    "post_code": 62650,
    "pid": 130900,
    "id": 184,
    "area_id": 130922
  },
  {
    "area_name": "东光县",
    "pinyin": "Dongguang",
    "level": 3,
    "area_code": 317,
    "post_code": 61600,
    "pid": 130900,
    "id": 185,
    "area_id": 130923
  },
  {
    "area_name": "海兴县",
    "pinyin": "Haixing",
    "level": 3,
    "area_code": 317,
    "post_code": 61200,
    "pid": 130900,
    "id": 186,
    "area_id": 130924
  },
  {
    "area_name": "盐山县",
    "pinyin": "Yanshan",
    "level": 3,
    "area_code": 317,
    "post_code": 61300,
    "pid": 130900,
    "id": 187,
    "area_id": 130925
  },
  {
    "area_name": "肃宁县",
    "pinyin": "Suning",
    "level": 3,
    "area_code": 317,
    "post_code": 62350,
    "pid": 130900,
    "id": 188,
    "area_id": 130926
  },
  {
    "area_name": "南皮县",
    "pinyin": "Nanpi",
    "level": 3,
    "area_code": 317,
    "post_code": 61500,
    "pid": 130900,
    "id": 189,
    "area_id": 130927
  },
  {
    "area_name": "吴桥县",
    "pinyin": "Wuqiao",
    "level": 3,
    "area_code": 317,
    "post_code": 61800,
    "pid": 130900,
    "id": 190,
    "area_id": 130928
  },
  {
    "area_name": "献县",
    "pinyin": "Xianxian",
    "level": 3,
    "area_code": 317,
    "post_code": 62250,
    "pid": 130900,
    "id": 191,
    "area_id": 130929
  },
  {
    "area_name": "孟村回族自治县",
    "pinyin": "Mengcun",
    "level": 3,
    "area_code": 317,
    "post_code": 61400,
    "pid": 130900,
    "id": 192,
    "area_id": 130930
  },
  {
    "area_name": "泊头市",
    "pinyin": "Botou",
    "level": 3,
    "area_code": 317,
    "post_code": 62150,
    "pid": 130900,
    "id": 193,
    "area_id": 130981
  },
  {
    "area_name": "任丘市",
    "pinyin": "Renqiu",
    "level": 3,
    "area_code": 317,
    "post_code": 62550,
    "pid": 130900,
    "id": 194,
    "area_id": 130982
  },
  {
    "area_name": "黄骅市",
    "pinyin": "Huanghua",
    "level": 3,
    "area_code": 317,
    "post_code": 61100,
    "pid": 130900,
    "id": 195,
    "area_id": 130983
  },
  {
    "area_name": "河间市",
    "pinyin": "Hejian",
    "level": 3,
    "area_code": 317,
    "post_code": 62450,
    "pid": 130900,
    "id": 196,
    "area_id": 130984
  },
  {
    "area_name": "廊坊市",
    "pinyin": "Langfang",
    "level": 2,
    "area_code": 316,
    "post_code": 65000,
    "pid": 130000,
    "id": 197,
    "area_id": 131000
  },
  {
    "area_name": "安次区",
    "pinyin": "Anci",
    "level": 3,
    "area_code": 316,
    "post_code": 65000,
    "pid": 131000,
    "id": 198,
    "area_id": 131002
  },
  {
    "area_name": "广阳区",
    "pinyin": "Guangyang",
    "level": 3,
    "area_code": 316,
    "post_code": 65000,
    "pid": 131000,
    "id": 199,
    "area_id": 131003
  },
  {
    "area_name": "固安县",
    "pinyin": "Gu'an",
    "level": 3,
    "area_code": 316,
    "post_code": 65500,
    "pid": 131000,
    "id": 200,
    "area_id": 131022
  },
  {
    "area_name": "永清县",
    "pinyin": "Yongqing",
    "level": 3,
    "area_code": 316,
    "post_code": 65600,
    "pid": 131000,
    "id": 201,
    "area_id": 131023
  },
  {
    "area_name": "香河县",
    "pinyin": "Xianghe",
    "level": 3,
    "area_code": 316,
    "post_code": 65400,
    "pid": 131000,
    "id": 202,
    "area_id": 131024
  },
  {
    "area_name": "大城县",
    "pinyin": "Daicheng",
    "level": 3,
    "area_code": 316,
    "post_code": 65900,
    "pid": 131000,
    "id": 203,
    "area_id": 131025
  },
  {
    "area_name": "文安县",
    "pinyin": "Wen'an",
    "level": 3,
    "area_code": 316,
    "post_code": 65800,
    "pid": 131000,
    "id": 204,
    "area_id": 131026
  },
  {
    "area_name": "大厂回族自治县",
    "pinyin": "Dachang",
    "level": 3,
    "area_code": 316,
    "post_code": 65300,
    "pid": 131000,
    "id": 205,
    "area_id": 131028
  },
  {
    "area_name": "霸州市",
    "pinyin": "Bazhou",
    "level": 3,
    "area_code": 316,
    "post_code": 65700,
    "pid": 131000,
    "id": 206,
    "area_id": 131081
  },
  {
    "area_name": "三河市",
    "pinyin": "Sanhe",
    "level": 3,
    "area_code": 316,
    "post_code": 65200,
    "pid": 131000,
    "id": 207,
    "area_id": 131082
  },
  {
    "area_name": "衡水市",
    "pinyin": "Hengshui",
    "level": 2,
    "area_code": 318,
    "post_code": 53000,
    "pid": 130000,
    "id": 208,
    "area_id": 131100
  },
  {
    "area_name": "桃城区",
    "pinyin": "Taocheng",
    "level": 3,
    "area_code": 318,
    "post_code": 53000,
    "pid": 131100,
    "id": 209,
    "area_id": 131102
  },
  {
    "area_name": "枣强县",
    "pinyin": "Zaoqiang",
    "level": 3,
    "area_code": 318,
    "post_code": 53100,
    "pid": 131100,
    "id": 210,
    "area_id": 131121
  },
  {
    "area_name": "武邑县",
    "pinyin": "Wuyi",
    "level": 3,
    "area_code": 318,
    "post_code": 53400,
    "pid": 131100,
    "id": 211,
    "area_id": 131122
  },
  {
    "area_name": "武强县",
    "pinyin": "Wuqiang",
    "level": 3,
    "area_code": 318,
    "post_code": 53300,
    "pid": 131100,
    "id": 212,
    "area_id": 131123
  },
  {
    "area_name": "饶阳县",
    "pinyin": "Raoyang",
    "level": 3,
    "area_code": 318,
    "post_code": 53900,
    "pid": 131100,
    "id": 213,
    "area_id": 131124
  },
  {
    "area_name": "安平县",
    "pinyin": "Anping",
    "level": 3,
    "area_code": 318,
    "post_code": 53600,
    "pid": 131100,
    "id": 214,
    "area_id": 131125
  },
  {
    "area_name": "故城县",
    "pinyin": "Gucheng",
    "level": 3,
    "area_code": 318,
    "post_code": 53800,
    "pid": 131100,
    "id": 215,
    "area_id": 131126
  },
  {
    "area_name": "景县",
    "pinyin": "Jingxian",
    "level": 3,
    "area_code": 318,
    "post_code": 53500,
    "pid": 131100,
    "id": 216,
    "area_id": 131127
  },
  {
    "area_name": "阜城县",
    "pinyin": "Fucheng",
    "level": 3,
    "area_code": 318,
    "post_code": 53700,
    "pid": 131100,
    "id": 217,
    "area_id": 131128
  },
  {
    "area_name": "冀州市",
    "pinyin": "Jizhou",
    "level": 3,
    "area_code": 318,
    "post_code": 53200,
    "pid": 131100,
    "id": 218,
    "area_id": 131181
  },
  {
    "area_name": "深州市",
    "pinyin": "Shenzhou",
    "level": 3,
    "area_code": 318,
    "post_code": 53800,
    "pid": 131100,
    "id": 219,
    "area_id": 131182
  },
  {
    "area_name": "山西省",
    "pinyin": "Shanxi",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 220,
    "area_id": 140000
  },
  {
    "area_name": "太原市",
    "pinyin": "Taiyuan",
    "level": 2,
    "area_code": 351,
    "post_code": 30082,
    "pid": 140000,
    "id": 221,
    "area_id": 140100
  },
  {
    "area_name": "小店区",
    "pinyin": "Xiaodian",
    "level": 3,
    "area_code": 351,
    "post_code": 30032,
    "pid": 140100,
    "id": 222,
    "area_id": 140105
  },
  {
    "area_name": "迎泽区",
    "pinyin": "Yingze",
    "level": 3,
    "area_code": 351,
    "post_code": 30002,
    "pid": 140100,
    "id": 223,
    "area_id": 140106
  },
  {
    "area_name": "杏花岭区",
    "pinyin": "Xinghualing",
    "level": 3,
    "area_code": 351,
    "post_code": 30009,
    "pid": 140100,
    "id": 224,
    "area_id": 140107
  },
  {
    "area_name": "尖草坪区",
    "pinyin": "Jiancaoping",
    "level": 3,
    "area_code": 351,
    "post_code": 30023,
    "pid": 140100,
    "id": 225,
    "area_id": 140108
  },
  {
    "area_name": "万柏林区",
    "pinyin": "Wanbailin",
    "level": 3,
    "area_code": 351,
    "post_code": 30024,
    "pid": 140100,
    "id": 226,
    "area_id": 140109
  },
  {
    "area_name": "晋源区",
    "pinyin": "Jinyuan",
    "level": 3,
    "area_code": 351,
    "post_code": 30025,
    "pid": 140100,
    "id": 227,
    "area_id": 140110
  },
  {
    "area_name": "清徐县",
    "pinyin": "Qingxu",
    "level": 3,
    "area_code": 351,
    "post_code": 30400,
    "pid": 140100,
    "id": 228,
    "area_id": 140121
  },
  {
    "area_name": "阳曲县",
    "pinyin": "Yangqu",
    "level": 3,
    "area_code": 351,
    "post_code": 30100,
    "pid": 140100,
    "id": 229,
    "area_id": 140122
  },
  {
    "area_name": "娄烦县",
    "pinyin": "Loufan",
    "level": 3,
    "area_code": 351,
    "post_code": 30300,
    "pid": 140100,
    "id": 230,
    "area_id": 140123
  },
  {
    "area_name": "古交市",
    "pinyin": "Gujiao",
    "level": 3,
    "area_code": 351,
    "post_code": 30200,
    "pid": 140100,
    "id": 231,
    "area_id": 140181
  },
  {
    "area_name": "大同市",
    "pinyin": "Datong",
    "level": 2,
    "area_code": 352,
    "post_code": 37008,
    "pid": 140000,
    "id": 232,
    "area_id": 140200
  },
  {
    "area_name": "城区",
    "pinyin": "Chengqu",
    "level": 3,
    "area_code": 352,
    "post_code": 37008,
    "pid": 140200,
    "id": 233,
    "area_id": 140202
  },
  {
    "area_name": "矿区",
    "pinyin": "Kuangqu",
    "level": 3,
    "area_code": 352,
    "post_code": 37003,
    "pid": 140200,
    "id": 234,
    "area_id": 140203
  },
  {
    "area_name": "南郊区",
    "pinyin": "Nanjiao",
    "level": 3,
    "area_code": 352,
    "post_code": 37001,
    "pid": 140200,
    "id": 235,
    "area_id": 140211
  },
  {
    "area_name": "新荣区",
    "pinyin": "Xinrong",
    "level": 3,
    "area_code": 352,
    "post_code": 37002,
    "pid": 140200,
    "id": 236,
    "area_id": 140212
  },
  {
    "area_name": "阳高县",
    "pinyin": "Yanggao",
    "level": 3,
    "area_code": 352,
    "post_code": 38100,
    "pid": 140200,
    "id": 237,
    "area_id": 140221
  },
  {
    "area_name": "天镇县",
    "pinyin": "Tianzhen",
    "level": 3,
    "area_code": 352,
    "post_code": 38200,
    "pid": 140200,
    "id": 238,
    "area_id": 140222
  },
  {
    "area_name": "广灵县",
    "pinyin": "Guangling",
    "level": 3,
    "area_code": 352,
    "post_code": 37500,
    "pid": 140200,
    "id": 239,
    "area_id": 140223
  },
  {
    "area_name": "灵丘县",
    "pinyin": "Lingqiu",
    "level": 3,
    "area_code": 352,
    "post_code": 34400,
    "pid": 140200,
    "id": 240,
    "area_id": 140224
  },
  {
    "area_name": "浑源县",
    "pinyin": "Hunyuan",
    "level": 3,
    "area_code": 352,
    "post_code": 37400,
    "pid": 140200,
    "id": 241,
    "area_id": 140225
  },
  {
    "area_name": "左云县",
    "pinyin": "Zuoyun",
    "level": 3,
    "area_code": 352,
    "post_code": 37100,
    "pid": 140200,
    "id": 242,
    "area_id": 140226
  },
  {
    "area_name": "大同县",
    "pinyin": "Datong",
    "level": 3,
    "area_code": 352,
    "post_code": 37300,
    "pid": 140200,
    "id": 243,
    "area_id": 140227
  },
  {
    "area_name": "阳泉市",
    "pinyin": "Yangquan",
    "level": 2,
    "area_code": 353,
    "post_code": 45000,
    "pid": 140000,
    "id": 244,
    "area_id": 140300
  },
  {
    "area_name": "城区",
    "pinyin": "Chengqu",
    "level": 3,
    "area_code": 353,
    "post_code": 45000,
    "pid": 140300,
    "id": 245,
    "area_id": 140302
  },
  {
    "area_name": "矿区",
    "pinyin": "Kuangqu",
    "level": 3,
    "area_code": 353,
    "post_code": 45000,
    "pid": 140300,
    "id": 246,
    "area_id": 140303
  },
  {
    "area_name": "郊区",
    "pinyin": "Jiaoqu",
    "level": 3,
    "area_code": 353,
    "post_code": 45011,
    "pid": 140300,
    "id": 247,
    "area_id": 140311
  },
  {
    "area_name": "平定县",
    "pinyin": "Pingding",
    "level": 3,
    "area_code": 353,
    "post_code": 45200,
    "pid": 140300,
    "id": 248,
    "area_id": 140321
  },
  {
    "area_name": "盂县",
    "pinyin": "Yuxian",
    "level": 3,
    "area_code": 353,
    "post_code": 45100,
    "pid": 140300,
    "id": 249,
    "area_id": 140322
  },
  {
    "area_name": "长治市",
    "pinyin": "Changzhi",
    "level": 2,
    "area_code": 355,
    "post_code": 46000,
    "pid": 140000,
    "id": 250,
    "area_id": 140400
  },
  {
    "area_name": "城区",
    "pinyin": "Chengqu",
    "level": 3,
    "area_code": 355,
    "post_code": 46011,
    "pid": 140400,
    "id": 251,
    "area_id": 140402
  },
  {
    "area_name": "郊区",
    "pinyin": "Jiaoqu",
    "level": 3,
    "area_code": 355,
    "post_code": 46011,
    "pid": 140400,
    "id": 252,
    "area_id": 140411
  },
  {
    "area_name": "长治县",
    "pinyin": "Changzhi",
    "level": 3,
    "area_code": 355,
    "post_code": 47100,
    "pid": 140400,
    "id": 253,
    "area_id": 140421
  },
  {
    "area_name": "襄垣县",
    "pinyin": "Xiangyuan",
    "level": 3,
    "area_code": 355,
    "post_code": 46200,
    "pid": 140400,
    "id": 254,
    "area_id": 140423
  },
  {
    "area_name": "屯留县",
    "pinyin": "Tunliu",
    "level": 3,
    "area_code": 355,
    "post_code": 46100,
    "pid": 140400,
    "id": 255,
    "area_id": 140424
  },
  {
    "area_name": "平顺县",
    "pinyin": "Pingshun",
    "level": 3,
    "area_code": 355,
    "post_code": 47400,
    "pid": 140400,
    "id": 256,
    "area_id": 140425
  },
  {
    "area_name": "黎城县",
    "pinyin": "Licheng",
    "level": 3,
    "area_code": 355,
    "post_code": 47600,
    "pid": 140400,
    "id": 257,
    "area_id": 140426
  },
  {
    "area_name": "壶关县",
    "pinyin": "Huguan",
    "level": 3,
    "area_code": 355,
    "post_code": 47300,
    "pid": 140400,
    "id": 258,
    "area_id": 140427
  },
  {
    "area_name": "长子县",
    "pinyin": "Zhangzi",
    "level": 3,
    "area_code": 355,
    "post_code": 46600,
    "pid": 140400,
    "id": 259,
    "area_id": 140428
  },
  {
    "area_name": "武乡县",
    "pinyin": "Wuxiang",
    "level": 3,
    "area_code": 355,
    "post_code": 46300,
    "pid": 140400,
    "id": 260,
    "area_id": 140429
  },
  {
    "area_name": "沁县",
    "pinyin": "Qinxian",
    "level": 3,
    "area_code": 355,
    "post_code": 46400,
    "pid": 140400,
    "id": 261,
    "area_id": 140430
  },
  {
    "area_name": "沁源县",
    "pinyin": "Qinyuan",
    "level": 3,
    "area_code": 355,
    "post_code": 46500,
    "pid": 140400,
    "id": 262,
    "area_id": 140431
  },
  {
    "area_name": "潞城市",
    "pinyin": "Lucheng",
    "level": 3,
    "area_code": 355,
    "post_code": 47500,
    "pid": 140400,
    "id": 263,
    "area_id": 140481
  },
  {
    "area_name": "晋城市",
    "pinyin": "Jincheng",
    "level": 2,
    "area_code": 356,
    "post_code": 48000,
    "pid": 140000,
    "id": 264,
    "area_id": 140500
  },
  {
    "area_name": "城区",
    "pinyin": "Chengqu",
    "level": 3,
    "area_code": 356,
    "post_code": 48000,
    "pid": 140500,
    "id": 265,
    "area_id": 140502
  },
  {
    "area_name": "沁水县",
    "pinyin": "Qinshui",
    "level": 3,
    "area_code": 356,
    "post_code": 48200,
    "pid": 140500,
    "id": 266,
    "area_id": 140521
  },
  {
    "area_name": "阳城县",
    "pinyin": "Yangcheng",
    "level": 3,
    "area_code": 356,
    "post_code": 48100,
    "pid": 140500,
    "id": 267,
    "area_id": 140522
  },
  {
    "area_name": "陵川县",
    "pinyin": "Lingchuan",
    "level": 3,
    "area_code": 356,
    "post_code": 48300,
    "pid": 140500,
    "id": 268,
    "area_id": 140524
  },
  {
    "area_name": "泽州县",
    "pinyin": "Zezhou",
    "level": 3,
    "area_code": 356,
    "post_code": 48012,
    "pid": 140500,
    "id": 269,
    "area_id": 140525
  },
  {
    "area_name": "高平市",
    "pinyin": "Gaoping",
    "level": 3,
    "area_code": 356,
    "post_code": 48400,
    "pid": 140500,
    "id": 270,
    "area_id": 140581
  },
  {
    "area_name": "朔州市",
    "pinyin": "Shuozhou",
    "level": 2,
    "area_code": 349,
    "post_code": 38500,
    "pid": 140000,
    "id": 271,
    "area_id": 140600
  },
  {
    "area_name": "朔城区",
    "pinyin": "Shuocheng",
    "level": 3,
    "area_code": 349,
    "post_code": 36000,
    "pid": 140600,
    "id": 272,
    "area_id": 140602
  },
  {
    "area_name": "平鲁区",
    "pinyin": "Pinglu",
    "level": 3,
    "area_code": 349,
    "post_code": 38600,
    "pid": 140600,
    "id": 273,
    "area_id": 140603
  },
  {
    "area_name": "山阴县",
    "pinyin": "Shanyin",
    "level": 3,
    "area_code": 349,
    "post_code": 36900,
    "pid": 140600,
    "id": 274,
    "area_id": 140621
  },
  {
    "area_name": "应县",
    "pinyin": "Yingxian",
    "level": 3,
    "area_code": 349,
    "post_code": 37600,
    "pid": 140600,
    "id": 275,
    "area_id": 140622
  },
  {
    "area_name": "右玉县",
    "pinyin": "Youyu",
    "level": 3,
    "area_code": 349,
    "post_code": 37200,
    "pid": 140600,
    "id": 276,
    "area_id": 140623
  },
  {
    "area_name": "怀仁县",
    "pinyin": "Huairen",
    "level": 3,
    "area_code": 349,
    "post_code": 38300,
    "pid": 140600,
    "id": 277,
    "area_id": 140624
  },
  {
    "area_name": "晋中市",
    "pinyin": "Jinzhong",
    "level": 2,
    "area_code": 354,
    "post_code": 30600,
    "pid": 140000,
    "id": 278,
    "area_id": 140700
  },
  {
    "area_name": "榆次区",
    "pinyin": "Yuci",
    "level": 3,
    "area_code": 354,
    "post_code": 30600,
    "pid": 140700,
    "id": 279,
    "area_id": 140702
  },
  {
    "area_name": "榆社县",
    "pinyin": "Yushe",
    "level": 3,
    "area_code": 354,
    "post_code": 31800,
    "pid": 140700,
    "id": 280,
    "area_id": 140721
  },
  {
    "area_name": "左权县",
    "pinyin": "Zuoquan",
    "level": 3,
    "area_code": 354,
    "post_code": 32600,
    "pid": 140700,
    "id": 281,
    "area_id": 140722
  },
  {
    "area_name": "和顺县",
    "pinyin": "Heshun",
    "level": 3,
    "area_code": 354,
    "post_code": 32700,
    "pid": 140700,
    "id": 282,
    "area_id": 140723
  },
  {
    "area_name": "昔阳县",
    "pinyin": "Xiyang",
    "level": 3,
    "area_code": 354,
    "post_code": 45300,
    "pid": 140700,
    "id": 283,
    "area_id": 140724
  },
  {
    "area_name": "寿阳县",
    "pinyin": "Shouyang",
    "level": 3,
    "area_code": 354,
    "post_code": 45400,
    "pid": 140700,
    "id": 284,
    "area_id": 140725
  },
  {
    "area_name": "太谷县",
    "pinyin": "Taigu",
    "level": 3,
    "area_code": 354,
    "post_code": 30800,
    "pid": 140700,
    "id": 285,
    "area_id": 140726
  },
  {
    "area_name": "祁县",
    "pinyin": "Qixian",
    "level": 3,
    "area_code": 354,
    "post_code": 30900,
    "pid": 140700,
    "id": 286,
    "area_id": 140727
  },
  {
    "area_name": "平遥县",
    "pinyin": "Pingyao",
    "level": 3,
    "area_code": 354,
    "post_code": 31100,
    "pid": 140700,
    "id": 287,
    "area_id": 140728
  },
  {
    "area_name": "灵石县",
    "pinyin": "Lingshi",
    "level": 3,
    "area_code": 354,
    "post_code": 31300,
    "pid": 140700,
    "id": 288,
    "area_id": 140729
  },
  {
    "area_name": "介休市",
    "pinyin": "Jiexiu",
    "level": 3,
    "area_code": 354,
    "post_code": 32000,
    "pid": 140700,
    "id": 289,
    "area_id": 140781
  },
  {
    "area_name": "运城市",
    "pinyin": "Yuncheng",
    "level": 2,
    "area_code": 359,
    "post_code": 44000,
    "pid": 140000,
    "id": 290,
    "area_id": 140800
  },
  {
    "area_name": "盐湖区",
    "pinyin": "Yanhu",
    "level": 3,
    "area_code": 359,
    "post_code": 44000,
    "pid": 140800,
    "id": 291,
    "area_id": 140802
  },
  {
    "area_name": "临猗县",
    "pinyin": "Linyi",
    "level": 3,
    "area_code": 359,
    "post_code": 44100,
    "pid": 140800,
    "id": 292,
    "area_id": 140821
  },
  {
    "area_name": "万荣县",
    "pinyin": "Wanrong",
    "level": 3,
    "area_code": 359,
    "post_code": 44200,
    "pid": 140800,
    "id": 293,
    "area_id": 140822
  },
  {
    "area_name": "闻喜县",
    "pinyin": "Wenxi",
    "level": 3,
    "area_code": 359,
    "post_code": 43800,
    "pid": 140800,
    "id": 294,
    "area_id": 140823
  },
  {
    "area_name": "稷山县",
    "pinyin": "Jishan",
    "level": 3,
    "area_code": 359,
    "post_code": 43200,
    "pid": 140800,
    "id": 295,
    "area_id": 140824
  },
  {
    "area_name": "新绛县",
    "pinyin": "Xinjiang",
    "level": 3,
    "area_code": 359,
    "post_code": 43100,
    "pid": 140800,
    "id": 296,
    "area_id": 140825
  },
  {
    "area_name": "绛县",
    "pinyin": "Jiangxian",
    "level": 3,
    "area_code": 359,
    "post_code": 43600,
    "pid": 140800,
    "id": 297,
    "area_id": 140826
  },
  {
    "area_name": "垣曲县",
    "pinyin": "Yuanqu",
    "level": 3,
    "area_code": 359,
    "post_code": 43700,
    "pid": 140800,
    "id": 298,
    "area_id": 140827
  },
  {
    "area_name": "夏县",
    "pinyin": "Xiaxian",
    "level": 3,
    "area_code": 359,
    "post_code": 44400,
    "pid": 140800,
    "id": 299,
    "area_id": 140828
  },
  {
    "area_name": "平陆县",
    "pinyin": "Pinglu",
    "level": 3,
    "area_code": 359,
    "post_code": 44300,
    "pid": 140800,
    "id": 300,
    "area_id": 140829
  },
  {
    "area_name": "芮城县",
    "pinyin": "Ruicheng",
    "level": 3,
    "area_code": 359,
    "post_code": 44600,
    "pid": 140800,
    "id": 301,
    "area_id": 140830
  },
  {
    "area_name": "永济市",
    "pinyin": "Yongji",
    "level": 3,
    "area_code": 359,
    "post_code": 44500,
    "pid": 140800,
    "id": 302,
    "area_id": 140881
  },
  {
    "area_name": "河津市",
    "pinyin": "Hejin",
    "level": 3,
    "area_code": 359,
    "post_code": 43300,
    "pid": 140800,
    "id": 303,
    "area_id": 140882
  },
  {
    "area_name": "忻州市",
    "pinyin": "Xinzhou",
    "level": 2,
    "area_code": 350,
    "post_code": 34000,
    "pid": 140000,
    "id": 304,
    "area_id": 140900
  },
  {
    "area_name": "忻府区",
    "pinyin": "Xinfu",
    "level": 3,
    "area_code": 350,
    "post_code": 34000,
    "pid": 140900,
    "id": 305,
    "area_id": 140902
  },
  {
    "area_name": "定襄县",
    "pinyin": "Dingxiang",
    "level": 3,
    "area_code": 350,
    "post_code": 35400,
    "pid": 140900,
    "id": 306,
    "area_id": 140921
  },
  {
    "area_name": "五台县",
    "pinyin": "Wutai",
    "level": 3,
    "area_code": 350,
    "post_code": 35500,
    "pid": 140900,
    "id": 307,
    "area_id": 140922
  },
  {
    "area_name": "代县",
    "pinyin": "Daixian",
    "level": 3,
    "area_code": 350,
    "post_code": 34200,
    "pid": 140900,
    "id": 308,
    "area_id": 140923
  },
  {
    "area_name": "繁峙县",
    "pinyin": "Fanshi",
    "level": 3,
    "area_code": 350,
    "post_code": 34300,
    "pid": 140900,
    "id": 309,
    "area_id": 140924
  },
  {
    "area_name": "宁武县",
    "pinyin": "Ningwu",
    "level": 3,
    "area_code": 350,
    "post_code": 36700,
    "pid": 140900,
    "id": 310,
    "area_id": 140925
  },
  {
    "area_name": "静乐县",
    "pinyin": "Jingle",
    "level": 3,
    "area_code": 350,
    "post_code": 35100,
    "pid": 140900,
    "id": 311,
    "area_id": 140926
  },
  {
    "area_name": "神池县",
    "pinyin": "Shenchi",
    "level": 3,
    "area_code": 350,
    "post_code": 36100,
    "pid": 140900,
    "id": 312,
    "area_id": 140927
  },
  {
    "area_name": "五寨县",
    "pinyin": "Wuzhai",
    "level": 3,
    "area_code": 350,
    "post_code": 36200,
    "pid": 140900,
    "id": 313,
    "area_id": 140928
  },
  {
    "area_name": "岢岚县",
    "pinyin": "Kelan",
    "level": 3,
    "area_code": 350,
    "post_code": 36300,
    "pid": 140900,
    "id": 314,
    "area_id": 140929
  },
  {
    "area_name": "河曲县",
    "pinyin": "Hequ",
    "level": 3,
    "area_code": 350,
    "post_code": 36500,
    "pid": 140900,
    "id": 315,
    "area_id": 140930
  },
  {
    "area_name": "保德县",
    "pinyin": "Baode",
    "level": 3,
    "area_code": 350,
    "post_code": 36600,
    "pid": 140900,
    "id": 316,
    "area_id": 140931
  },
  {
    "area_name": "偏关县",
    "pinyin": "Pianguan",
    "level": 3,
    "area_code": 350,
    "post_code": 36400,
    "pid": 140900,
    "id": 317,
    "area_id": 140932
  },
  {
    "area_name": "原平市",
    "pinyin": "Yuanping",
    "level": 3,
    "area_code": 350,
    "post_code": 34100,
    "pid": 140900,
    "id": 318,
    "area_id": 140981
  },
  {
    "area_name": "临汾市",
    "pinyin": "Linfen",
    "level": 2,
    "area_code": 357,
    "post_code": 41000,
    "pid": 140000,
    "id": 319,
    "area_id": 141000
  },
  {
    "area_name": "尧都区",
    "pinyin": "Yaodu",
    "level": 3,
    "area_code": 357,
    "post_code": 41000,
    "pid": 141000,
    "id": 320,
    "area_id": 141002
  },
  {
    "area_name": "曲沃县",
    "pinyin": "Quwo",
    "level": 3,
    "area_code": 357,
    "post_code": 43400,
    "pid": 141000,
    "id": 321,
    "area_id": 141021
  },
  {
    "area_name": "翼城县",
    "pinyin": "Yicheng",
    "level": 3,
    "area_code": 357,
    "post_code": 43500,
    "pid": 141000,
    "id": 322,
    "area_id": 141022
  },
  {
    "area_name": "襄汾县",
    "pinyin": "Xiangfen",
    "level": 3,
    "area_code": 357,
    "post_code": 41500,
    "pid": 141000,
    "id": 323,
    "area_id": 141023
  },
  {
    "area_name": "洪洞县",
    "pinyin": "Hongtong",
    "level": 3,
    "area_code": 357,
    "post_code": 41600,
    "pid": 141000,
    "id": 324,
    "area_id": 141024
  },
  {
    "area_name": "古县",
    "pinyin": "Guxian",
    "level": 3,
    "area_code": 357,
    "post_code": 42400,
    "pid": 141000,
    "id": 325,
    "area_id": 141025
  },
  {
    "area_name": "安泽县",
    "pinyin": "Anze",
    "level": 3,
    "area_code": 357,
    "post_code": 42500,
    "pid": 141000,
    "id": 326,
    "area_id": 141026
  },
  {
    "area_name": "浮山县",
    "pinyin": "Fushan",
    "level": 3,
    "area_code": 357,
    "post_code": 42600,
    "pid": 141000,
    "id": 327,
    "area_id": 141027
  },
  {
    "area_name": "吉县",
    "pinyin": "Jixian",
    "level": 3,
    "area_code": 357,
    "post_code": 42200,
    "pid": 141000,
    "id": 328,
    "area_id": 141028
  },
  {
    "area_name": "乡宁县",
    "pinyin": "Xiangning",
    "level": 3,
    "area_code": 357,
    "post_code": 42100,
    "pid": 141000,
    "id": 329,
    "area_id": 141029
  },
  {
    "area_name": "大宁县",
    "pinyin": "Daning",
    "level": 3,
    "area_code": 357,
    "post_code": 42300,
    "pid": 141000,
    "id": 330,
    "area_id": 141030
  },
  {
    "area_name": "隰县",
    "pinyin": "Xixian",
    "level": 3,
    "area_code": 357,
    "post_code": 41300,
    "pid": 141000,
    "id": 331,
    "area_id": 141031
  },
  {
    "area_name": "永和县",
    "pinyin": "Yonghe",
    "level": 3,
    "area_code": 357,
    "post_code": 41400,
    "pid": 141000,
    "id": 332,
    "area_id": 141032
  },
  {
    "area_name": "蒲县",
    "pinyin": "Puxian",
    "level": 3,
    "area_code": 357,
    "post_code": 41200,
    "pid": 141000,
    "id": 333,
    "area_id": 141033
  },
  {
    "area_name": "汾西县",
    "pinyin": "Fenxi",
    "level": 3,
    "area_code": 357,
    "post_code": 31500,
    "pid": 141000,
    "id": 334,
    "area_id": 141034
  },
  {
    "area_name": "侯马市",
    "pinyin": "Houma",
    "level": 3,
    "area_code": 357,
    "post_code": 43000,
    "pid": 141000,
    "id": 335,
    "area_id": 141081
  },
  {
    "area_name": "霍州市",
    "pinyin": "Huozhou",
    "level": 3,
    "area_code": 357,
    "post_code": 31400,
    "pid": 141000,
    "id": 336,
    "area_id": 141082
  },
  {
    "area_name": "吕梁市",
    "pinyin": "Lvliang",
    "level": 2,
    "area_code": 358,
    "post_code": 33000,
    "pid": 140000,
    "id": 337,
    "area_id": 141100
  },
  {
    "area_name": "离石区",
    "pinyin": "Lishi",
    "level": 3,
    "area_code": 358,
    "post_code": 33000,
    "pid": 141100,
    "id": 338,
    "area_id": 141102
  },
  {
    "area_name": "文水县",
    "pinyin": "Wenshui",
    "level": 3,
    "area_code": 358,
    "post_code": 32100,
    "pid": 141100,
    "id": 339,
    "area_id": 141121
  },
  {
    "area_name": "交城县",
    "pinyin": "Jiaocheng",
    "level": 3,
    "area_code": 358,
    "post_code": 30500,
    "pid": 141100,
    "id": 340,
    "area_id": 141122
  },
  {
    "area_name": "兴县",
    "pinyin": "Xingxian",
    "level": 3,
    "area_code": 358,
    "post_code": 33600,
    "pid": 141100,
    "id": 341,
    "area_id": 141123
  },
  {
    "area_name": "临县",
    "pinyin": "Linxian",
    "level": 3,
    "area_code": 358,
    "post_code": 33200,
    "pid": 141100,
    "id": 342,
    "area_id": 141124
  },
  {
    "area_name": "柳林县",
    "pinyin": "Liulin",
    "level": 3,
    "area_code": 358,
    "post_code": 33300,
    "pid": 141100,
    "id": 343,
    "area_id": 141125
  },
  {
    "area_name": "石楼县",
    "pinyin": "Shilou",
    "level": 3,
    "area_code": 358,
    "post_code": 32500,
    "pid": 141100,
    "id": 344,
    "area_id": 141126
  },
  {
    "area_name": "岚县",
    "pinyin": "Lanxian",
    "level": 3,
    "area_code": 358,
    "post_code": 33500,
    "pid": 141100,
    "id": 345,
    "area_id": 141127
  },
  {
    "area_name": "方山县",
    "pinyin": "Fangshan",
    "level": 3,
    "area_code": 358,
    "post_code": 33100,
    "pid": 141100,
    "id": 346,
    "area_id": 141128
  },
  {
    "area_name": "中阳县",
    "pinyin": "Zhongyang",
    "level": 3,
    "area_code": 358,
    "post_code": 33400,
    "pid": 141100,
    "id": 347,
    "area_id": 141129
  },
  {
    "area_name": "交口县",
    "pinyin": "Jiaokou",
    "level": 3,
    "area_code": 358,
    "post_code": 32400,
    "pid": 141100,
    "id": 348,
    "area_id": 141130
  },
  {
    "area_name": "孝义市",
    "pinyin": "Xiaoyi",
    "level": 3,
    "area_code": 358,
    "post_code": 32300,
    "pid": 141100,
    "id": 349,
    "area_id": 141181
  },
  {
    "area_name": "汾阳市",
    "pinyin": "Fenyang",
    "level": 3,
    "area_code": 358,
    "post_code": 32200,
    "pid": 141100,
    "id": 350,
    "area_id": 141182
  },
  {
    "area_name": "内蒙古自治区",
    "pinyin": "Inner Mongolia",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 351,
    "area_id": 150000
  },
  {
    "area_name": "呼和浩特市",
    "pinyin": "Hohhot",
    "level": 2,
    "area_code": 471,
    "post_code": 10000,
    "pid": 150000,
    "id": 352,
    "area_id": 150100
  },
  {
    "area_name": "新城区",
    "pinyin": "Xincheng",
    "level": 3,
    "area_code": 471,
    "post_code": 10050,
    "pid": 150100,
    "id": 353,
    "area_id": 150102
  },
  {
    "area_name": "回民区",
    "pinyin": "Huimin",
    "level": 3,
    "area_code": 471,
    "post_code": 10030,
    "pid": 150100,
    "id": 354,
    "area_id": 150103
  },
  {
    "area_name": "玉泉区",
    "pinyin": "Yuquan",
    "level": 3,
    "area_code": 471,
    "post_code": 10020,
    "pid": 150100,
    "id": 355,
    "area_id": 150104
  },
  {
    "area_name": "赛罕区",
    "pinyin": "Saihan",
    "level": 3,
    "area_code": 471,
    "post_code": 10020,
    "pid": 150100,
    "id": 356,
    "area_id": 150105
  },
  {
    "area_name": "土默特左旗",
    "pinyin": "Tumotezuoqi",
    "level": 3,
    "area_code": 471,
    "post_code": 10100,
    "pid": 150100,
    "id": 357,
    "area_id": 150121
  },
  {
    "area_name": "托克托县",
    "pinyin": "Tuoketuo",
    "level": 3,
    "area_code": 471,
    "post_code": 10200,
    "pid": 150100,
    "id": 358,
    "area_id": 150122
  },
  {
    "area_name": "和林格尔县",
    "pinyin": "Helingeer",
    "level": 3,
    "area_code": 471,
    "post_code": 11500,
    "pid": 150100,
    "id": 359,
    "area_id": 150123
  },
  {
    "area_name": "清水河县",
    "pinyin": "Qingshuihe",
    "level": 3,
    "area_code": 471,
    "post_code": 11600,
    "pid": 150100,
    "id": 360,
    "area_id": 150124
  },
  {
    "area_name": "武川县",
    "pinyin": "Wuchuan",
    "level": 3,
    "area_code": 471,
    "post_code": 11700,
    "pid": 150100,
    "id": 361,
    "area_id": 150125
  },
  {
    "area_name": "包头市",
    "pinyin": "Baotou",
    "level": 2,
    "area_code": 472,
    "post_code": 14025,
    "pid": 150000,
    "id": 362,
    "area_id": 150200
  },
  {
    "area_name": "东河区",
    "pinyin": "Donghe",
    "level": 3,
    "area_code": 472,
    "post_code": 14040,
    "pid": 150200,
    "id": 363,
    "area_id": 150202
  },
  {
    "area_name": "昆都仑区",
    "pinyin": "Kundulun",
    "level": 3,
    "area_code": 472,
    "post_code": 14010,
    "pid": 150200,
    "id": 364,
    "area_id": 150203
  },
  {
    "area_name": "青山区",
    "pinyin": "Qingshan",
    "level": 3,
    "area_code": 472,
    "post_code": 14030,
    "pid": 150200,
    "id": 365,
    "area_id": 150204
  },
  {
    "area_name": "石拐区",
    "pinyin": "Shiguai",
    "level": 3,
    "area_code": 472,
    "post_code": 14070,
    "pid": 150200,
    "id": 366,
    "area_id": 150205
  },
  {
    "area_name": "白云鄂博矿区",
    "pinyin": "Baiyunebokuangqu",
    "level": 3,
    "area_code": 472,
    "post_code": 14080,
    "pid": 150200,
    "id": 367,
    "area_id": 150206
  },
  {
    "area_name": "九原区",
    "pinyin": "Jiuyuan",
    "level": 3,
    "area_code": 472,
    "post_code": 14060,
    "pid": 150200,
    "id": 368,
    "area_id": 150207
  },
  {
    "area_name": "土默特右旗",
    "pinyin": "Tumoteyouqi",
    "level": 3,
    "area_code": 472,
    "post_code": 14100,
    "pid": 150200,
    "id": 369,
    "area_id": 150221
  },
  {
    "area_name": "固阳县",
    "pinyin": "Guyang",
    "level": 3,
    "area_code": 472,
    "post_code": 14200,
    "pid": 150200,
    "id": 370,
    "area_id": 150222
  },
  {
    "area_name": "达尔罕茂明安联合旗",
    "pinyin": "Damaoqi",
    "level": 3,
    "area_code": 472,
    "post_code": 14500,
    "pid": 150200,
    "id": 371,
    "area_id": 150223
  },
  {
    "area_name": "乌海市",
    "pinyin": "Wuhai",
    "level": 2,
    "area_code": 473,
    "post_code": 16000,
    "pid": 150000,
    "id": 372,
    "area_id": 150300
  },
  {
    "area_name": "海勃湾区",
    "pinyin": "Haibowan",
    "level": 3,
    "area_code": 473,
    "post_code": 16000,
    "pid": 150300,
    "id": 373,
    "area_id": 150302
  },
  {
    "area_name": "海南区",
    "pinyin": "Hainan",
    "level": 3,
    "area_code": 473,
    "post_code": 16030,
    "pid": 150300,
    "id": 374,
    "area_id": 150303
  },
  {
    "area_name": "乌达区",
    "pinyin": "Wuda",
    "level": 3,
    "area_code": 473,
    "post_code": 16040,
    "pid": 150300,
    "id": 375,
    "area_id": 150304
  },
  {
    "area_name": "赤峰市",
    "pinyin": "Chifeng",
    "level": 2,
    "area_code": 476,
    "post_code": 24000,
    "pid": 150000,
    "id": 376,
    "area_id": 150400
  },
  {
    "area_name": "红山区",
    "pinyin": "Hongshan",
    "level": 3,
    "area_code": 476,
    "post_code": 24020,
    "pid": 150400,
    "id": 377,
    "area_id": 150402
  },
  {
    "area_name": "元宝山区",
    "pinyin": "Yuanbaoshan",
    "level": 3,
    "area_code": 476,
    "post_code": 24076,
    "pid": 150400,
    "id": 378,
    "area_id": 150403
  },
  {
    "area_name": "松山区",
    "pinyin": "Songshan",
    "level": 3,
    "area_code": 476,
    "post_code": 24005,
    "pid": 150400,
    "id": 379,
    "area_id": 150404
  },
  {
    "area_name": "阿鲁科尔沁旗",
    "pinyin": "Alukeerqinqi",
    "level": 3,
    "area_code": 476,
    "post_code": 25550,
    "pid": 150400,
    "id": 380,
    "area_id": 150421
  },
  {
    "area_name": "巴林左旗",
    "pinyin": "Balinzuoqi",
    "level": 3,
    "area_code": 476,
    "post_code": 25450,
    "pid": 150400,
    "id": 381,
    "area_id": 150422
  },
  {
    "area_name": "巴林右旗",
    "pinyin": "Balinyouqi",
    "level": 3,
    "area_code": 476,
    "post_code": 25150,
    "pid": 150400,
    "id": 382,
    "area_id": 150423
  },
  {
    "area_name": "林西县",
    "pinyin": "Linxi",
    "level": 3,
    "area_code": 476,
    "post_code": 25250,
    "pid": 150400,
    "id": 383,
    "area_id": 150424
  },
  {
    "area_name": "克什克腾旗",
    "pinyin": "Keshiketengqi",
    "level": 3,
    "area_code": 476,
    "post_code": 25350,
    "pid": 150400,
    "id": 384,
    "area_id": 150425
  },
  {
    "area_name": "翁牛特旗",
    "pinyin": "Wengniuteqi",
    "level": 3,
    "area_code": 476,
    "post_code": 24500,
    "pid": 150400,
    "id": 385,
    "area_id": 150426
  },
  {
    "area_name": "喀喇沁旗",
    "pinyin": "Kalaqinqi",
    "level": 3,
    "area_code": 476,
    "post_code": 24400,
    "pid": 150400,
    "id": 386,
    "area_id": 150428
  },
  {
    "area_name": "宁城县",
    "pinyin": "Ningcheng",
    "level": 3,
    "area_code": 476,
    "post_code": 24200,
    "pid": 150400,
    "id": 387,
    "area_id": 150429
  },
  {
    "area_name": "敖汉旗",
    "pinyin": "Aohanqi",
    "level": 3,
    "area_code": 476,
    "post_code": 24300,
    "pid": 150400,
    "id": 388,
    "area_id": 150430
  },
  {
    "area_name": "通辽市",
    "pinyin": "Tongliao",
    "level": 2,
    "area_code": 475,
    "post_code": 28000,
    "pid": 150000,
    "id": 389,
    "area_id": 150500
  },
  {
    "area_name": "科尔沁区",
    "pinyin": "Keerqin",
    "level": 3,
    "area_code": 475,
    "post_code": 28000,
    "pid": 150500,
    "id": 390,
    "area_id": 150502
  },
  {
    "area_name": "科尔沁左翼中旗",
    "pinyin": "Keerqinzuoyizhongqi",
    "level": 3,
    "area_code": 475,
    "post_code": 29300,
    "pid": 150500,
    "id": 391,
    "area_id": 150521
  },
  {
    "area_name": "科尔沁左翼后旗",
    "pinyin": "Keerqinzuoyihouqi",
    "level": 3,
    "area_code": 475,
    "post_code": 28100,
    "pid": 150500,
    "id": 392,
    "area_id": 150522
  },
  {
    "area_name": "开鲁县",
    "pinyin": "Kailu",
    "level": 3,
    "area_code": 475,
    "post_code": 28400,
    "pid": 150500,
    "id": 393,
    "area_id": 150523
  },
  {
    "area_name": "库伦旗",
    "pinyin": "Kulunqi",
    "level": 3,
    "area_code": 475,
    "post_code": 28200,
    "pid": 150500,
    "id": 394,
    "area_id": 150524
  },
  {
    "area_name": "奈曼旗",
    "pinyin": "Naimanqi",
    "level": 3,
    "area_code": 475,
    "post_code": 28300,
    "pid": 150500,
    "id": 395,
    "area_id": 150525
  },
  {
    "area_name": "扎鲁特旗",
    "pinyin": "Zhaluteqi",
    "level": 3,
    "area_code": 475,
    "post_code": 29100,
    "pid": 150500,
    "id": 396,
    "area_id": 150526
  },
  {
    "area_name": "霍林郭勒市",
    "pinyin": "Huolinguole",
    "level": 3,
    "area_code": 475,
    "post_code": 29200,
    "pid": 150500,
    "id": 397,
    "area_id": 150581
  },
  {
    "area_name": "鄂尔多斯市",
    "pinyin": "Ordos",
    "level": 2,
    "area_code": 477,
    "post_code": 17004,
    "pid": 150000,
    "id": 398,
    "area_id": 150600
  },
  {
    "area_name": "东胜区",
    "pinyin": "Dongsheng",
    "level": 3,
    "area_code": 477,
    "post_code": 17000,
    "pid": 150600,
    "id": 399,
    "area_id": 150602
  },
  {
    "area_name": "达拉特旗",
    "pinyin": "Dalateqi",
    "level": 3,
    "area_code": 477,
    "post_code": 14300,
    "pid": 150600,
    "id": 400,
    "area_id": 150621
  },
  {
    "area_name": "准格尔旗",
    "pinyin": "Zhungeerqi",
    "level": 3,
    "area_code": 477,
    "post_code": 17100,
    "pid": 150600,
    "id": 401,
    "area_id": 150622
  },
  {
    "area_name": "鄂托克前旗",
    "pinyin": "Etuokeqianqi",
    "level": 3,
    "area_code": 477,
    "post_code": 16200,
    "pid": 150600,
    "id": 402,
    "area_id": 150623
  },
  {
    "area_name": "鄂托克旗",
    "pinyin": "Etuokeqi",
    "level": 3,
    "area_code": 477,
    "post_code": 16100,
    "pid": 150600,
    "id": 403,
    "area_id": 150624
  },
  {
    "area_name": "杭锦旗",
    "pinyin": "Hangjinqi",
    "level": 3,
    "area_code": 477,
    "post_code": 17400,
    "pid": 150600,
    "id": 404,
    "area_id": 150625
  },
  {
    "area_name": "乌审旗",
    "pinyin": "Wushenqi",
    "level": 3,
    "area_code": 477,
    "post_code": 17300,
    "pid": 150600,
    "id": 405,
    "area_id": 150626
  },
  {
    "area_name": "伊金霍洛旗",
    "pinyin": "Yijinhuoluoqi",
    "level": 3,
    "area_code": 477,
    "post_code": 17200,
    "pid": 150600,
    "id": 406,
    "area_id": 150627
  },
  {
    "area_name": "呼伦贝尔市",
    "pinyin": "Hulunber",
    "level": 2,
    "area_code": 470,
    "post_code": 21008,
    "pid": 150000,
    "id": 407,
    "area_id": 150700
  },
  {
    "area_name": "海拉尔区",
    "pinyin": "Hailaer",
    "level": 3,
    "area_code": 470,
    "post_code": 21000,
    "pid": 150700,
    "id": 408,
    "area_id": 150702
  },
  {
    "area_name": "扎赉诺尔区",
    "pinyin": "Zhalainuoer",
    "level": 3,
    "area_code": 470,
    "post_code": 21410,
    "pid": 150700,
    "id": 409,
    "area_id": 150703
  },
  {
    "area_name": "阿荣旗",
    "pinyin": "Arongqi",
    "level": 3,
    "area_code": 470,
    "post_code": 162750,
    "pid": 150700,
    "id": 410,
    "area_id": 150721
  },
  {
    "area_name": "莫力达瓦达斡尔族自治旗",
    "pinyin": "Moqi",
    "level": 3,
    "area_code": 470,
    "post_code": 162850,
    "pid": 150700,
    "id": 411,
    "area_id": 150722
  },
  {
    "area_name": "鄂伦春自治旗",
    "pinyin": "Elunchun",
    "level": 3,
    "area_code": 470,
    "post_code": 165450,
    "pid": 150700,
    "id": 412,
    "area_id": 150723
  },
  {
    "area_name": "鄂温克族自治旗",
    "pinyin": "Ewen",
    "level": 3,
    "area_code": 470,
    "post_code": 21100,
    "pid": 150700,
    "id": 413,
    "area_id": 150724
  },
  {
    "area_name": "陈巴尔虎旗",
    "pinyin": "Chenbaerhuqi",
    "level": 3,
    "area_code": 470,
    "post_code": 21500,
    "pid": 150700,
    "id": 414,
    "area_id": 150725
  },
  {
    "area_name": "新巴尔虎左旗",
    "pinyin": "Xinbaerhuzuoqi",
    "level": 3,
    "area_code": 470,
    "post_code": 21200,
    "pid": 150700,
    "id": 415,
    "area_id": 150726
  },
  {
    "area_name": "新巴尔虎右旗",
    "pinyin": "Xinbaerhuyouqi",
    "level": 3,
    "area_code": 470,
    "post_code": 21300,
    "pid": 150700,
    "id": 416,
    "area_id": 150727
  },
  {
    "area_name": "满洲里市",
    "pinyin": "Manzhouli",
    "level": 3,
    "area_code": 470,
    "post_code": 21400,
    "pid": 150700,
    "id": 417,
    "area_id": 150781
  },
  {
    "area_name": "牙克石市",
    "pinyin": "Yakeshi",
    "level": 3,
    "area_code": 470,
    "post_code": 22150,
    "pid": 150700,
    "id": 418,
    "area_id": 150782
  },
  {
    "area_name": "扎兰屯市",
    "pinyin": "Zhalantun",
    "level": 3,
    "area_code": 470,
    "post_code": 162650,
    "pid": 150700,
    "id": 419,
    "area_id": 150783
  },
  {
    "area_name": "额尔古纳市",
    "pinyin": "Eerguna",
    "level": 3,
    "area_code": 470,
    "post_code": 22250,
    "pid": 150700,
    "id": 420,
    "area_id": 150784
  },
  {
    "area_name": "根河市",
    "pinyin": "Genhe",
    "level": 3,
    "area_code": 470,
    "post_code": 22350,
    "pid": 150700,
    "id": 421,
    "area_id": 150785
  },
  {
    "area_name": "巴彦淖尔市",
    "pinyin": "Bayan Nur",
    "level": 2,
    "area_code": 478,
    "post_code": 15001,
    "pid": 150000,
    "id": 422,
    "area_id": 150800
  },
  {
    "area_name": "临河区",
    "pinyin": "Linhe",
    "level": 3,
    "area_code": 478,
    "post_code": 15001,
    "pid": 150800,
    "id": 423,
    "area_id": 150802
  },
  {
    "area_name": "五原县",
    "pinyin": "Wuyuan",
    "level": 3,
    "area_code": 478,
    "post_code": 15100,
    "pid": 150800,
    "id": 424,
    "area_id": 150821
  },
  {
    "area_name": "磴口县",
    "pinyin": "Dengkou",
    "level": 3,
    "area_code": 478,
    "post_code": 15200,
    "pid": 150800,
    "id": 425,
    "area_id": 150822
  },
  {
    "area_name": "乌拉特前旗",
    "pinyin": "Wulateqianqi",
    "level": 3,
    "area_code": 478,
    "post_code": 14400,
    "pid": 150800,
    "id": 426,
    "area_id": 150823
  },
  {
    "area_name": "乌拉特中旗",
    "pinyin": "Wulatezhongqi",
    "level": 3,
    "area_code": 478,
    "post_code": 15300,
    "pid": 150800,
    "id": 427,
    "area_id": 150824
  },
  {
    "area_name": "乌拉特后旗",
    "pinyin": "Wulatehouqi",
    "level": 3,
    "area_code": 478,
    "post_code": 15500,
    "pid": 150800,
    "id": 428,
    "area_id": 150825
  },
  {
    "area_name": "杭锦后旗",
    "pinyin": "Hangjinhouqi",
    "level": 3,
    "area_code": 478,
    "post_code": 15400,
    "pid": 150800,
    "id": 429,
    "area_id": 150826
  },
  {
    "area_name": "乌兰察布市",
    "pinyin": "Ulanqab",
    "level": 2,
    "area_code": 474,
    "post_code": 12000,
    "pid": 150000,
    "id": 430,
    "area_id": 150900
  },
  {
    "area_name": "集宁区",
    "pinyin": "Jining",
    "level": 3,
    "area_code": 474,
    "post_code": 12000,
    "pid": 150900,
    "id": 431,
    "area_id": 150902
  },
  {
    "area_name": "卓资县",
    "pinyin": "Zhuozi",
    "level": 3,
    "area_code": 474,
    "post_code": 12300,
    "pid": 150900,
    "id": 432,
    "area_id": 150921
  },
  {
    "area_name": "化德县",
    "pinyin": "Huade",
    "level": 3,
    "area_code": 474,
    "post_code": 13350,
    "pid": 150900,
    "id": 433,
    "area_id": 150922
  },
  {
    "area_name": "商都县",
    "pinyin": "Shangdu",
    "level": 3,
    "area_code": 474,
    "post_code": 13450,
    "pid": 150900,
    "id": 434,
    "area_id": 150923
  },
  {
    "area_name": "兴和县",
    "pinyin": "Xinghe",
    "level": 3,
    "area_code": 474,
    "post_code": 13650,
    "pid": 150900,
    "id": 435,
    "area_id": 150924
  },
  {
    "area_name": "凉城县",
    "pinyin": "Liangcheng",
    "level": 3,
    "area_code": 474,
    "post_code": 13750,
    "pid": 150900,
    "id": 436,
    "area_id": 150925
  },
  {
    "area_name": "察哈尔右翼前旗",
    "pinyin": "Chayouqianqi",
    "level": 3,
    "area_code": 474,
    "post_code": 12200,
    "pid": 150900,
    "id": 437,
    "area_id": 150926
  },
  {
    "area_name": "察哈尔右翼中旗",
    "pinyin": "Chayouzhongqi",
    "level": 3,
    "area_code": 474,
    "post_code": 13550,
    "pid": 150900,
    "id": 438,
    "area_id": 150927
  },
  {
    "area_name": "察哈尔右翼后旗",
    "pinyin": "Chayouhouqi",
    "level": 3,
    "area_code": 474,
    "post_code": 12400,
    "pid": 150900,
    "id": 439,
    "area_id": 150928
  },
  {
    "area_name": "四子王旗",
    "pinyin": "Siziwangqi",
    "level": 3,
    "area_code": 474,
    "post_code": 11800,
    "pid": 150900,
    "id": 440,
    "area_id": 150929
  },
  {
    "area_name": "丰镇市",
    "pinyin": "Fengzhen",
    "level": 3,
    "area_code": 474,
    "post_code": 12100,
    "pid": 150900,
    "id": 441,
    "area_id": 150981
  },
  {
    "area_name": "兴安盟",
    "pinyin": "Hinggan",
    "level": 2,
    "area_code": 482,
    "post_code": 137401,
    "pid": 150000,
    "id": 442,
    "area_id": 152200
  },
  {
    "area_name": "乌兰浩特市",
    "pinyin": "Wulanhaote",
    "level": 3,
    "area_code": 482,
    "post_code": 137401,
    "pid": 152200,
    "id": 443,
    "area_id": 152201
  },
  {
    "area_name": "阿尔山市",
    "pinyin": "Aershan",
    "level": 3,
    "area_code": 482,
    "post_code": 137800,
    "pid": 152200,
    "id": 444,
    "area_id": 152202
  },
  {
    "area_name": "科尔沁右翼前旗",
    "pinyin": "Keyouqianqi",
    "level": 3,
    "area_code": 482,
    "post_code": 137423,
    "pid": 152200,
    "id": 445,
    "area_id": 152221
  },
  {
    "area_name": "科尔沁右翼中旗",
    "pinyin": "Keyouzhongqi",
    "level": 3,
    "area_code": 482,
    "post_code": 29400,
    "pid": 152200,
    "id": 446,
    "area_id": 152222
  },
  {
    "area_name": "扎赉特旗",
    "pinyin": "Zhalaiteqi",
    "level": 3,
    "area_code": 482,
    "post_code": 137600,
    "pid": 152200,
    "id": 447,
    "area_id": 152223
  },
  {
    "area_name": "突泉县",
    "pinyin": "Tuquan",
    "level": 3,
    "area_code": 482,
    "post_code": 137500,
    "pid": 152200,
    "id": 448,
    "area_id": 152224
  },
  {
    "area_name": "锡林郭勒盟",
    "pinyin": "Xilin Gol",
    "level": 2,
    "area_code": 479,
    "post_code": 26000,
    "pid": 150000,
    "id": 449,
    "area_id": 152500
  },
  {
    "area_name": "二连浩特市",
    "pinyin": "Erlianhaote",
    "level": 3,
    "area_code": 479,
    "post_code": 11100,
    "pid": 152500,
    "id": 450,
    "area_id": 152501
  },
  {
    "area_name": "锡林浩特市",
    "pinyin": "Xilinhaote",
    "level": 3,
    "area_code": 479,
    "post_code": 26021,
    "pid": 152500,
    "id": 451,
    "area_id": 152502
  },
  {
    "area_name": "阿巴嘎旗",
    "pinyin": "Abagaqi",
    "level": 3,
    "area_code": 479,
    "post_code": 11400,
    "pid": 152500,
    "id": 452,
    "area_id": 152522
  },
  {
    "area_name": "苏尼特左旗",
    "pinyin": "Sunitezuoqi",
    "level": 3,
    "area_code": 479,
    "post_code": 11300,
    "pid": 152500,
    "id": 453,
    "area_id": 152523
  },
  {
    "area_name": "苏尼特右旗",
    "pinyin": "Suniteyouqi",
    "level": 3,
    "area_code": 479,
    "post_code": 11200,
    "pid": 152500,
    "id": 454,
    "area_id": 152524
  },
  {
    "area_name": "东乌珠穆沁旗",
    "pinyin": "Dongwuqi",
    "level": 3,
    "area_code": 479,
    "post_code": 26300,
    "pid": 152500,
    "id": 455,
    "area_id": 152525
  },
  {
    "area_name": "西乌珠穆沁旗",
    "pinyin": "Xiwuqi",
    "level": 3,
    "area_code": 479,
    "post_code": 26200,
    "pid": 152500,
    "id": 456,
    "area_id": 152526
  },
  {
    "area_name": "太仆寺旗",
    "pinyin": "Taipusiqi",
    "level": 3,
    "area_code": 479,
    "post_code": 27000,
    "pid": 152500,
    "id": 457,
    "area_id": 152527
  },
  {
    "area_name": "镶黄旗",
    "pinyin": "Xianghuangqi",
    "level": 3,
    "area_code": 479,
    "post_code": 13250,
    "pid": 152500,
    "id": 458,
    "area_id": 152528
  },
  {
    "area_name": "正镶白旗",
    "pinyin": "Zhengxiangbaiqi",
    "level": 3,
    "area_code": 479,
    "post_code": 13800,
    "pid": 152500,
    "id": 459,
    "area_id": 152529
  },
  {
    "area_name": "正蓝旗",
    "pinyin": "Zhenglanqi",
    "level": 3,
    "area_code": 479,
    "post_code": 27200,
    "pid": 152500,
    "id": 460,
    "area_id": 152530
  },
  {
    "area_name": "多伦县",
    "pinyin": "Duolun",
    "level": 3,
    "area_code": 479,
    "post_code": 27300,
    "pid": 152500,
    "id": 461,
    "area_id": 152531
  },
  {
    "area_name": "阿拉善盟",
    "pinyin": "Alxa",
    "level": 2,
    "area_code": 483,
    "post_code": 750306,
    "pid": 150000,
    "id": 462,
    "area_id": 152900
  },
  {
    "area_name": "阿拉善左旗",
    "pinyin": "Alashanzuoqi",
    "level": 3,
    "area_code": 483,
    "post_code": 750306,
    "pid": 152900,
    "id": 463,
    "area_id": 152921
  },
  {
    "area_name": "阿拉善右旗",
    "pinyin": "Alashanyouqi",
    "level": 3,
    "area_code": 483,
    "post_code": 737300,
    "pid": 152900,
    "id": 464,
    "area_id": 152922
  },
  {
    "area_name": "额济纳旗",
    "pinyin": "Ejinaqi",
    "level": 3,
    "area_code": 483,
    "post_code": 735400,
    "pid": 152900,
    "id": 465,
    "area_id": 152923
  },
  {
    "area_name": "辽宁省",
    "pinyin": "Liaoning",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 466,
    "area_id": 210000
  },
  {
    "area_name": "沈阳市",
    "pinyin": "Shenyang",
    "level": 2,
    "area_code": 24,
    "post_code": 110013,
    "pid": 210000,
    "id": 467,
    "area_id": 210100
  },
  {
    "area_name": "和平区",
    "pinyin": "Heping",
    "level": 3,
    "area_code": 24,
    "post_code": 110001,
    "pid": 210100,
    "id": 468,
    "area_id": 210102
  },
  {
    "area_name": "沈河区",
    "pinyin": "Shenhe",
    "level": 3,
    "area_code": 24,
    "post_code": 110011,
    "pid": 210100,
    "id": 469,
    "area_id": 210103
  },
  {
    "area_name": "大东区",
    "pinyin": "Dadong",
    "level": 3,
    "area_code": 24,
    "post_code": 110041,
    "pid": 210100,
    "id": 470,
    "area_id": 210104
  },
  {
    "area_name": "皇姑区",
    "pinyin": "Huanggu",
    "level": 3,
    "area_code": 24,
    "post_code": 110031,
    "pid": 210100,
    "id": 471,
    "area_id": 210105
  },
  {
    "area_name": "铁西区",
    "pinyin": "Tiexi",
    "level": 3,
    "area_code": 24,
    "post_code": 110021,
    "pid": 210100,
    "id": 472,
    "area_id": 210106
  },
  {
    "area_name": "苏家屯区",
    "pinyin": "Sujiatun",
    "level": 3,
    "area_code": 24,
    "post_code": 110101,
    "pid": 210100,
    "id": 473,
    "area_id": 210111
  },
  {
    "area_name": "浑南区",
    "pinyin": "Hunnan",
    "level": 3,
    "area_code": 24,
    "post_code": 110015,
    "pid": 210100,
    "id": 474,
    "area_id": 210112
  },
  {
    "area_name": "沈北新区",
    "pinyin": "Shenbeixinqu",
    "level": 3,
    "area_code": 24,
    "post_code": 110121,
    "pid": 210100,
    "id": 475,
    "area_id": 210113
  },
  {
    "area_name": "于洪区",
    "pinyin": "Yuhong",
    "level": 3,
    "area_code": 24,
    "post_code": 110141,
    "pid": 210100,
    "id": 476,
    "area_id": 210114
  },
  {
    "area_name": "辽中县",
    "pinyin": "Liaozhong",
    "level": 3,
    "area_code": 24,
    "post_code": 110200,
    "pid": 210100,
    "id": 477,
    "area_id": 210122
  },
  {
    "area_name": "康平县",
    "pinyin": "Kangping",
    "level": 3,
    "area_code": 24,
    "post_code": 110500,
    "pid": 210100,
    "id": 478,
    "area_id": 210123
  },
  {
    "area_name": "法库县",
    "pinyin": "Faku",
    "level": 3,
    "area_code": 24,
    "post_code": 110400,
    "pid": 210100,
    "id": 479,
    "area_id": 210124
  },
  {
    "area_name": "新民市",
    "pinyin": "Xinmin",
    "level": 3,
    "area_code": 24,
    "post_code": 110300,
    "pid": 210100,
    "id": 480,
    "area_id": 210181
  },
  {
    "area_name": "大连市",
    "pinyin": "Dalian",
    "level": 2,
    "area_code": 411,
    "post_code": 116011,
    "pid": 210000,
    "id": 481,
    "area_id": 210200
  },
  {
    "area_name": "中山区",
    "pinyin": "Zhongshan",
    "level": 3,
    "area_code": 411,
    "post_code": 116001,
    "pid": 210200,
    "id": 482,
    "area_id": 210202
  },
  {
    "area_name": "西岗区",
    "pinyin": "Xigang",
    "level": 3,
    "area_code": 411,
    "post_code": 116011,
    "pid": 210200,
    "id": 483,
    "area_id": 210203
  },
  {
    "area_name": "沙河口区",
    "pinyin": "Shahekou",
    "level": 3,
    "area_code": 411,
    "post_code": 116021,
    "pid": 210200,
    "id": 484,
    "area_id": 210204
  },
  {
    "area_name": "甘井子区",
    "pinyin": "Ganjingzi",
    "level": 3,
    "area_code": 411,
    "post_code": 116033,
    "pid": 210200,
    "id": 485,
    "area_id": 210211
  },
  {
    "area_name": "旅顺口区",
    "pinyin": "Lvshunkou",
    "level": 3,
    "area_code": 411,
    "post_code": 116041,
    "pid": 210200,
    "id": 486,
    "area_id": 210212
  },
  {
    "area_name": "金州区",
    "pinyin": "Jinzhou",
    "level": 3,
    "area_code": 411,
    "post_code": 116100,
    "pid": 210200,
    "id": 487,
    "area_id": 210213
  },
  {
    "area_name": "长海县",
    "pinyin": "Changhai",
    "level": 3,
    "area_code": 411,
    "post_code": 116500,
    "pid": 210200,
    "id": 488,
    "area_id": 210224
  },
  {
    "area_name": "瓦房店市",
    "pinyin": "Wafangdian",
    "level": 3,
    "area_code": 411,
    "post_code": 116300,
    "pid": 210200,
    "id": 489,
    "area_id": 210281
  },
  {
    "area_name": "普兰店市",
    "pinyin": "Pulandian",
    "level": 3,
    "area_code": 411,
    "post_code": 116200,
    "pid": 210200,
    "id": 490,
    "area_id": 210282
  },
  {
    "area_name": "庄河市",
    "pinyin": "Zhuanghe",
    "level": 3,
    "area_code": 411,
    "post_code": 116400,
    "pid": 210200,
    "id": 491,
    "area_id": 210283
  },
  {
    "area_name": "鞍山市",
    "pinyin": "Anshan",
    "level": 2,
    "area_code": 412,
    "post_code": 114001,
    "pid": 210000,
    "id": 492,
    "area_id": 210300
  },
  {
    "area_name": "铁东区",
    "pinyin": "Tiedong",
    "level": 3,
    "area_code": 412,
    "post_code": 114001,
    "pid": 210300,
    "id": 493,
    "area_id": 210302
  },
  {
    "area_name": "铁西区",
    "pinyin": "Tiexi",
    "level": 3,
    "area_code": 413,
    "post_code": 114013,
    "pid": 210300,
    "id": 494,
    "area_id": 210303
  },
  {
    "area_name": "立山区",
    "pinyin": "Lishan",
    "level": 3,
    "area_code": 414,
    "post_code": 114031,
    "pid": 210300,
    "id": 495,
    "area_id": 210304
  },
  {
    "area_name": "千山区",
    "pinyin": "Qianshan",
    "level": 3,
    "area_code": 415,
    "post_code": 114041,
    "pid": 210300,
    "id": 496,
    "area_id": 210311
  },
  {
    "area_name": "台安县",
    "pinyin": "Tai'an",
    "level": 3,
    "area_code": 417,
    "post_code": 114100,
    "pid": 210300,
    "id": 497,
    "area_id": 210321
  },
  {
    "area_name": "岫岩满族自治县",
    "pinyin": "Xiuyan",
    "level": 3,
    "area_code": 418,
    "post_code": 114300,
    "pid": 210300,
    "id": 498,
    "area_id": 210323
  },
  {
    "area_name": "海城市",
    "pinyin": "Haicheng",
    "level": 3,
    "area_code": 416,
    "post_code": 114200,
    "pid": 210300,
    "id": 499,
    "area_id": 210381
  },
  {
    "area_name": "抚顺市",
    "pinyin": "Fushun",
    "level": 2,
    "area_code": 24,
    "post_code": 113008,
    "pid": 210000,
    "id": 500,
    "area_id": 210400
  },
  {
    "area_name": "新抚区",
    "pinyin": "Xinfu",
    "level": 3,
    "area_code": 24,
    "post_code": 113008,
    "pid": 210400,
    "id": 501,
    "area_id": 210402
  },
  {
    "area_name": "东洲区",
    "pinyin": "Dongzhou",
    "level": 3,
    "area_code": 24,
    "post_code": 113003,
    "pid": 210400,
    "id": 502,
    "area_id": 210403
  },
  {
    "area_name": "望花区",
    "pinyin": "Wanghua",
    "level": 3,
    "area_code": 24,
    "post_code": 113001,
    "pid": 210400,
    "id": 503,
    "area_id": 210404
  },
  {
    "area_name": "顺城区",
    "pinyin": "Shuncheng",
    "level": 3,
    "area_code": 24,
    "post_code": 113006,
    "pid": 210400,
    "id": 504,
    "area_id": 210411
  },
  {
    "area_name": "抚顺县",
    "pinyin": "Fushun",
    "level": 3,
    "area_code": 24,
    "post_code": 113006,
    "pid": 210400,
    "id": 505,
    "area_id": 210421
  },
  {
    "area_name": "新宾满族自治县",
    "pinyin": "Xinbin",
    "level": 3,
    "area_code": 24,
    "post_code": 113200,
    "pid": 210400,
    "id": 506,
    "area_id": 210422
  },
  {
    "area_name": "清原满族自治县",
    "pinyin": "Qingyuan",
    "level": 3,
    "area_code": 24,
    "post_code": 113300,
    "pid": 210400,
    "id": 507,
    "area_id": 210423
  },
  {
    "area_name": "本溪市",
    "pinyin": "Benxi",
    "level": 2,
    "area_code": 414,
    "post_code": 117000,
    "pid": 210000,
    "id": 508,
    "area_id": 210500
  },
  {
    "area_name": "平山区",
    "pinyin": "Pingshan",
    "level": 3,
    "area_code": 414,
    "post_code": 117000,
    "pid": 210500,
    "id": 509,
    "area_id": 210502
  },
  {
    "area_name": "溪湖区",
    "pinyin": "Xihu",
    "level": 3,
    "area_code": 414,
    "post_code": 117002,
    "pid": 210500,
    "id": 510,
    "area_id": 210503
  },
  {
    "area_name": "明山区",
    "pinyin": "Mingshan",
    "level": 3,
    "area_code": 414,
    "post_code": 117021,
    "pid": 210500,
    "id": 511,
    "area_id": 210504
  },
  {
    "area_name": "南芬区",
    "pinyin": "Nanfen",
    "level": 3,
    "area_code": 414,
    "post_code": 117014,
    "pid": 210500,
    "id": 512,
    "area_id": 210505
  },
  {
    "area_name": "本溪满族自治县",
    "pinyin": "Benxi",
    "level": 3,
    "area_code": 414,
    "post_code": 117100,
    "pid": 210500,
    "id": 513,
    "area_id": 210521
  },
  {
    "area_name": "桓仁满族自治县",
    "pinyin": "Huanren",
    "level": 3,
    "area_code": 414,
    "post_code": 117200,
    "pid": 210500,
    "id": 514,
    "area_id": 210522
  },
  {
    "area_name": "丹东市",
    "pinyin": "Dandong",
    "level": 2,
    "area_code": 415,
    "post_code": 118000,
    "pid": 210000,
    "id": 515,
    "area_id": 210600
  },
  {
    "area_name": "元宝区",
    "pinyin": "Yuanbao",
    "level": 3,
    "area_code": 415,
    "post_code": 118000,
    "pid": 210600,
    "id": 516,
    "area_id": 210602
  },
  {
    "area_name": "振兴区",
    "pinyin": "Zhenxing",
    "level": 3,
    "area_code": 415,
    "post_code": 118002,
    "pid": 210600,
    "id": 517,
    "area_id": 210603
  },
  {
    "area_name": "振安区",
    "pinyin": "Zhen'an",
    "level": 3,
    "area_code": 415,
    "post_code": 118001,
    "pid": 210600,
    "id": 518,
    "area_id": 210604
  },
  {
    "area_name": "宽甸满族自治县",
    "pinyin": "Kuandian",
    "level": 3,
    "area_code": 415,
    "post_code": 118200,
    "pid": 210600,
    "id": 519,
    "area_id": 210624
  },
  {
    "area_name": "东港市",
    "pinyin": "Donggang",
    "level": 3,
    "area_code": 415,
    "post_code": 118300,
    "pid": 210600,
    "id": 520,
    "area_id": 210681
  },
  {
    "area_name": "凤城市",
    "pinyin": "Fengcheng",
    "level": 3,
    "area_code": 415,
    "post_code": 118100,
    "pid": 210600,
    "id": 521,
    "area_id": 210682
  },
  {
    "area_name": "锦州市",
    "pinyin": "Jinzhou",
    "level": 2,
    "area_code": 416,
    "post_code": 121000,
    "pid": 210000,
    "id": 522,
    "area_id": 210700
  },
  {
    "area_name": "古塔区",
    "pinyin": "Guta",
    "level": 3,
    "area_code": 416,
    "post_code": 121001,
    "pid": 210700,
    "id": 523,
    "area_id": 210702
  },
  {
    "area_name": "凌河区",
    "pinyin": "Linghe",
    "level": 3,
    "area_code": 416,
    "post_code": 121000,
    "pid": 210700,
    "id": 524,
    "area_id": 210703
  },
  {
    "area_name": "太和区",
    "pinyin": "Taihe",
    "level": 3,
    "area_code": 416,
    "post_code": 121011,
    "pid": 210700,
    "id": 525,
    "area_id": 210711
  },
  {
    "area_name": "黑山县",
    "pinyin": "Heishan",
    "level": 3,
    "area_code": 416,
    "post_code": 121400,
    "pid": 210700,
    "id": 526,
    "area_id": 210726
  },
  {
    "area_name": "义县",
    "pinyin": "Yixian",
    "level": 3,
    "area_code": 416,
    "post_code": 121100,
    "pid": 210700,
    "id": 527,
    "area_id": 210727
  },
  {
    "area_name": "凌海市",
    "pinyin": "Linghai",
    "level": 3,
    "area_code": 416,
    "post_code": 121200,
    "pid": 210700,
    "id": 528,
    "area_id": 210781
  },
  {
    "area_name": "北镇市",
    "pinyin": "Beizhen",
    "level": 3,
    "area_code": 416,
    "post_code": 121300,
    "pid": 210700,
    "id": 529,
    "area_id": 210782
  },
  {
    "area_name": "营口市",
    "pinyin": "Yingkou",
    "level": 2,
    "area_code": 417,
    "post_code": 115003,
    "pid": 210000,
    "id": 530,
    "area_id": 210800
  },
  {
    "area_name": "站前区",
    "pinyin": "Zhanqian",
    "level": 3,
    "area_code": 417,
    "post_code": 115002,
    "pid": 210800,
    "id": 531,
    "area_id": 210802
  },
  {
    "area_name": "西市区",
    "pinyin": "Xishi",
    "level": 3,
    "area_code": 417,
    "post_code": 115004,
    "pid": 210800,
    "id": 532,
    "area_id": 210803
  },
  {
    "area_name": "鲅鱼圈区",
    "pinyin": "Bayuquan",
    "level": 3,
    "area_code": 417,
    "post_code": 115007,
    "pid": 210800,
    "id": 533,
    "area_id": 210804
  },
  {
    "area_name": "老边区",
    "pinyin": "Laobian",
    "level": 3,
    "area_code": 417,
    "post_code": 115005,
    "pid": 210800,
    "id": 534,
    "area_id": 210811
  },
  {
    "area_name": "盖州市",
    "pinyin": "Gaizhou",
    "level": 3,
    "area_code": 417,
    "post_code": 115200,
    "pid": 210800,
    "id": 535,
    "area_id": 210881
  },
  {
    "area_name": "大石桥市",
    "pinyin": "Dashiqiao",
    "level": 3,
    "area_code": 417,
    "post_code": 115100,
    "pid": 210800,
    "id": 536,
    "area_id": 210882
  },
  {
    "area_name": "阜新市",
    "pinyin": "Fuxin",
    "level": 2,
    "area_code": 418,
    "post_code": 123000,
    "pid": 210000,
    "id": 537,
    "area_id": 210900
  },
  {
    "area_name": "海州区",
    "pinyin": "Haizhou",
    "level": 3,
    "area_code": 418,
    "post_code": 123000,
    "pid": 210900,
    "id": 538,
    "area_id": 210902
  },
  {
    "area_name": "新邱区",
    "pinyin": "Xinqiu",
    "level": 3,
    "area_code": 418,
    "post_code": 123005,
    "pid": 210900,
    "id": 539,
    "area_id": 210903
  },
  {
    "area_name": "太平区",
    "pinyin": "Taiping",
    "level": 3,
    "area_code": 418,
    "post_code": 123003,
    "pid": 210900,
    "id": 540,
    "area_id": 210904
  },
  {
    "area_name": "清河门区",
    "pinyin": "Qinghemen",
    "level": 3,
    "area_code": 418,
    "post_code": 123006,
    "pid": 210900,
    "id": 541,
    "area_id": 210905
  },
  {
    "area_name": "细河区",
    "pinyin": "Xihe",
    "level": 3,
    "area_code": 418,
    "post_code": 123000,
    "pid": 210900,
    "id": 542,
    "area_id": 210911
  },
  {
    "area_name": "阜新蒙古族自治县",
    "pinyin": "Fuxin",
    "level": 3,
    "area_code": 418,
    "post_code": 123100,
    "pid": 210900,
    "id": 543,
    "area_id": 210921
  },
  {
    "area_name": "彰武县",
    "pinyin": "Zhangwu",
    "level": 3,
    "area_code": 418,
    "post_code": 123200,
    "pid": 210900,
    "id": 544,
    "area_id": 210922
  },
  {
    "area_name": "辽阳市",
    "pinyin": "Liaoyang",
    "level": 2,
    "area_code": 419,
    "post_code": 111000,
    "pid": 210000,
    "id": 545,
    "area_id": 211000
  },
  {
    "area_name": "白塔区",
    "pinyin": "Baita",
    "level": 3,
    "area_code": 419,
    "post_code": 111000,
    "pid": 211000,
    "id": 546,
    "area_id": 211002
  },
  {
    "area_name": "文圣区",
    "pinyin": "Wensheng",
    "level": 3,
    "area_code": 419,
    "post_code": 111000,
    "pid": 211000,
    "id": 547,
    "area_id": 211003
  },
  {
    "area_name": "宏伟区",
    "pinyin": "Hongwei",
    "level": 3,
    "area_code": 419,
    "post_code": 111003,
    "pid": 211000,
    "id": 548,
    "area_id": 211004
  },
  {
    "area_name": "弓长岭区",
    "pinyin": "Gongchangling",
    "level": 3,
    "area_code": 419,
    "post_code": 111008,
    "pid": 211000,
    "id": 549,
    "area_id": 211005
  },
  {
    "area_name": "太子河区",
    "pinyin": "Taizihe",
    "level": 3,
    "area_code": 419,
    "post_code": 111000,
    "pid": 211000,
    "id": 550,
    "area_id": 211011
  },
  {
    "area_name": "辽阳县",
    "pinyin": "Liaoyang",
    "level": 3,
    "area_code": 419,
    "post_code": 111200,
    "pid": 211000,
    "id": 551,
    "area_id": 211021
  },
  {
    "area_name": "灯塔市",
    "pinyin": "Dengta",
    "level": 3,
    "area_code": 419,
    "post_code": 111300,
    "pid": 211000,
    "id": 552,
    "area_id": 211081
  },
  {
    "area_name": "盘锦市",
    "pinyin": "Panjin",
    "level": 2,
    "area_code": 427,
    "post_code": 124010,
    "pid": 210000,
    "id": 553,
    "area_id": 211100
  },
  {
    "area_name": "双台子区",
    "pinyin": "Shuangtaizi",
    "level": 3,
    "area_code": 427,
    "post_code": 124000,
    "pid": 211100,
    "id": 554,
    "area_id": 211102
  },
  {
    "area_name": "兴隆台区",
    "pinyin": "Xinglongtai",
    "level": 3,
    "area_code": 427,
    "post_code": 124010,
    "pid": 211100,
    "id": 555,
    "area_id": 211103
  },
  {
    "area_name": "大洼县",
    "pinyin": "Dawa",
    "level": 3,
    "area_code": 427,
    "post_code": 124200,
    "pid": 211100,
    "id": 556,
    "area_id": 211121
  },
  {
    "area_name": "盘山县",
    "pinyin": "Panshan",
    "level": 3,
    "area_code": 427,
    "post_code": 124000,
    "pid": 211100,
    "id": 557,
    "area_id": 211122
  },
  {
    "area_name": "铁岭市",
    "pinyin": "Tieling",
    "level": 2,
    "area_code": 24,
    "post_code": 112000,
    "pid": 210000,
    "id": 558,
    "area_id": 211200
  },
  {
    "area_name": "银州区",
    "pinyin": "Yinzhou",
    "level": 3,
    "area_code": 24,
    "post_code": 112000,
    "pid": 211200,
    "id": 559,
    "area_id": 211202
  },
  {
    "area_name": "清河区",
    "pinyin": "Qinghe",
    "level": 3,
    "area_code": 24,
    "post_code": 112003,
    "pid": 211200,
    "id": 560,
    "area_id": 211204
  },
  {
    "area_name": "铁岭县",
    "pinyin": "Tieling",
    "level": 3,
    "area_code": 24,
    "post_code": 112000,
    "pid": 211200,
    "id": 561,
    "area_id": 211221
  },
  {
    "area_name": "西丰县",
    "pinyin": "Xifeng",
    "level": 3,
    "area_code": 24,
    "post_code": 112400,
    "pid": 211200,
    "id": 562,
    "area_id": 211223
  },
  {
    "area_name": "昌图县",
    "pinyin": "Changtu",
    "level": 3,
    "area_code": 24,
    "post_code": 112500,
    "pid": 211200,
    "id": 563,
    "area_id": 211224
  },
  {
    "area_name": "调兵山市",
    "pinyin": "Diaobingshan",
    "level": 3,
    "area_code": 24,
    "post_code": 112700,
    "pid": 211200,
    "id": 564,
    "area_id": 211281
  },
  {
    "area_name": "开原市",
    "pinyin": "Kaiyuan",
    "level": 3,
    "area_code": 24,
    "post_code": 112300,
    "pid": 211200,
    "id": 565,
    "area_id": 211282
  },
  {
    "area_name": "朝阳市",
    "pinyin": "Chaoyang",
    "level": 2,
    "area_code": 421,
    "post_code": 122000,
    "pid": 210000,
    "id": 566,
    "area_id": 211300
  },
  {
    "area_name": "双塔区",
    "pinyin": "Shuangta",
    "level": 3,
    "area_code": 421,
    "post_code": 122000,
    "pid": 211300,
    "id": 567,
    "area_id": 211302
  },
  {
    "area_name": "龙城区",
    "pinyin": "Longcheng",
    "level": 3,
    "area_code": 421,
    "post_code": 122000,
    "pid": 211300,
    "id": 568,
    "area_id": 211303
  },
  {
    "area_name": "朝阳县",
    "pinyin": "Chaoyang",
    "level": 3,
    "area_code": 421,
    "post_code": 122000,
    "pid": 211300,
    "id": 569,
    "area_id": 211321
  },
  {
    "area_name": "建平县",
    "pinyin": "Jianping",
    "level": 3,
    "area_code": 421,
    "post_code": 122400,
    "pid": 211300,
    "id": 570,
    "area_id": 211322
  },
  {
    "area_name": "喀喇沁左翼蒙古族自治县",
    "pinyin": "Kalaqinzuoyi",
    "level": 3,
    "area_code": 421,
    "post_code": 122300,
    "pid": 211300,
    "id": 571,
    "area_id": 211324
  },
  {
    "area_name": "北票市",
    "pinyin": "Beipiao",
    "level": 3,
    "area_code": 421,
    "post_code": 122100,
    "pid": 211300,
    "id": 572,
    "area_id": 211381
  },
  {
    "area_name": "凌源市",
    "pinyin": "Lingyuan",
    "level": 3,
    "area_code": 421,
    "post_code": 122500,
    "pid": 211300,
    "id": 573,
    "area_id": 211382
  },
  {
    "area_name": "葫芦岛市",
    "pinyin": "Huludao",
    "level": 2,
    "area_code": 429,
    "post_code": 125000,
    "pid": 210000,
    "id": 574,
    "area_id": 211400
  },
  {
    "area_name": "连山区",
    "pinyin": "Lianshan",
    "level": 3,
    "area_code": 429,
    "post_code": 125001,
    "pid": 211400,
    "id": 575,
    "area_id": 211402
  },
  {
    "area_name": "龙港区",
    "pinyin": "Longgang",
    "level": 3,
    "area_code": 429,
    "post_code": 125003,
    "pid": 211400,
    "id": 576,
    "area_id": 211403
  },
  {
    "area_name": "南票区",
    "pinyin": "Nanpiao",
    "level": 3,
    "area_code": 429,
    "post_code": 125027,
    "pid": 211400,
    "id": 577,
    "area_id": 211404
  },
  {
    "area_name": "绥中县",
    "pinyin": "Suizhong",
    "level": 3,
    "area_code": 429,
    "post_code": 125200,
    "pid": 211400,
    "id": 578,
    "area_id": 211421
  },
  {
    "area_name": "建昌县",
    "pinyin": "Jianchang",
    "level": 3,
    "area_code": 429,
    "post_code": 125300,
    "pid": 211400,
    "id": 579,
    "area_id": 211422
  },
  {
    "area_name": "兴城市",
    "pinyin": "Xingcheng",
    "level": 3,
    "area_code": 429,
    "post_code": 125100,
    "pid": 211400,
    "id": 580,
    "area_id": 211481
  },
  {
    "area_name": "金普新区",
    "pinyin": "Jinpuxinqu",
    "level": 2,
    "area_code": 411,
    "post_code": 116100,
    "pid": 210000,
    "id": 581,
    "area_id": 211500
  },
  {
    "area_name": "金州新区",
    "pinyin": "Jinzhouxinqu",
    "level": 3,
    "area_code": 411,
    "post_code": 116100,
    "pid": 211500,
    "id": 582,
    "area_id": 211501
  },
  {
    "area_name": "普湾新区",
    "pinyin": "Puwanxinqu",
    "level": 3,
    "area_code": 411,
    "post_code": 116200,
    "pid": 211500,
    "id": 583,
    "area_id": 211502
  },
  {
    "area_name": "保税区",
    "pinyin": "Baoshuiqu",
    "level": 3,
    "area_code": 411,
    "post_code": 116100,
    "pid": 211500,
    "id": 584,
    "area_id": 211503
  },
  {
    "area_name": "吉林省",
    "pinyin": "Jilin",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 585,
    "area_id": 220000
  },
  {
    "area_name": "长春市",
    "pinyin": "Changchun",
    "level": 2,
    "area_code": 431,
    "post_code": 130022,
    "pid": 220000,
    "id": 586,
    "area_id": 220100
  },
  {
    "area_name": "南关区",
    "pinyin": "Nanguan",
    "level": 3,
    "area_code": 431,
    "post_code": 130022,
    "pid": 220100,
    "id": 587,
    "area_id": 220102
  },
  {
    "area_name": "宽城区",
    "pinyin": "Kuancheng",
    "level": 3,
    "area_code": 431,
    "post_code": 130051,
    "pid": 220100,
    "id": 588,
    "area_id": 220103
  },
  {
    "area_name": "朝阳区",
    "pinyin": "Chaoyang",
    "level": 3,
    "area_code": 431,
    "post_code": 130012,
    "pid": 220100,
    "id": 589,
    "area_id": 220104
  },
  {
    "area_name": "二道区",
    "pinyin": "Erdao",
    "level": 3,
    "area_code": 431,
    "post_code": 130031,
    "pid": 220100,
    "id": 590,
    "area_id": 220105
  },
  {
    "area_name": "绿园区",
    "pinyin": "Lvyuan",
    "level": 3,
    "area_code": 431,
    "post_code": 130062,
    "pid": 220100,
    "id": 591,
    "area_id": 220106
  },
  {
    "area_name": "双阳区",
    "pinyin": "Shuangyang",
    "level": 3,
    "area_code": 431,
    "post_code": 130600,
    "pid": 220100,
    "id": 592,
    "area_id": 220112
  },
  {
    "area_name": "九台区",
    "pinyin": "Jiutai",
    "level": 3,
    "area_code": 431,
    "post_code": 130500,
    "pid": 220100,
    "id": 593,
    "area_id": 220113
  },
  {
    "area_name": "农安县",
    "pinyin": "Nong'an",
    "level": 3,
    "area_code": 431,
    "post_code": 130200,
    "pid": 220100,
    "id": 594,
    "area_id": 220122
  },
  {
    "area_name": "榆树市",
    "pinyin": "Yushu",
    "level": 3,
    "area_code": 431,
    "post_code": 130400,
    "pid": 220100,
    "id": 595,
    "area_id": 220182
  },
  {
    "area_name": "德惠市",
    "pinyin": "Dehui",
    "level": 3,
    "area_code": 431,
    "post_code": 130300,
    "pid": 220100,
    "id": 596,
    "area_id": 220183
  },
  {
    "area_name": "吉林市",
    "pinyin": "Jilin",
    "level": 2,
    "area_code": 432,
    "post_code": 132011,
    "pid": 220000,
    "id": 597,
    "area_id": 220200
  },
  {
    "area_name": "昌邑区",
    "pinyin": "Changyi",
    "level": 3,
    "area_code": 432,
    "post_code": 132002,
    "pid": 220200,
    "id": 598,
    "area_id": 220202
  },
  {
    "area_name": "龙潭区",
    "pinyin": "Longtan",
    "level": 3,
    "area_code": 432,
    "post_code": 132021,
    "pid": 220200,
    "id": 599,
    "area_id": 220203
  },
  {
    "area_name": "船营区",
    "pinyin": "Chuanying",
    "level": 3,
    "area_code": 432,
    "post_code": 132011,
    "pid": 220200,
    "id": 600,
    "area_id": 220204
  },
  {
    "area_name": "丰满区",
    "pinyin": "Fengman",
    "level": 3,
    "area_code": 432,
    "post_code": 132013,
    "pid": 220200,
    "id": 601,
    "area_id": 220211
  },
  {
    "area_name": "永吉县",
    "pinyin": "Yongji",
    "level": 3,
    "area_code": 432,
    "post_code": 132200,
    "pid": 220200,
    "id": 602,
    "area_id": 220221
  },
  {
    "area_name": "蛟河市",
    "pinyin": "Jiaohe",
    "level": 3,
    "area_code": 432,
    "post_code": 132500,
    "pid": 220200,
    "id": 603,
    "area_id": 220281
  },
  {
    "area_name": "桦甸市",
    "pinyin": "Huadian",
    "level": 3,
    "area_code": 432,
    "post_code": 132400,
    "pid": 220200,
    "id": 604,
    "area_id": 220282
  },
  {
    "area_name": "舒兰市",
    "pinyin": "Shulan",
    "level": 3,
    "area_code": 432,
    "post_code": 132600,
    "pid": 220200,
    "id": 605,
    "area_id": 220283
  },
  {
    "area_name": "磐石市",
    "pinyin": "Panshi",
    "level": 3,
    "area_code": 432,
    "post_code": 132300,
    "pid": 220200,
    "id": 606,
    "area_id": 220284
  },
  {
    "area_name": "四平市",
    "pinyin": "Siping",
    "level": 2,
    "area_code": 434,
    "post_code": 136000,
    "pid": 220000,
    "id": 607,
    "area_id": 220300
  },
  {
    "area_name": "铁西区",
    "pinyin": "Tiexi",
    "level": 3,
    "area_code": 434,
    "post_code": 136000,
    "pid": 220300,
    "id": 608,
    "area_id": 220302
  },
  {
    "area_name": "铁东区",
    "pinyin": "Tiedong",
    "level": 3,
    "area_code": 434,
    "post_code": 136001,
    "pid": 220300,
    "id": 609,
    "area_id": 220303
  },
  {
    "area_name": "梨树县",
    "pinyin": "Lishu",
    "level": 3,
    "area_code": 434,
    "post_code": 136500,
    "pid": 220300,
    "id": 610,
    "area_id": 220322
  },
  {
    "area_name": "伊通满族自治县",
    "pinyin": "Yitong",
    "level": 3,
    "area_code": 434,
    "post_code": 130700,
    "pid": 220300,
    "id": 611,
    "area_id": 220323
  },
  {
    "area_name": "公主岭市",
    "pinyin": "Gongzhuling",
    "level": 3,
    "area_code": 434,
    "post_code": 136100,
    "pid": 220300,
    "id": 612,
    "area_id": 220381
  },
  {
    "area_name": "双辽市",
    "pinyin": "Shuangliao",
    "level": 3,
    "area_code": 434,
    "post_code": 136400,
    "pid": 220300,
    "id": 613,
    "area_id": 220382
  },
  {
    "area_name": "辽源市",
    "pinyin": "Liaoyuan",
    "level": 2,
    "area_code": 437,
    "post_code": 136200,
    "pid": 220000,
    "id": 614,
    "area_id": 220400
  },
  {
    "area_name": "龙山区",
    "pinyin": "Longshan",
    "level": 3,
    "area_code": 437,
    "post_code": 136200,
    "pid": 220400,
    "id": 615,
    "area_id": 220402
  },
  {
    "area_name": "西安区",
    "pinyin": "Xi'an",
    "level": 3,
    "area_code": 437,
    "post_code": 136201,
    "pid": 220400,
    "id": 616,
    "area_id": 220403
  },
  {
    "area_name": "东丰县",
    "pinyin": "Dongfeng",
    "level": 3,
    "area_code": 437,
    "post_code": 136300,
    "pid": 220400,
    "id": 617,
    "area_id": 220421
  },
  {
    "area_name": "东辽县",
    "pinyin": "Dongliao",
    "level": 3,
    "area_code": 437,
    "post_code": 136600,
    "pid": 220400,
    "id": 618,
    "area_id": 220422
  },
  {
    "area_name": "通化市",
    "pinyin": "Tonghua",
    "level": 2,
    "area_code": 435,
    "post_code": 134001,
    "pid": 220000,
    "id": 619,
    "area_id": 220500
  },
  {
    "area_name": "东昌区",
    "pinyin": "Dongchang",
    "level": 3,
    "area_code": 435,
    "post_code": 134001,
    "pid": 220500,
    "id": 620,
    "area_id": 220502
  },
  {
    "area_name": "二道江区",
    "pinyin": "Erdaojiang",
    "level": 3,
    "area_code": 435,
    "post_code": 134003,
    "pid": 220500,
    "id": 621,
    "area_id": 220503
  },
  {
    "area_name": "通化县",
    "pinyin": "Tonghua",
    "level": 3,
    "area_code": 435,
    "post_code": 134100,
    "pid": 220500,
    "id": 622,
    "area_id": 220521
  },
  {
    "area_name": "辉南县",
    "pinyin": "Huinan",
    "level": 3,
    "area_code": 435,
    "post_code": 135100,
    "pid": 220500,
    "id": 623,
    "area_id": 220523
  },
  {
    "area_name": "柳河县",
    "pinyin": "Liuhe",
    "level": 3,
    "area_code": 435,
    "post_code": 135300,
    "pid": 220500,
    "id": 624,
    "area_id": 220524
  },
  {
    "area_name": "梅河口市",
    "pinyin": "Meihekou",
    "level": 3,
    "area_code": 435,
    "post_code": 135000,
    "pid": 220500,
    "id": 625,
    "area_id": 220581
  },
  {
    "area_name": "集安市",
    "pinyin": "Ji'an",
    "level": 3,
    "area_code": 435,
    "post_code": 134200,
    "pid": 220500,
    "id": 626,
    "area_id": 220582
  },
  {
    "area_name": "白山市",
    "pinyin": "Baishan",
    "level": 2,
    "area_code": 439,
    "post_code": 134300,
    "pid": 220000,
    "id": 627,
    "area_id": 220600
  },
  {
    "area_name": "浑江区",
    "pinyin": "Hunjiang",
    "level": 3,
    "area_code": 439,
    "post_code": 134300,
    "pid": 220600,
    "id": 628,
    "area_id": 220602
  },
  {
    "area_name": "江源区",
    "pinyin": "Jiangyuan",
    "level": 3,
    "area_code": 439,
    "post_code": 134700,
    "pid": 220600,
    "id": 629,
    "area_id": 220605
  },
  {
    "area_name": "抚松县",
    "pinyin": "Fusong",
    "level": 3,
    "area_code": 439,
    "post_code": 134500,
    "pid": 220600,
    "id": 630,
    "area_id": 220621
  },
  {
    "area_name": "靖宇县",
    "pinyin": "Jingyu",
    "level": 3,
    "area_code": 439,
    "post_code": 135200,
    "pid": 220600,
    "id": 631,
    "area_id": 220622
  },
  {
    "area_name": "长白朝鲜族自治县",
    "pinyin": "Changbai",
    "level": 3,
    "area_code": 439,
    "post_code": 134400,
    "pid": 220600,
    "id": 632,
    "area_id": 220623
  },
  {
    "area_name": "临江市",
    "pinyin": "Linjiang",
    "level": 3,
    "area_code": 439,
    "post_code": 134600,
    "pid": 220600,
    "id": 633,
    "area_id": 220681
  },
  {
    "area_name": "松原市",
    "pinyin": "Songyuan",
    "level": 2,
    "area_code": 438,
    "post_code": 138000,
    "pid": 220000,
    "id": 634,
    "area_id": 220700
  },
  {
    "area_name": "宁江区",
    "pinyin": "Ningjiang",
    "level": 3,
    "area_code": 438,
    "post_code": 138000,
    "pid": 220700,
    "id": 635,
    "area_id": 220702
  },
  {
    "area_name": "前郭尔罗斯蒙古族自治县",
    "pinyin": "Qianguoerluosi",
    "level": 3,
    "area_code": 438,
    "post_code": 138000,
    "pid": 220700,
    "id": 636,
    "area_id": 220721
  },
  {
    "area_name": "长岭县",
    "pinyin": "Changling",
    "level": 3,
    "area_code": 438,
    "post_code": 131500,
    "pid": 220700,
    "id": 637,
    "area_id": 220722
  },
  {
    "area_name": "乾安县",
    "pinyin": "Qian'an",
    "level": 3,
    "area_code": 438,
    "post_code": 131400,
    "pid": 220700,
    "id": 638,
    "area_id": 220723
  },
  {
    "area_name": "扶余市",
    "pinyin": "Fuyu",
    "level": 3,
    "area_code": 438,
    "post_code": 131200,
    "pid": 220700,
    "id": 639,
    "area_id": 220781
  },
  {
    "area_name": "白城市",
    "pinyin": "Baicheng",
    "level": 2,
    "area_code": 436,
    "post_code": 137000,
    "pid": 220000,
    "id": 640,
    "area_id": 220800
  },
  {
    "area_name": "洮北区",
    "pinyin": "Taobei",
    "level": 3,
    "area_code": 436,
    "post_code": 137000,
    "pid": 220800,
    "id": 641,
    "area_id": 220802
  },
  {
    "area_name": "镇赉县",
    "pinyin": "Zhenlai",
    "level": 3,
    "area_code": 436,
    "post_code": 137300,
    "pid": 220800,
    "id": 642,
    "area_id": 220821
  },
  {
    "area_name": "通榆县",
    "pinyin": "Tongyu",
    "level": 3,
    "area_code": 436,
    "post_code": 137200,
    "pid": 220800,
    "id": 643,
    "area_id": 220822
  },
  {
    "area_name": "洮南市",
    "pinyin": "Taonan",
    "level": 3,
    "area_code": 436,
    "post_code": 137100,
    "pid": 220800,
    "id": 644,
    "area_id": 220881
  },
  {
    "area_name": "大安市",
    "pinyin": "Da'an",
    "level": 3,
    "area_code": 436,
    "post_code": 131300,
    "pid": 220800,
    "id": 645,
    "area_id": 220882
  },
  {
    "area_name": "延边朝鲜族自治州",
    "pinyin": "Yanbian",
    "level": 2,
    "area_code": 433,
    "post_code": 133000,
    "pid": 220000,
    "id": 646,
    "area_id": 222400
  },
  {
    "area_name": "延吉市",
    "pinyin": "Yanji",
    "level": 3,
    "area_code": 433,
    "post_code": 133000,
    "pid": 222400,
    "id": 647,
    "area_id": 222401
  },
  {
    "area_name": "图们市",
    "pinyin": "Tumen",
    "level": 3,
    "area_code": 433,
    "post_code": 133100,
    "pid": 222400,
    "id": 648,
    "area_id": 222402
  },
  {
    "area_name": "敦化市",
    "pinyin": "Dunhua",
    "level": 3,
    "area_code": 433,
    "post_code": 133700,
    "pid": 222400,
    "id": 649,
    "area_id": 222403
  },
  {
    "area_name": "珲春市",
    "pinyin": "Hunchun",
    "level": 3,
    "area_code": 433,
    "post_code": 133300,
    "pid": 222400,
    "id": 650,
    "area_id": 222404
  },
  {
    "area_name": "龙井市",
    "pinyin": "Longjing",
    "level": 3,
    "area_code": 433,
    "post_code": 133400,
    "pid": 222400,
    "id": 651,
    "area_id": 222405
  },
  {
    "area_name": "和龙市",
    "pinyin": "Helong",
    "level": 3,
    "area_code": 433,
    "post_code": 133500,
    "pid": 222400,
    "id": 652,
    "area_id": 222406
  },
  {
    "area_name": "汪清县",
    "pinyin": "Wangqing",
    "level": 3,
    "area_code": 433,
    "post_code": 133200,
    "pid": 222400,
    "id": 653,
    "area_id": 222424
  },
  {
    "area_name": "安图县",
    "pinyin": "Antu",
    "level": 3,
    "area_code": 433,
    "post_code": 133600,
    "pid": 222400,
    "id": 654,
    "area_id": 222426
  },
  {
    "area_name": "黑龙江省",
    "pinyin": "Heilongjiang",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 655,
    "area_id": 230000
  },
  {
    "area_name": "哈尔滨市",
    "pinyin": "Harbin",
    "level": 2,
    "area_code": 451,
    "post_code": 150010,
    "pid": 230000,
    "id": 656,
    "area_id": 230100
  },
  {
    "area_name": "道里区",
    "pinyin": "Daoli",
    "level": 3,
    "area_code": 451,
    "post_code": 150010,
    "pid": 230100,
    "id": 657,
    "area_id": 230102
  },
  {
    "area_name": "南岗区",
    "pinyin": "Nangang",
    "level": 3,
    "area_code": 451,
    "post_code": 150006,
    "pid": 230100,
    "id": 658,
    "area_id": 230103
  },
  {
    "area_name": "道外区",
    "pinyin": "Daowai",
    "level": 3,
    "area_code": 451,
    "post_code": 150020,
    "pid": 230100,
    "id": 659,
    "area_id": 230104
  },
  {
    "area_name": "平房区",
    "pinyin": "Pingfang",
    "level": 3,
    "area_code": 451,
    "post_code": 150060,
    "pid": 230100,
    "id": 660,
    "area_id": 230108
  },
  {
    "area_name": "松北区",
    "pinyin": "Songbei",
    "level": 3,
    "area_code": 451,
    "post_code": 150028,
    "pid": 230100,
    "id": 661,
    "area_id": 230109
  },
  {
    "area_name": "香坊区",
    "pinyin": "Xiangfang",
    "level": 3,
    "area_code": 451,
    "post_code": 150036,
    "pid": 230100,
    "id": 662,
    "area_id": 230110
  },
  {
    "area_name": "呼兰区",
    "pinyin": "Hulan",
    "level": 3,
    "area_code": 451,
    "post_code": 150500,
    "pid": 230100,
    "id": 663,
    "area_id": 230111
  },
  {
    "area_name": "阿城区",
    "pinyin": "A'cheng",
    "level": 3,
    "area_code": 451,
    "post_code": 150300,
    "pid": 230100,
    "id": 664,
    "area_id": 230112
  },
  {
    "area_name": "双城区",
    "pinyin": "Shuangcheng",
    "level": 3,
    "area_code": 451,
    "post_code": 150100,
    "pid": 230100,
    "id": 665,
    "area_id": 230113
  },
  {
    "area_name": "依兰县",
    "pinyin": "Yilan",
    "level": 3,
    "area_code": 451,
    "post_code": 154800,
    "pid": 230100,
    "id": 666,
    "area_id": 230123
  },
  {
    "area_name": "方正县",
    "pinyin": "Fangzheng",
    "level": 3,
    "area_code": 451,
    "post_code": 150800,
    "pid": 230100,
    "id": 667,
    "area_id": 230124
  },
  {
    "area_name": "宾县",
    "pinyin": "Binxian",
    "level": 3,
    "area_code": 451,
    "post_code": 150400,
    "pid": 230100,
    "id": 668,
    "area_id": 230125
  },
  {
    "area_name": "巴彦县",
    "pinyin": "Bayan",
    "level": 3,
    "area_code": 451,
    "post_code": 151800,
    "pid": 230100,
    "id": 669,
    "area_id": 230126
  },
  {
    "area_name": "木兰县",
    "pinyin": "Mulan",
    "level": 3,
    "area_code": 451,
    "post_code": 151900,
    "pid": 230100,
    "id": 670,
    "area_id": 230127
  },
  {
    "area_name": "通河县",
    "pinyin": "Tonghe",
    "level": 3,
    "area_code": 451,
    "post_code": 150900,
    "pid": 230100,
    "id": 671,
    "area_id": 230128
  },
  {
    "area_name": "延寿县",
    "pinyin": "Yanshou",
    "level": 3,
    "area_code": 451,
    "post_code": 150700,
    "pid": 230100,
    "id": 672,
    "area_id": 230129
  },
  {
    "area_name": "尚志市",
    "pinyin": "Shangzhi",
    "level": 3,
    "area_code": 451,
    "post_code": 150600,
    "pid": 230100,
    "id": 673,
    "area_id": 230183
  },
  {
    "area_name": "五常市",
    "pinyin": "Wuchang",
    "level": 3,
    "area_code": 451,
    "post_code": 150200,
    "pid": 230100,
    "id": 674,
    "area_id": 230184
  },
  {
    "area_name": "齐齐哈尔市",
    "pinyin": "Qiqihar",
    "level": 2,
    "area_code": 452,
    "post_code": 161005,
    "pid": 230000,
    "id": 675,
    "area_id": 230200
  },
  {
    "area_name": "龙沙区",
    "pinyin": "Longsha",
    "level": 3,
    "area_code": 452,
    "post_code": 161000,
    "pid": 230200,
    "id": 676,
    "area_id": 230202
  },
  {
    "area_name": "建华区",
    "pinyin": "Jianhua",
    "level": 3,
    "area_code": 452,
    "post_code": 161006,
    "pid": 230200,
    "id": 677,
    "area_id": 230203
  },
  {
    "area_name": "铁锋区",
    "pinyin": "Tiefeng",
    "level": 3,
    "area_code": 452,
    "post_code": 161000,
    "pid": 230200,
    "id": 678,
    "area_id": 230204
  },
  {
    "area_name": "昂昂溪区",
    "pinyin": "Angangxi",
    "level": 3,
    "area_code": 452,
    "post_code": 161031,
    "pid": 230200,
    "id": 679,
    "area_id": 230205
  },
  {
    "area_name": "富拉尔基区",
    "pinyin": "Fulaerji",
    "level": 3,
    "area_code": 452,
    "post_code": 161041,
    "pid": 230200,
    "id": 680,
    "area_id": 230206
  },
  {
    "area_name": "碾子山区",
    "pinyin": "Nianzishan",
    "level": 3,
    "area_code": 452,
    "post_code": 161046,
    "pid": 230200,
    "id": 681,
    "area_id": 230207
  },
  {
    "area_name": "梅里斯达斡尔族区",
    "pinyin": "Meilisi",
    "level": 3,
    "area_code": 452,
    "post_code": 161021,
    "pid": 230200,
    "id": 682,
    "area_id": 230208
  },
  {
    "area_name": "龙江县",
    "pinyin": "Longjiang",
    "level": 3,
    "area_code": 452,
    "post_code": 161100,
    "pid": 230200,
    "id": 683,
    "area_id": 230221
  },
  {
    "area_name": "依安县",
    "pinyin": "Yi'an",
    "level": 3,
    "area_code": 452,
    "post_code": 161500,
    "pid": 230200,
    "id": 684,
    "area_id": 230223
  },
  {
    "area_name": "泰来县",
    "pinyin": "Tailai",
    "level": 3,
    "area_code": 452,
    "post_code": 162400,
    "pid": 230200,
    "id": 685,
    "area_id": 230224
  },
  {
    "area_name": "甘南县",
    "pinyin": "Gannan",
    "level": 3,
    "area_code": 452,
    "post_code": 162100,
    "pid": 230200,
    "id": 686,
    "area_id": 230225
  },
  {
    "area_name": "富裕县",
    "pinyin": "Fuyu",
    "level": 3,
    "area_code": 452,
    "post_code": 161200,
    "pid": 230200,
    "id": 687,
    "area_id": 230227
  },
  {
    "area_name": "克山县",
    "pinyin": "Keshan",
    "level": 3,
    "area_code": 452,
    "post_code": 161600,
    "pid": 230200,
    "id": 688,
    "area_id": 230229
  },
  {
    "area_name": "克东县",
    "pinyin": "Kedong",
    "level": 3,
    "area_code": 452,
    "post_code": 164800,
    "pid": 230200,
    "id": 689,
    "area_id": 230230
  },
  {
    "area_name": "拜泉县",
    "pinyin": "Baiquan",
    "level": 3,
    "area_code": 452,
    "post_code": 164700,
    "pid": 230200,
    "id": 690,
    "area_id": 230231
  },
  {
    "area_name": "讷河市",
    "pinyin": "Nehe",
    "level": 3,
    "area_code": 452,
    "post_code": 161300,
    "pid": 230200,
    "id": 691,
    "area_id": 230281
  },
  {
    "area_name": "鸡西市",
    "pinyin": "Jixi",
    "level": 2,
    "area_code": 467,
    "post_code": 158100,
    "pid": 230000,
    "id": 692,
    "area_id": 230300
  },
  {
    "area_name": "鸡冠区",
    "pinyin": "Jiguan",
    "level": 3,
    "area_code": 467,
    "post_code": 158100,
    "pid": 230300,
    "id": 693,
    "area_id": 230302
  },
  {
    "area_name": "恒山区",
    "pinyin": "Hengshan",
    "level": 3,
    "area_code": 467,
    "post_code": 158130,
    "pid": 230300,
    "id": 694,
    "area_id": 230303
  },
  {
    "area_name": "滴道区",
    "pinyin": "Didao",
    "level": 3,
    "area_code": 467,
    "post_code": 158150,
    "pid": 230300,
    "id": 695,
    "area_id": 230304
  },
  {
    "area_name": "梨树区",
    "pinyin": "Lishu",
    "level": 3,
    "area_code": 467,
    "post_code": 158160,
    "pid": 230300,
    "id": 696,
    "area_id": 230305
  },
  {
    "area_name": "城子河区",
    "pinyin": "Chengzihe",
    "level": 3,
    "area_code": 467,
    "post_code": 158170,
    "pid": 230300,
    "id": 697,
    "area_id": 230306
  },
  {
    "area_name": "麻山区",
    "pinyin": "Mashan",
    "level": 3,
    "area_code": 467,
    "post_code": 158180,
    "pid": 230300,
    "id": 698,
    "area_id": 230307
  },
  {
    "area_name": "鸡东县",
    "pinyin": "Jidong",
    "level": 3,
    "area_code": 467,
    "post_code": 158200,
    "pid": 230300,
    "id": 699,
    "area_id": 230321
  },
  {
    "area_name": "虎林市",
    "pinyin": "Hulin",
    "level": 3,
    "area_code": 467,
    "post_code": 158400,
    "pid": 230300,
    "id": 700,
    "area_id": 230381
  },
  {
    "area_name": "密山市",
    "pinyin": "Mishan",
    "level": 3,
    "area_code": 467,
    "post_code": 158300,
    "pid": 230300,
    "id": 701,
    "area_id": 230382
  },
  {
    "area_name": "鹤岗市",
    "pinyin": "Hegang",
    "level": 2,
    "area_code": 468,
    "post_code": 154100,
    "pid": 230000,
    "id": 702,
    "area_id": 230400
  },
  {
    "area_name": "向阳区",
    "pinyin": "Xiangyang",
    "level": 3,
    "area_code": 468,
    "post_code": 154100,
    "pid": 230400,
    "id": 703,
    "area_id": 230402
  },
  {
    "area_name": "工农区",
    "pinyin": "Gongnong",
    "level": 3,
    "area_code": 468,
    "post_code": 154101,
    "pid": 230400,
    "id": 704,
    "area_id": 230403
  },
  {
    "area_name": "南山区",
    "pinyin": "Nanshan",
    "level": 3,
    "area_code": 468,
    "post_code": 154104,
    "pid": 230400,
    "id": 705,
    "area_id": 230404
  },
  {
    "area_name": "兴安区",
    "pinyin": "Xing'an",
    "level": 3,
    "area_code": 468,
    "post_code": 154102,
    "pid": 230400,
    "id": 706,
    "area_id": 230405
  },
  {
    "area_name": "东山区",
    "pinyin": "Dongshan",
    "level": 3,
    "area_code": 468,
    "post_code": 154106,
    "pid": 230400,
    "id": 707,
    "area_id": 230406
  },
  {
    "area_name": "兴山区",
    "pinyin": "Xingshan",
    "level": 3,
    "area_code": 468,
    "post_code": 154105,
    "pid": 230400,
    "id": 708,
    "area_id": 230407
  },
  {
    "area_name": "萝北县",
    "pinyin": "Luobei",
    "level": 3,
    "area_code": 468,
    "post_code": 154200,
    "pid": 230400,
    "id": 709,
    "area_id": 230421
  },
  {
    "area_name": "绥滨县",
    "pinyin": "Suibin",
    "level": 3,
    "area_code": 468,
    "post_code": 156200,
    "pid": 230400,
    "id": 710,
    "area_id": 230422
  },
  {
    "area_name": "双鸭山市",
    "pinyin": "Shuangyashan",
    "level": 2,
    "area_code": 469,
    "post_code": 155100,
    "pid": 230000,
    "id": 711,
    "area_id": 230500
  },
  {
    "area_name": "尖山区",
    "pinyin": "Jianshan",
    "level": 3,
    "area_code": 469,
    "post_code": 155100,
    "pid": 230500,
    "id": 712,
    "area_id": 230502
  },
  {
    "area_name": "岭东区",
    "pinyin": "Lingdong",
    "level": 3,
    "area_code": 469,
    "post_code": 155120,
    "pid": 230500,
    "id": 713,
    "area_id": 230503
  },
  {
    "area_name": "四方台区",
    "pinyin": "Sifangtai",
    "level": 3,
    "area_code": 469,
    "post_code": 155130,
    "pid": 230500,
    "id": 714,
    "area_id": 230505
  },
  {
    "area_name": "宝山区",
    "pinyin": "Baoshan",
    "level": 3,
    "area_code": 469,
    "post_code": 155131,
    "pid": 230500,
    "id": 715,
    "area_id": 230506
  },
  {
    "area_name": "集贤县",
    "pinyin": "Jixian",
    "level": 3,
    "area_code": 469,
    "post_code": 155900,
    "pid": 230500,
    "id": 716,
    "area_id": 230521
  },
  {
    "area_name": "友谊县",
    "pinyin": "Youyi",
    "level": 3,
    "area_code": 469,
    "post_code": 155800,
    "pid": 230500,
    "id": 717,
    "area_id": 230522
  },
  {
    "area_name": "宝清县",
    "pinyin": "Baoqing",
    "level": 3,
    "area_code": 469,
    "post_code": 155600,
    "pid": 230500,
    "id": 718,
    "area_id": 230523
  },
  {
    "area_name": "饶河县",
    "pinyin": "Raohe",
    "level": 3,
    "area_code": 469,
    "post_code": 155700,
    "pid": 230500,
    "id": 719,
    "area_id": 230524
  },
  {
    "area_name": "大庆市",
    "pinyin": "Daqing",
    "level": 2,
    "area_code": 459,
    "post_code": 163000,
    "pid": 230000,
    "id": 720,
    "area_id": 230600
  },
  {
    "area_name": "萨尔图区",
    "pinyin": "Saertu",
    "level": 3,
    "area_code": 459,
    "post_code": 163001,
    "pid": 230600,
    "id": 721,
    "area_id": 230602
  },
  {
    "area_name": "龙凤区",
    "pinyin": "Longfeng",
    "level": 3,
    "area_code": 459,
    "post_code": 163711,
    "pid": 230600,
    "id": 722,
    "area_id": 230603
  },
  {
    "area_name": "让胡路区",
    "pinyin": "Ranghulu",
    "level": 3,
    "area_code": 459,
    "post_code": 163712,
    "pid": 230600,
    "id": 723,
    "area_id": 230604
  },
  {
    "area_name": "红岗区",
    "pinyin": "Honggang",
    "level": 3,
    "area_code": 459,
    "post_code": 163511,
    "pid": 230600,
    "id": 724,
    "area_id": 230605
  },
  {
    "area_name": "大同区",
    "pinyin": "Datong",
    "level": 3,
    "area_code": 459,
    "post_code": 163515,
    "pid": 230600,
    "id": 725,
    "area_id": 230606
  },
  {
    "area_name": "肇州县",
    "pinyin": "Zhaozhou",
    "level": 3,
    "area_code": 459,
    "post_code": 166400,
    "pid": 230600,
    "id": 726,
    "area_id": 230621
  },
  {
    "area_name": "肇源县",
    "pinyin": "Zhaoyuan",
    "level": 3,
    "area_code": 459,
    "post_code": 166500,
    "pid": 230600,
    "id": 727,
    "area_id": 230622
  },
  {
    "area_name": "林甸县",
    "pinyin": "Lindian",
    "level": 3,
    "area_code": 459,
    "post_code": 166300,
    "pid": 230600,
    "id": 728,
    "area_id": 230623
  },
  {
    "area_name": "杜尔伯特蒙古族自治县",
    "pinyin": "Duerbote",
    "level": 3,
    "area_code": 459,
    "post_code": 166200,
    "pid": 230600,
    "id": 729,
    "area_id": 230624
  },
  {
    "area_name": "伊春市",
    "pinyin": "Yichun",
    "level": 2,
    "area_code": 458,
    "post_code": 153000,
    "pid": 230000,
    "id": 730,
    "area_id": 230700
  },
  {
    "area_name": "伊春区",
    "pinyin": "Yichun",
    "level": 3,
    "area_code": 458,
    "post_code": 153000,
    "pid": 230700,
    "id": 731,
    "area_id": 230702
  },
  {
    "area_name": "南岔区",
    "pinyin": "Nancha",
    "level": 3,
    "area_code": 458,
    "post_code": 153100,
    "pid": 230700,
    "id": 732,
    "area_id": 230703
  },
  {
    "area_name": "友好区",
    "pinyin": "Youhao",
    "level": 3,
    "area_code": 458,
    "post_code": 153031,
    "pid": 230700,
    "id": 733,
    "area_id": 230704
  },
  {
    "area_name": "西林区",
    "pinyin": "Xilin",
    "level": 3,
    "area_code": 458,
    "post_code": 153025,
    "pid": 230700,
    "id": 734,
    "area_id": 230705
  },
  {
    "area_name": "翠峦区",
    "pinyin": "Cuiluan",
    "level": 3,
    "area_code": 458,
    "post_code": 153013,
    "pid": 230700,
    "id": 735,
    "area_id": 230706
  },
  {
    "area_name": "新青区",
    "pinyin": "Xinqing",
    "level": 3,
    "area_code": 458,
    "post_code": 153036,
    "pid": 230700,
    "id": 736,
    "area_id": 230707
  },
  {
    "area_name": "美溪区",
    "pinyin": "Meixi",
    "level": 3,
    "area_code": 458,
    "post_code": 153021,
    "pid": 230700,
    "id": 737,
    "area_id": 230708
  },
  {
    "area_name": "金山屯区",
    "pinyin": "Jinshantun",
    "level": 3,
    "area_code": 458,
    "post_code": 153026,
    "pid": 230700,
    "id": 738,
    "area_id": 230709
  },
  {
    "area_name": "五营区",
    "pinyin": "Wuying",
    "level": 3,
    "area_code": 458,
    "post_code": 153033,
    "pid": 230700,
    "id": 739,
    "area_id": 230710
  },
  {
    "area_name": "乌马河区",
    "pinyin": "Wumahe",
    "level": 3,
    "area_code": 458,
    "post_code": 153011,
    "pid": 230700,
    "id": 740,
    "area_id": 230711
  },
  {
    "area_name": "汤旺河区",
    "pinyin": "Tangwanghe",
    "level": 3,
    "area_code": 458,
    "post_code": 153037,
    "pid": 230700,
    "id": 741,
    "area_id": 230712
  },
  {
    "area_name": "带岭区",
    "pinyin": "Dailing",
    "level": 3,
    "area_code": 458,
    "post_code": 153106,
    "pid": 230700,
    "id": 742,
    "area_id": 230713
  },
  {
    "area_name": "乌伊岭区",
    "pinyin": "Wuyiling",
    "level": 3,
    "area_code": 458,
    "post_code": 153038,
    "pid": 230700,
    "id": 743,
    "area_id": 230714
  },
  {
    "area_name": "红星区",
    "pinyin": "Hongxing",
    "level": 3,
    "area_code": 458,
    "post_code": 153035,
    "pid": 230700,
    "id": 744,
    "area_id": 230715
  },
  {
    "area_name": "上甘岭区",
    "pinyin": "Shangganling",
    "level": 3,
    "area_code": 458,
    "post_code": 153032,
    "pid": 230700,
    "id": 745,
    "area_id": 230716
  },
  {
    "area_name": "嘉荫县",
    "pinyin": "Jiayin",
    "level": 3,
    "area_code": 458,
    "post_code": 153200,
    "pid": 230700,
    "id": 746,
    "area_id": 230722
  },
  {
    "area_name": "铁力市",
    "pinyin": "Tieli",
    "level": 3,
    "area_code": 458,
    "post_code": 152500,
    "pid": 230700,
    "id": 747,
    "area_id": 230781
  },
  {
    "area_name": "佳木斯市",
    "pinyin": "Jiamusi",
    "level": 2,
    "area_code": 454,
    "post_code": 154002,
    "pid": 230000,
    "id": 748,
    "area_id": 230800
  },
  {
    "area_name": "向阳区",
    "pinyin": "Xiangyang",
    "level": 3,
    "area_code": 454,
    "post_code": 154002,
    "pid": 230800,
    "id": 749,
    "area_id": 230803
  },
  {
    "area_name": "前进区",
    "pinyin": "Qianjin",
    "level": 3,
    "area_code": 454,
    "post_code": 154002,
    "pid": 230800,
    "id": 750,
    "area_id": 230804
  },
  {
    "area_name": "东风区",
    "pinyin": "Dongfeng",
    "level": 3,
    "area_code": 454,
    "post_code": 154005,
    "pid": 230800,
    "id": 751,
    "area_id": 230805
  },
  {
    "area_name": "郊区",
    "pinyin": "Jiaoqu",
    "level": 3,
    "area_code": 454,
    "post_code": 154004,
    "pid": 230800,
    "id": 752,
    "area_id": 230811
  },
  {
    "area_name": "桦南县",
    "pinyin": "Huanan",
    "level": 3,
    "area_code": 454,
    "post_code": 154400,
    "pid": 230800,
    "id": 753,
    "area_id": 230822
  },
  {
    "area_name": "桦川县",
    "pinyin": "Huachuan",
    "level": 3,
    "area_code": 454,
    "post_code": 154300,
    "pid": 230800,
    "id": 754,
    "area_id": 230826
  },
  {
    "area_name": "汤原县",
    "pinyin": "Tangyuan",
    "level": 3,
    "area_code": 454,
    "post_code": 154700,
    "pid": 230800,
    "id": 755,
    "area_id": 230828
  },
  {
    "area_name": "抚远县",
    "pinyin": "Fuyuan",
    "level": 3,
    "area_code": 454,
    "post_code": 156500,
    "pid": 230800,
    "id": 756,
    "area_id": 230833
  },
  {
    "area_name": "同江市",
    "pinyin": "Tongjiang",
    "level": 3,
    "area_code": 454,
    "post_code": 156400,
    "pid": 230800,
    "id": 757,
    "area_id": 230881
  },
  {
    "area_name": "富锦市",
    "pinyin": "Fujin",
    "level": 3,
    "area_code": 454,
    "post_code": 156100,
    "pid": 230800,
    "id": 758,
    "area_id": 230882
  },
  {
    "area_name": "七台河市",
    "pinyin": "Qitaihe",
    "level": 2,
    "area_code": 464,
    "post_code": 154600,
    "pid": 230000,
    "id": 759,
    "area_id": 230900
  },
  {
    "area_name": "新兴区",
    "pinyin": "Xinxing",
    "level": 3,
    "area_code": 464,
    "post_code": 154604,
    "pid": 230900,
    "id": 760,
    "area_id": 230902
  },
  {
    "area_name": "桃山区",
    "pinyin": "Taoshan",
    "level": 3,
    "area_code": 464,
    "post_code": 154600,
    "pid": 230900,
    "id": 761,
    "area_id": 230903
  },
  {
    "area_name": "茄子河区",
    "pinyin": "Qiezihe",
    "level": 3,
    "area_code": 464,
    "post_code": 154622,
    "pid": 230900,
    "id": 762,
    "area_id": 230904
  },
  {
    "area_name": "勃利县",
    "pinyin": "Boli",
    "level": 3,
    "area_code": 464,
    "post_code": 154500,
    "pid": 230900,
    "id": 763,
    "area_id": 230921
  },
  {
    "area_name": "牡丹江市",
    "pinyin": "Mudanjiang",
    "level": 2,
    "area_code": 453,
    "post_code": 157000,
    "pid": 230000,
    "id": 764,
    "area_id": 231000
  },
  {
    "area_name": "东安区",
    "pinyin": "Dong'an",
    "level": 3,
    "area_code": 453,
    "post_code": 157000,
    "pid": 231000,
    "id": 765,
    "area_id": 231002
  },
  {
    "area_name": "阳明区",
    "pinyin": "Yangming",
    "level": 3,
    "area_code": 453,
    "post_code": 157013,
    "pid": 231000,
    "id": 766,
    "area_id": 231003
  },
  {
    "area_name": "爱民区",
    "pinyin": "Aimin",
    "level": 3,
    "area_code": 453,
    "post_code": 157009,
    "pid": 231000,
    "id": 767,
    "area_id": 231004
  },
  {
    "area_name": "西安区",
    "pinyin": "Xi'an",
    "level": 3,
    "area_code": 453,
    "post_code": 157000,
    "pid": 231000,
    "id": 768,
    "area_id": 231005
  },
  {
    "area_name": "东宁县",
    "pinyin": "Dongning",
    "level": 3,
    "area_code": 453,
    "post_code": 157200,
    "pid": 231000,
    "id": 769,
    "area_id": 231024
  },
  {
    "area_name": "林口县",
    "pinyin": "Linkou",
    "level": 3,
    "area_code": 453,
    "post_code": 157600,
    "pid": 231000,
    "id": 770,
    "area_id": 231025
  },
  {
    "area_name": "绥芬河市",
    "pinyin": "Suifenhe",
    "level": 3,
    "area_code": 453,
    "post_code": 157300,
    "pid": 231000,
    "id": 771,
    "area_id": 231081
  },
  {
    "area_name": "海林市",
    "pinyin": "Hailin",
    "level": 3,
    "area_code": 453,
    "post_code": 157100,
    "pid": 231000,
    "id": 772,
    "area_id": 231083
  },
  {
    "area_name": "宁安市",
    "pinyin": "Ning'an",
    "level": 3,
    "area_code": 453,
    "post_code": 157400,
    "pid": 231000,
    "id": 773,
    "area_id": 231084
  },
  {
    "area_name": "穆棱市",
    "pinyin": "Muling",
    "level": 3,
    "area_code": 453,
    "post_code": 157500,
    "pid": 231000,
    "id": 774,
    "area_id": 231085
  },
  {
    "area_name": "黑河市",
    "pinyin": "Heihe",
    "level": 2,
    "area_code": 456,
    "post_code": 164300,
    "pid": 230000,
    "id": 775,
    "area_id": 231100
  },
  {
    "area_name": "爱辉区",
    "pinyin": "Aihui",
    "level": 3,
    "area_code": 456,
    "post_code": 164300,
    "pid": 231100,
    "id": 776,
    "area_id": 231102
  },
  {
    "area_name": "嫩江县",
    "pinyin": "Nenjiang",
    "level": 3,
    "area_code": 456,
    "post_code": 161400,
    "pid": 231100,
    "id": 777,
    "area_id": 231121
  },
  {
    "area_name": "逊克县",
    "pinyin": "Xunke",
    "level": 3,
    "area_code": 456,
    "post_code": 164400,
    "pid": 231100,
    "id": 778,
    "area_id": 231123
  },
  {
    "area_name": "孙吴县",
    "pinyin": "Sunwu",
    "level": 3,
    "area_code": 456,
    "post_code": 164200,
    "pid": 231100,
    "id": 779,
    "area_id": 231124
  },
  {
    "area_name": "北安市",
    "pinyin": "Bei'an",
    "level": 3,
    "area_code": 456,
    "post_code": 164000,
    "pid": 231100,
    "id": 780,
    "area_id": 231181
  },
  {
    "area_name": "五大连池市",
    "pinyin": "Wudalianchi",
    "level": 3,
    "area_code": 456,
    "post_code": 164100,
    "pid": 231100,
    "id": 781,
    "area_id": 231182
  },
  {
    "area_name": "绥化市",
    "pinyin": "Suihua",
    "level": 2,
    "area_code": 455,
    "post_code": 152000,
    "pid": 230000,
    "id": 782,
    "area_id": 231200
  },
  {
    "area_name": "北林区",
    "pinyin": "Beilin",
    "level": 3,
    "area_code": 455,
    "post_code": 152000,
    "pid": 231200,
    "id": 783,
    "area_id": 231202
  },
  {
    "area_name": "望奎县",
    "pinyin": "Wangkui",
    "level": 3,
    "area_code": 455,
    "post_code": 152100,
    "pid": 231200,
    "id": 784,
    "area_id": 231221
  },
  {
    "area_name": "兰西县",
    "pinyin": "Lanxi",
    "level": 3,
    "area_code": 455,
    "post_code": 151500,
    "pid": 231200,
    "id": 785,
    "area_id": 231222
  },
  {
    "area_name": "青冈县",
    "pinyin": "Qinggang",
    "level": 3,
    "area_code": 455,
    "post_code": 151600,
    "pid": 231200,
    "id": 786,
    "area_id": 231223
  },
  {
    "area_name": "庆安县",
    "pinyin": "Qing'an",
    "level": 3,
    "area_code": 455,
    "post_code": 152400,
    "pid": 231200,
    "id": 787,
    "area_id": 231224
  },
  {
    "area_name": "明水县",
    "pinyin": "Mingshui",
    "level": 3,
    "area_code": 455,
    "post_code": 151700,
    "pid": 231200,
    "id": 788,
    "area_id": 231225
  },
  {
    "area_name": "绥棱县",
    "pinyin": "Suileng",
    "level": 3,
    "area_code": 455,
    "post_code": 152200,
    "pid": 231200,
    "id": 789,
    "area_id": 231226
  },
  {
    "area_name": "安达市",
    "pinyin": "Anda",
    "level": 3,
    "area_code": 455,
    "post_code": 151400,
    "pid": 231200,
    "id": 790,
    "area_id": 231281
  },
  {
    "area_name": "肇东市",
    "pinyin": "Zhaodong",
    "level": 3,
    "area_code": 455,
    "post_code": 151100,
    "pid": 231200,
    "id": 791,
    "area_id": 231282
  },
  {
    "area_name": "海伦市",
    "pinyin": "Hailun",
    "level": 3,
    "area_code": 455,
    "post_code": 152300,
    "pid": 231200,
    "id": 792,
    "area_id": 231283
  },
  {
    "area_name": "大兴安岭地区",
    "pinyin": "DaXingAnLing",
    "level": 2,
    "area_code": 457,
    "post_code": 165000,
    "pid": 230000,
    "id": 793,
    "area_id": 232700
  },
  {
    "area_name": "加格达奇区",
    "pinyin": "Jiagedaqi",
    "level": 3,
    "area_code": 457,
    "post_code": 165000,
    "pid": 232700,
    "id": 794,
    "area_id": 232701
  },
  {
    "area_name": "新林区",
    "pinyin": "Xinlin",
    "level": 3,
    "area_code": 457,
    "post_code": 165000,
    "pid": 232700,
    "id": 795,
    "area_id": 232702
  },
  {
    "area_name": "松岭区",
    "pinyin": "Songling",
    "level": 3,
    "area_code": 457,
    "post_code": 165000,
    "pid": 232700,
    "id": 796,
    "area_id": 232703
  },
  {
    "area_name": "呼中区",
    "pinyin": "Huzhong",
    "level": 3,
    "area_code": 457,
    "post_code": 165000,
    "pid": 232700,
    "id": 797,
    "area_id": 232704
  },
  {
    "area_name": "呼玛县",
    "pinyin": "Huma",
    "level": 3,
    "area_code": 457,
    "post_code": 165100,
    "pid": 232700,
    "id": 798,
    "area_id": 232721
  },
  {
    "area_name": "塔河县",
    "pinyin": "Tahe",
    "level": 3,
    "area_code": 457,
    "post_code": 165200,
    "pid": 232700,
    "id": 799,
    "area_id": 232722
  },
  {
    "area_name": "漠河县",
    "pinyin": "Mohe",
    "level": 3,
    "area_code": 457,
    "post_code": 165300,
    "pid": 232700,
    "id": 800,
    "area_id": 232723
  },
  {
    "area_name": "上海",
    "pinyin": "Shanghai",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 801,
    "area_id": 310000
  },
  {
    "area_name": "上海市",
    "pinyin": "Shanghai",
    "level": 2,
    "area_code": 21,
    "post_code": 200000,
    "pid": 310000,
    "id": 802,
    "area_id": 310100
  },
  {
    "area_name": "黄浦区",
    "pinyin": "Huangpu",
    "level": 3,
    "area_code": 21,
    "post_code": 200001,
    "pid": 310100,
    "id": 803,
    "area_id": 310101
  },
  {
    "area_name": "徐汇区",
    "pinyin": "Xuhui",
    "level": 3,
    "area_code": 21,
    "post_code": 200030,
    "pid": 310100,
    "id": 804,
    "area_id": 310104
  },
  {
    "area_name": "长宁区",
    "pinyin": "Changning",
    "level": 3,
    "area_code": 21,
    "post_code": 200050,
    "pid": 310100,
    "id": 805,
    "area_id": 310105
  },
  {
    "area_name": "静安区",
    "pinyin": "Jing'an",
    "level": 3,
    "area_code": 21,
    "post_code": 200040,
    "pid": 310100,
    "id": 806,
    "area_id": 310106
  },
  {
    "area_name": "普陀区",
    "pinyin": "Putuo",
    "level": 3,
    "area_code": 21,
    "post_code": 200333,
    "pid": 310100,
    "id": 807,
    "area_id": 310107
  },
  {
    "area_name": "闸北区",
    "pinyin": "Zhabei",
    "level": 3,
    "area_code": 21,
    "post_code": 200070,
    "pid": 310100,
    "id": 808,
    "area_id": 310108
  },
  {
    "area_name": "虹口区",
    "pinyin": "Hongkou",
    "level": 3,
    "area_code": 21,
    "post_code": 200086,
    "pid": 310100,
    "id": 809,
    "area_id": 310109
  },
  {
    "area_name": "杨浦区",
    "pinyin": "Yangpu",
    "level": 3,
    "area_code": 21,
    "post_code": 200082,
    "pid": 310100,
    "id": 810,
    "area_id": 310110
  },
  {
    "area_name": "闵行区",
    "pinyin": "Minhang",
    "level": 3,
    "area_code": 21,
    "post_code": 201100,
    "pid": 310100,
    "id": 811,
    "area_id": 310112
  },
  {
    "area_name": "宝山区",
    "pinyin": "Baoshan",
    "level": 3,
    "area_code": 21,
    "post_code": 201900,
    "pid": 310100,
    "id": 812,
    "area_id": 310113
  },
  {
    "area_name": "嘉定区",
    "pinyin": "Jiading",
    "level": 3,
    "area_code": 21,
    "post_code": 201800,
    "pid": 310100,
    "id": 813,
    "area_id": 310114
  },
  {
    "area_name": "浦东新区",
    "pinyin": "Pudong",
    "level": 3,
    "area_code": 21,
    "post_code": 200135,
    "pid": 310100,
    "id": 814,
    "area_id": 310115
  },
  {
    "area_name": "金山区",
    "pinyin": "Jinshan",
    "level": 3,
    "area_code": 21,
    "post_code": 200540,
    "pid": 310100,
    "id": 815,
    "area_id": 310116
  },
  {
    "area_name": "松江区",
    "pinyin": "Songjiang",
    "level": 3,
    "area_code": 21,
    "post_code": 201600,
    "pid": 310100,
    "id": 816,
    "area_id": 310117
  },
  {
    "area_name": "青浦区",
    "pinyin": "Qingpu",
    "level": 3,
    "area_code": 21,
    "post_code": 201700,
    "pid": 310100,
    "id": 817,
    "area_id": 310118
  },
  {
    "area_name": "奉贤区",
    "pinyin": "Fengxian",
    "level": 3,
    "area_code": 21,
    "post_code": 201400,
    "pid": 310100,
    "id": 818,
    "area_id": 310120
  },
  {
    "area_name": "崇明县",
    "pinyin": "Chongming",
    "level": 3,
    "area_code": 21,
    "post_code": 202150,
    "pid": 310100,
    "id": 819,
    "area_id": 310230
  },
  {
    "area_name": "江苏省",
    "pinyin": "Jiangsu",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 820,
    "area_id": 320000
  },
  {
    "area_name": "南京市",
    "pinyin": "Nanjing",
    "level": 2,
    "area_code": 25,
    "post_code": 210008,
    "pid": 320000,
    "id": 821,
    "area_id": 320100
  },
  {
    "area_name": "玄武区",
    "pinyin": "Xuanwu",
    "level": 3,
    "area_code": 25,
    "post_code": 210018,
    "pid": 320100,
    "id": 822,
    "area_id": 320102
  },
  {
    "area_name": "秦淮区",
    "pinyin": "Qinhuai",
    "level": 3,
    "area_code": 25,
    "post_code": 210001,
    "pid": 320100,
    "id": 823,
    "area_id": 320104
  },
  {
    "area_name": "建邺区",
    "pinyin": "Jianye",
    "level": 3,
    "area_code": 25,
    "post_code": 210004,
    "pid": 320100,
    "id": 824,
    "area_id": 320105
  },
  {
    "area_name": "鼓楼区",
    "pinyin": "Gulou",
    "level": 3,
    "area_code": 25,
    "post_code": 210009,
    "pid": 320100,
    "id": 825,
    "area_id": 320106
  },
  {
    "area_name": "浦口区",
    "pinyin": "Pukou",
    "level": 3,
    "area_code": 25,
    "post_code": 211800,
    "pid": 320100,
    "id": 826,
    "area_id": 320111
  },
  {
    "area_name": "栖霞区",
    "pinyin": "Qixia",
    "level": 3,
    "area_code": 25,
    "post_code": 210046,
    "pid": 320100,
    "id": 827,
    "area_id": 320113
  },
  {
    "area_name": "雨花台区",
    "pinyin": "Yuhuatai",
    "level": 3,
    "area_code": 25,
    "post_code": 210012,
    "pid": 320100,
    "id": 828,
    "area_id": 320114
  },
  {
    "area_name": "江宁区",
    "pinyin": "Jiangning",
    "level": 3,
    "area_code": 25,
    "post_code": 211100,
    "pid": 320100,
    "id": 829,
    "area_id": 320115
  },
  {
    "area_name": "六合区",
    "pinyin": "Luhe",
    "level": 3,
    "area_code": 25,
    "post_code": 211500,
    "pid": 320100,
    "id": 830,
    "area_id": 320116
  },
  {
    "area_name": "溧水区",
    "pinyin": "Lishui",
    "level": 3,
    "area_code": 25,
    "post_code": 211200,
    "pid": 320100,
    "id": 831,
    "area_id": 320117
  },
  {
    "area_name": "高淳区",
    "pinyin": "Gaochun",
    "level": 3,
    "area_code": 25,
    "post_code": 211300,
    "pid": 320100,
    "id": 832,
    "area_id": 320118
  },
  {
    "area_name": "无锡市",
    "pinyin": "Wuxi",
    "level": 2,
    "area_code": 510,
    "post_code": 214000,
    "pid": 320000,
    "id": 833,
    "area_id": 320200
  },
  {
    "area_name": "崇安区",
    "pinyin": "Chong'an",
    "level": 3,
    "area_code": 510,
    "post_code": 214001,
    "pid": 320200,
    "id": 834,
    "area_id": 320202
  },
  {
    "area_name": "南长区",
    "pinyin": "Nanchang",
    "level": 3,
    "area_code": 510,
    "post_code": 214021,
    "pid": 320200,
    "id": 835,
    "area_id": 320203
  },
  {
    "area_name": "北塘区",
    "pinyin": "Beitang",
    "level": 3,
    "area_code": 510,
    "post_code": 214044,
    "pid": 320200,
    "id": 836,
    "area_id": 320204
  },
  {
    "area_name": "锡山区",
    "pinyin": "Xishan",
    "level": 3,
    "area_code": 510,
    "post_code": 214101,
    "pid": 320200,
    "id": 837,
    "area_id": 320205
  },
  {
    "area_name": "惠山区",
    "pinyin": "Huishan",
    "level": 3,
    "area_code": 510,
    "post_code": 214174,
    "pid": 320200,
    "id": 838,
    "area_id": 320206
  },
  {
    "area_name": "滨湖区",
    "pinyin": "Binhu",
    "level": 3,
    "area_code": 510,
    "post_code": 214123,
    "pid": 320200,
    "id": 839,
    "area_id": 320211
  },
  {
    "area_name": "江阴市",
    "pinyin": "Jiangyin",
    "level": 3,
    "area_code": 510,
    "post_code": 214431,
    "pid": 320200,
    "id": 840,
    "area_id": 320281
  },
  {
    "area_name": "宜兴市",
    "pinyin": "Yixing",
    "level": 3,
    "area_code": 510,
    "post_code": 214200,
    "pid": 320200,
    "id": 841,
    "area_id": 320282
  },
  {
    "area_name": "徐州市",
    "pinyin": "Xuzhou",
    "level": 2,
    "area_code": 516,
    "post_code": 221003,
    "pid": 320000,
    "id": 842,
    "area_id": 320300
  },
  {
    "area_name": "鼓楼区",
    "pinyin": "Gulou",
    "level": 3,
    "area_code": 516,
    "post_code": 221005,
    "pid": 320300,
    "id": 843,
    "area_id": 320302
  },
  {
    "area_name": "云龙区",
    "pinyin": "Yunlong",
    "level": 3,
    "area_code": 516,
    "post_code": 221007,
    "pid": 320300,
    "id": 844,
    "area_id": 320303
  },
  {
    "area_name": "贾汪区",
    "pinyin": "Jiawang",
    "level": 3,
    "area_code": 516,
    "post_code": 221003,
    "pid": 320300,
    "id": 845,
    "area_id": 320305
  },
  {
    "area_name": "泉山区",
    "pinyin": "Quanshan",
    "level": 3,
    "area_code": 516,
    "post_code": 221006,
    "pid": 320300,
    "id": 846,
    "area_id": 320311
  },
  {
    "area_name": "铜山区",
    "pinyin": "Tongshan",
    "level": 3,
    "area_code": 516,
    "post_code": 221106,
    "pid": 320300,
    "id": 847,
    "area_id": 320312
  },
  {
    "area_name": "丰县",
    "pinyin": "Fengxian",
    "level": 3,
    "area_code": 516,
    "post_code": 221700,
    "pid": 320300,
    "id": 848,
    "area_id": 320321
  },
  {
    "area_name": "沛县",
    "pinyin": "Peixian",
    "level": 3,
    "area_code": 516,
    "post_code": 221600,
    "pid": 320300,
    "id": 849,
    "area_id": 320322
  },
  {
    "area_name": "睢宁县",
    "pinyin": "Suining",
    "level": 3,
    "area_code": 516,
    "post_code": 221200,
    "pid": 320300,
    "id": 850,
    "area_id": 320324
  },
  {
    "area_name": "新沂市",
    "pinyin": "Xinyi",
    "level": 3,
    "area_code": 516,
    "post_code": 221400,
    "pid": 320300,
    "id": 851,
    "area_id": 320381
  },
  {
    "area_name": "邳州市",
    "pinyin": "Pizhou",
    "level": 3,
    "area_code": 516,
    "post_code": 221300,
    "pid": 320300,
    "id": 852,
    "area_id": 320382
  },
  {
    "area_name": "常州市",
    "pinyin": "Changzhou",
    "level": 2,
    "area_code": 519,
    "post_code": 213000,
    "pid": 320000,
    "id": 853,
    "area_id": 320400
  },
  {
    "area_name": "天宁区",
    "pinyin": "Tianning",
    "level": 3,
    "area_code": 519,
    "post_code": 213000,
    "pid": 320400,
    "id": 854,
    "area_id": 320402
  },
  {
    "area_name": "钟楼区",
    "pinyin": "Zhonglou",
    "level": 3,
    "area_code": 519,
    "post_code": 213023,
    "pid": 320400,
    "id": 855,
    "area_id": 320404
  },
  {
    "area_name": "戚墅堰区",
    "pinyin": "Qishuyan",
    "level": 3,
    "area_code": 519,
    "post_code": 213025,
    "pid": 320400,
    "id": 856,
    "area_id": 320405
  },
  {
    "area_name": "新北区",
    "pinyin": "Xinbei",
    "level": 3,
    "area_code": 519,
    "post_code": 213022,
    "pid": 320400,
    "id": 857,
    "area_id": 320411
  },
  {
    "area_name": "武进区",
    "pinyin": "Wujin",
    "level": 3,
    "area_code": 519,
    "post_code": 213100,
    "pid": 320400,
    "id": 858,
    "area_id": 320412
  },
  {
    "area_name": "溧阳市",
    "pinyin": "Liyang",
    "level": 3,
    "area_code": 519,
    "post_code": 213300,
    "pid": 320400,
    "id": 859,
    "area_id": 320481
  },
  {
    "area_name": "金坛市",
    "pinyin": "Jintan",
    "level": 3,
    "area_code": 519,
    "post_code": 213200,
    "pid": 320400,
    "id": 860,
    "area_id": 320482
  },
  {
    "area_name": "苏州市",
    "pinyin": "Suzhou",
    "level": 2,
    "area_code": 512,
    "post_code": 215002,
    "pid": 320000,
    "id": 861,
    "area_id": 320500
  },
  {
    "area_name": "虎丘区",
    "pinyin": "Huqiu",
    "level": 3,
    "area_code": 512,
    "post_code": 215004,
    "pid": 320500,
    "id": 862,
    "area_id": 320505
  },
  {
    "area_name": "吴中区",
    "pinyin": "Wuzhong",
    "level": 3,
    "area_code": 512,
    "post_code": 215128,
    "pid": 320500,
    "id": 863,
    "area_id": 320506
  },
  {
    "area_name": "相城区",
    "pinyin": "Xiangcheng",
    "level": 3,
    "area_code": 512,
    "post_code": 215131,
    "pid": 320500,
    "id": 864,
    "area_id": 320507
  },
  {
    "area_name": "姑苏区",
    "pinyin": "Gusu",
    "level": 3,
    "area_code": 512,
    "post_code": 215031,
    "pid": 320500,
    "id": 865,
    "area_id": 320508
  },
  {
    "area_name": "吴江区",
    "pinyin": "Wujiang",
    "level": 3,
    "area_code": 512,
    "post_code": 215200,
    "pid": 320500,
    "id": 866,
    "area_id": 320509
  },
  {
    "area_name": "常熟市",
    "pinyin": "Changshu",
    "level": 3,
    "area_code": 512,
    "post_code": 215500,
    "pid": 320500,
    "id": 867,
    "area_id": 320581
  },
  {
    "area_name": "张家港市",
    "pinyin": "Zhangjiagang",
    "level": 3,
    "area_code": 512,
    "post_code": 215600,
    "pid": 320500,
    "id": 868,
    "area_id": 320582
  },
  {
    "area_name": "昆山市",
    "pinyin": "Kunshan",
    "level": 3,
    "area_code": 512,
    "post_code": 215300,
    "pid": 320500,
    "id": 869,
    "area_id": 320583
  },
  {
    "area_name": "太仓市",
    "pinyin": "Taicang",
    "level": 3,
    "area_code": 512,
    "post_code": 215400,
    "pid": 320500,
    "id": 870,
    "area_id": 320585
  },
  {
    "area_name": "南通市",
    "pinyin": "Nantong",
    "level": 2,
    "area_code": 513,
    "post_code": 226001,
    "pid": 320000,
    "id": 871,
    "area_id": 320600
  },
  {
    "area_name": "崇川区",
    "pinyin": "Chongchuan",
    "level": 3,
    "area_code": 513,
    "post_code": 226001,
    "pid": 320600,
    "id": 872,
    "area_id": 320602
  },
  {
    "area_name": "港闸区",
    "pinyin": "Gangzha",
    "level": 3,
    "area_code": 513,
    "post_code": 226001,
    "pid": 320600,
    "id": 873,
    "area_id": 320611
  },
  {
    "area_name": "通州区",
    "pinyin": "Tongzhou",
    "level": 3,
    "area_code": 513,
    "post_code": 226300,
    "pid": 320600,
    "id": 874,
    "area_id": 320612
  },
  {
    "area_name": "海安县",
    "pinyin": "Hai'an",
    "level": 3,
    "area_code": 513,
    "post_code": 226600,
    "pid": 320600,
    "id": 875,
    "area_id": 320621
  },
  {
    "area_name": "如东县",
    "pinyin": "Rudong",
    "level": 3,
    "area_code": 513,
    "post_code": 226400,
    "pid": 320600,
    "id": 876,
    "area_id": 320623
  },
  {
    "area_name": "启东市",
    "pinyin": "Qidong",
    "level": 3,
    "area_code": 513,
    "post_code": 226200,
    "pid": 320600,
    "id": 877,
    "area_id": 320681
  },
  {
    "area_name": "如皋市",
    "pinyin": "Rugao",
    "level": 3,
    "area_code": 513,
    "post_code": 226500,
    "pid": 320600,
    "id": 878,
    "area_id": 320682
  },
  {
    "area_name": "海门市",
    "pinyin": "Haimen",
    "level": 3,
    "area_code": 513,
    "post_code": 226100,
    "pid": 320600,
    "id": 879,
    "area_id": 320684
  },
  {
    "area_name": "连云港市",
    "pinyin": "Lianyungang",
    "level": 2,
    "area_code": 518,
    "post_code": 222002,
    "pid": 320000,
    "id": 880,
    "area_id": 320700
  },
  {
    "area_name": "连云区",
    "pinyin": "Lianyun",
    "level": 3,
    "area_code": 518,
    "post_code": 222042,
    "pid": 320700,
    "id": 881,
    "area_id": 320703
  },
  {
    "area_name": "海州区",
    "pinyin": "Haizhou",
    "level": 3,
    "area_code": 518,
    "post_code": 222003,
    "pid": 320700,
    "id": 882,
    "area_id": 320706
  },
  {
    "area_name": "赣榆区",
    "pinyin": "Ganyu",
    "level": 3,
    "area_code": 518,
    "post_code": 222100,
    "pid": 320700,
    "id": 883,
    "area_id": 320707
  },
  {
    "area_name": "东海县",
    "pinyin": "Donghai",
    "level": 3,
    "area_code": 518,
    "post_code": 222300,
    "pid": 320700,
    "id": 884,
    "area_id": 320722
  },
  {
    "area_name": "灌云县",
    "pinyin": "Guanyun",
    "level": 3,
    "area_code": 518,
    "post_code": 222200,
    "pid": 320700,
    "id": 885,
    "area_id": 320723
  },
  {
    "area_name": "灌南县",
    "pinyin": "Guannan",
    "level": 3,
    "area_code": 518,
    "post_code": 222500,
    "pid": 320700,
    "id": 886,
    "area_id": 320724
  },
  {
    "area_name": "淮安市",
    "pinyin": "Huai'an",
    "level": 2,
    "area_code": 517,
    "post_code": 223001,
    "pid": 320000,
    "id": 887,
    "area_id": 320800
  },
  {
    "area_name": "清河区",
    "pinyin": "Qinghe",
    "level": 3,
    "area_code": 517,
    "post_code": 223001,
    "pid": 320800,
    "id": 888,
    "area_id": 320802
  },
  {
    "area_name": "淮安区",
    "pinyin": "Huai'an",
    "level": 3,
    "area_code": 517,
    "post_code": 223200,
    "pid": 320800,
    "id": 889,
    "area_id": 320803
  },
  {
    "area_name": "淮阴区",
    "pinyin": "Huaiyin",
    "level": 3,
    "area_code": 517,
    "post_code": 223300,
    "pid": 320800,
    "id": 890,
    "area_id": 320804
  },
  {
    "area_name": "清浦区",
    "pinyin": "Qingpu",
    "level": 3,
    "area_code": 517,
    "post_code": 223002,
    "pid": 320800,
    "id": 891,
    "area_id": 320811
  },
  {
    "area_name": "涟水县",
    "pinyin": "Lianshui",
    "level": 3,
    "area_code": 517,
    "post_code": 223400,
    "pid": 320800,
    "id": 892,
    "area_id": 320826
  },
  {
    "area_name": "洪泽县",
    "pinyin": "Hongze",
    "level": 3,
    "area_code": 517,
    "post_code": 223100,
    "pid": 320800,
    "id": 893,
    "area_id": 320829
  },
  {
    "area_name": "盱眙县",
    "pinyin": "Xuyi",
    "level": 3,
    "area_code": 517,
    "post_code": 211700,
    "pid": 320800,
    "id": 894,
    "area_id": 320830
  },
  {
    "area_name": "金湖县",
    "pinyin": "Jinhu",
    "level": 3,
    "area_code": 517,
    "post_code": 211600,
    "pid": 320800,
    "id": 895,
    "area_id": 320831
  },
  {
    "area_name": "盐城市",
    "pinyin": "Yancheng",
    "level": 2,
    "area_code": 515,
    "post_code": 224005,
    "pid": 320000,
    "id": 896,
    "area_id": 320900
  },
  {
    "area_name": "亭湖区",
    "pinyin": "Tinghu",
    "level": 3,
    "area_code": 515,
    "post_code": 224005,
    "pid": 320900,
    "id": 897,
    "area_id": 320902
  },
  {
    "area_name": "盐都区",
    "pinyin": "Yandu",
    "level": 3,
    "area_code": 515,
    "post_code": 224055,
    "pid": 320900,
    "id": 898,
    "area_id": 320903
  },
  {
    "area_name": "响水县",
    "pinyin": "Xiangshui",
    "level": 3,
    "area_code": 515,
    "post_code": 224600,
    "pid": 320900,
    "id": 899,
    "area_id": 320921
  },
  {
    "area_name": "滨海县",
    "pinyin": "Binhai",
    "level": 3,
    "area_code": 515,
    "post_code": 224500,
    "pid": 320900,
    "id": 900,
    "area_id": 320922
  },
  {
    "area_name": "阜宁县",
    "pinyin": "Funing",
    "level": 3,
    "area_code": 515,
    "post_code": 224400,
    "pid": 320900,
    "id": 901,
    "area_id": 320923
  },
  {
    "area_name": "射阳县",
    "pinyin": "Sheyang",
    "level": 3,
    "area_code": 515,
    "post_code": 224300,
    "pid": 320900,
    "id": 902,
    "area_id": 320924
  },
  {
    "area_name": "建湖县",
    "pinyin": "Jianhu",
    "level": 3,
    "area_code": 515,
    "post_code": 224700,
    "pid": 320900,
    "id": 903,
    "area_id": 320925
  },
  {
    "area_name": "东台市",
    "pinyin": "Dongtai",
    "level": 3,
    "area_code": 515,
    "post_code": 224200,
    "pid": 320900,
    "id": 904,
    "area_id": 320981
  },
  {
    "area_name": "大丰市",
    "pinyin": "Dafeng",
    "level": 3,
    "area_code": 515,
    "post_code": 224100,
    "pid": 320900,
    "id": 905,
    "area_id": 320982
  },
  {
    "area_name": "扬州市",
    "pinyin": "Yangzhou",
    "level": 2,
    "area_code": 514,
    "post_code": 225002,
    "pid": 320000,
    "id": 906,
    "area_id": 321000
  },
  {
    "area_name": "广陵区",
    "pinyin": "Guangling",
    "level": 3,
    "area_code": 514,
    "post_code": 225002,
    "pid": 321000,
    "id": 907,
    "area_id": 321002
  },
  {
    "area_name": "邗江区",
    "pinyin": "Hanjiang",
    "level": 3,
    "area_code": 514,
    "post_code": 225002,
    "pid": 321000,
    "id": 908,
    "area_id": 321003
  },
  {
    "area_name": "江都区",
    "pinyin": "Jiangdu",
    "level": 3,
    "area_code": 514,
    "post_code": 225200,
    "pid": 321000,
    "id": 909,
    "area_id": 321012
  },
  {
    "area_name": "宝应县",
    "pinyin": "Baoying",
    "level": 3,
    "area_code": 514,
    "post_code": 225800,
    "pid": 321000,
    "id": 910,
    "area_id": 321023
  },
  {
    "area_name": "仪征市",
    "pinyin": "Yizheng",
    "level": 3,
    "area_code": 514,
    "post_code": 211400,
    "pid": 321000,
    "id": 911,
    "area_id": 321081
  },
  {
    "area_name": "高邮市",
    "pinyin": "Gaoyou",
    "level": 3,
    "area_code": 514,
    "post_code": 225600,
    "pid": 321000,
    "id": 912,
    "area_id": 321084
  },
  {
    "area_name": "镇江市",
    "pinyin": "Zhenjiang",
    "level": 2,
    "area_code": 511,
    "post_code": 212004,
    "pid": 320000,
    "id": 913,
    "area_id": 321100
  },
  {
    "area_name": "京口区",
    "pinyin": "Jingkou",
    "level": 3,
    "area_code": 511,
    "post_code": 212003,
    "pid": 321100,
    "id": 914,
    "area_id": 321102
  },
  {
    "area_name": "润州区",
    "pinyin": "Runzhou",
    "level": 3,
    "area_code": 511,
    "post_code": 212005,
    "pid": 321100,
    "id": 915,
    "area_id": 321111
  },
  {
    "area_name": "丹徒区",
    "pinyin": "Dantu",
    "level": 3,
    "area_code": 511,
    "post_code": 212028,
    "pid": 321100,
    "id": 916,
    "area_id": 321112
  },
  {
    "area_name": "丹阳市",
    "pinyin": "Danyang",
    "level": 3,
    "area_code": 511,
    "post_code": 212300,
    "pid": 321100,
    "id": 917,
    "area_id": 321181
  },
  {
    "area_name": "扬中市",
    "pinyin": "Yangzhong",
    "level": 3,
    "area_code": 511,
    "post_code": 212200,
    "pid": 321100,
    "id": 918,
    "area_id": 321182
  },
  {
    "area_name": "句容市",
    "pinyin": "Jurong",
    "level": 3,
    "area_code": 511,
    "post_code": 212400,
    "pid": 321100,
    "id": 919,
    "area_id": 321183
  },
  {
    "area_name": "泰州市",
    "pinyin": "Taizhou",
    "level": 2,
    "area_code": 523,
    "post_code": 225300,
    "pid": 320000,
    "id": 920,
    "area_id": 321200
  },
  {
    "area_name": "海陵区",
    "pinyin": "Hailing",
    "level": 3,
    "area_code": 523,
    "post_code": 225300,
    "pid": 321200,
    "id": 921,
    "area_id": 321202
  },
  {
    "area_name": "高港区",
    "pinyin": "Gaogang",
    "level": 3,
    "area_code": 523,
    "post_code": 225321,
    "pid": 321200,
    "id": 922,
    "area_id": 321203
  },
  {
    "area_name": "姜堰区",
    "pinyin": "Jiangyan",
    "level": 3,
    "area_code": 523,
    "post_code": 225500,
    "pid": 321200,
    "id": 923,
    "area_id": 321204
  },
  {
    "area_name": "兴化市",
    "pinyin": "Xinghua",
    "level": 3,
    "area_code": 523,
    "post_code": 225700,
    "pid": 321200,
    "id": 924,
    "area_id": 321281
  },
  {
    "area_name": "靖江市",
    "pinyin": "Jingjiang",
    "level": 3,
    "area_code": 523,
    "post_code": 214500,
    "pid": 321200,
    "id": 925,
    "area_id": 321282
  },
  {
    "area_name": "泰兴市",
    "pinyin": "Taixing",
    "level": 3,
    "area_code": 523,
    "post_code": 225400,
    "pid": 321200,
    "id": 926,
    "area_id": 321283
  },
  {
    "area_name": "宿迁市",
    "pinyin": "Suqian",
    "level": 2,
    "area_code": 527,
    "post_code": 223800,
    "pid": 320000,
    "id": 927,
    "area_id": 321300
  },
  {
    "area_name": "宿城区",
    "pinyin": "Sucheng",
    "level": 3,
    "area_code": 527,
    "post_code": 223800,
    "pid": 321300,
    "id": 928,
    "area_id": 321302
  },
  {
    "area_name": "宿豫区",
    "pinyin": "Suyu",
    "level": 3,
    "area_code": 527,
    "post_code": 223800,
    "pid": 321300,
    "id": 929,
    "area_id": 321311
  },
  {
    "area_name": "沭阳县",
    "pinyin": "Shuyang",
    "level": 3,
    "area_code": 527,
    "post_code": 223600,
    "pid": 321300,
    "id": 930,
    "area_id": 321322
  },
  {
    "area_name": "泗阳县",
    "pinyin": "Siyang",
    "level": 3,
    "area_code": 527,
    "post_code": 223700,
    "pid": 321300,
    "id": 931,
    "area_id": 321323
  },
  {
    "area_name": "泗洪县",
    "pinyin": "Sihong",
    "level": 3,
    "area_code": 527,
    "post_code": 223900,
    "pid": 321300,
    "id": 932,
    "area_id": 321324
  },
  {
    "area_name": "浙江省",
    "pinyin": "Zhejiang",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 933,
    "area_id": 330000
  },
  {
    "area_name": "杭州市",
    "pinyin": "Hangzhou",
    "level": 2,
    "area_code": 571,
    "post_code": 310026,
    "pid": 330000,
    "id": 934,
    "area_id": 330100
  },
  {
    "area_name": "上城区",
    "pinyin": "Shangcheng",
    "level": 3,
    "area_code": 571,
    "post_code": 310002,
    "pid": 330100,
    "id": 935,
    "area_id": 330102
  },
  {
    "area_name": "下城区",
    "pinyin": "Xiacheng",
    "level": 3,
    "area_code": 571,
    "post_code": 310006,
    "pid": 330100,
    "id": 936,
    "area_id": 330103
  },
  {
    "area_name": "江干区",
    "pinyin": "Jianggan",
    "level": 3,
    "area_code": 571,
    "post_code": 310016,
    "pid": 330100,
    "id": 937,
    "area_id": 330104
  },
  {
    "area_name": "拱墅区",
    "pinyin": "Gongshu",
    "level": 3,
    "area_code": 571,
    "post_code": 310011,
    "pid": 330100,
    "id": 938,
    "area_id": 330105
  },
  {
    "area_name": "西湖区",
    "pinyin": "Xihu",
    "level": 3,
    "area_code": 571,
    "post_code": 310013,
    "pid": 330100,
    "id": 939,
    "area_id": 330106
  },
  {
    "area_name": "滨江区",
    "pinyin": "Binjiang",
    "level": 3,
    "area_code": 571,
    "post_code": 310051,
    "pid": 330100,
    "id": 940,
    "area_id": 330108
  },
  {
    "area_name": "萧山区",
    "pinyin": "Xiaoshan",
    "level": 3,
    "area_code": 571,
    "post_code": 311200,
    "pid": 330100,
    "id": 941,
    "area_id": 330109
  },
  {
    "area_name": "余杭区",
    "pinyin": "Yuhang",
    "level": 3,
    "area_code": 571,
    "post_code": 311100,
    "pid": 330100,
    "id": 942,
    "area_id": 330110
  },
  {
    "area_name": "桐庐县",
    "pinyin": "Tonglu",
    "level": 3,
    "area_code": 571,
    "post_code": 311500,
    "pid": 330100,
    "id": 943,
    "area_id": 330122
  },
  {
    "area_name": "淳安县",
    "pinyin": "Chun'an",
    "level": 3,
    "area_code": 571,
    "post_code": 311700,
    "pid": 330100,
    "id": 944,
    "area_id": 330127
  },
  {
    "area_name": "建德市",
    "pinyin": "Jiande",
    "level": 3,
    "area_code": 571,
    "post_code": 311600,
    "pid": 330100,
    "id": 945,
    "area_id": 330182
  },
  {
    "area_name": "富阳区",
    "pinyin": "Fuyang",
    "level": 3,
    "area_code": 571,
    "post_code": 311400,
    "pid": 330100,
    "id": 946,
    "area_id": 330183
  },
  {
    "area_name": "临安市",
    "pinyin": "Lin'an",
    "level": 3,
    "area_code": 571,
    "post_code": 311300,
    "pid": 330100,
    "id": 947,
    "area_id": 330185
  },
  {
    "area_name": "宁波市",
    "pinyin": "Ningbo",
    "level": 2,
    "area_code": 574,
    "post_code": 315000,
    "pid": 330000,
    "id": 948,
    "area_id": 330200
  },
  {
    "area_name": "海曙区",
    "pinyin": "Haishu",
    "level": 3,
    "area_code": 574,
    "post_code": 315000,
    "pid": 330200,
    "id": 949,
    "area_id": 330203
  },
  {
    "area_name": "江东区",
    "pinyin": "Jiangdong",
    "level": 3,
    "area_code": 574,
    "post_code": 315040,
    "pid": 330200,
    "id": 950,
    "area_id": 330204
  },
  {
    "area_name": "江北区",
    "pinyin": "Jiangbei",
    "level": 3,
    "area_code": 574,
    "post_code": 315020,
    "pid": 330200,
    "id": 951,
    "area_id": 330205
  },
  {
    "area_name": "北仑区",
    "pinyin": "Beilun",
    "level": 3,
    "area_code": 574,
    "post_code": 315800,
    "pid": 330200,
    "id": 952,
    "area_id": 330206
  },
  {
    "area_name": "镇海区",
    "pinyin": "Zhenhai",
    "level": 3,
    "area_code": 574,
    "post_code": 315200,
    "pid": 330200,
    "id": 953,
    "area_id": 330211
  },
  {
    "area_name": "鄞州区",
    "pinyin": "Yinzhou",
    "level": 3,
    "area_code": 574,
    "post_code": 315100,
    "pid": 330200,
    "id": 954,
    "area_id": 330212
  },
  {
    "area_name": "象山县",
    "pinyin": "Xiangshan",
    "level": 3,
    "area_code": 574,
    "post_code": 315700,
    "pid": 330200,
    "id": 955,
    "area_id": 330225
  },
  {
    "area_name": "宁海县",
    "pinyin": "Ninghai",
    "level": 3,
    "area_code": 574,
    "post_code": 315600,
    "pid": 330200,
    "id": 956,
    "area_id": 330226
  },
  {
    "area_name": "余姚市",
    "pinyin": "Yuyao",
    "level": 3,
    "area_code": 574,
    "post_code": 315400,
    "pid": 330200,
    "id": 957,
    "area_id": 330281
  },
  {
    "area_name": "慈溪市",
    "pinyin": "Cixi",
    "level": 3,
    "area_code": 574,
    "post_code": 315300,
    "pid": 330200,
    "id": 958,
    "area_id": 330282
  },
  {
    "area_name": "奉化市",
    "pinyin": "Fenghua",
    "level": 3,
    "area_code": 574,
    "post_code": 315500,
    "pid": 330200,
    "id": 959,
    "area_id": 330283
  },
  {
    "area_name": "温州市",
    "pinyin": "Wenzhou",
    "level": 2,
    "area_code": 577,
    "post_code": 325000,
    "pid": 330000,
    "id": 960,
    "area_id": 330300
  },
  {
    "area_name": "鹿城区",
    "pinyin": "Lucheng",
    "level": 3,
    "area_code": 577,
    "post_code": 325000,
    "pid": 330300,
    "id": 961,
    "area_id": 330302
  },
  {
    "area_name": "龙湾区",
    "pinyin": "Longwan",
    "level": 3,
    "area_code": 577,
    "post_code": 325013,
    "pid": 330300,
    "id": 962,
    "area_id": 330303
  },
  {
    "area_name": "瓯海区",
    "pinyin": "Ouhai",
    "level": 3,
    "area_code": 577,
    "post_code": 325005,
    "pid": 330300,
    "id": 963,
    "area_id": 330304
  },
  {
    "area_name": "洞头县",
    "pinyin": "Dongtou",
    "level": 3,
    "area_code": 577,
    "post_code": 325700,
    "pid": 330300,
    "id": 964,
    "area_id": 330322
  },
  {
    "area_name": "永嘉县",
    "pinyin": "Yongjia",
    "level": 3,
    "area_code": 577,
    "post_code": 325100,
    "pid": 330300,
    "id": 965,
    "area_id": 330324
  },
  {
    "area_name": "平阳县",
    "pinyin": "Pingyang",
    "level": 3,
    "area_code": 577,
    "post_code": 325400,
    "pid": 330300,
    "id": 966,
    "area_id": 330326
  },
  {
    "area_name": "苍南县",
    "pinyin": "Cangnan",
    "level": 3,
    "area_code": 577,
    "post_code": 325800,
    "pid": 330300,
    "id": 967,
    "area_id": 330327
  },
  {
    "area_name": "文成县",
    "pinyin": "Wencheng",
    "level": 3,
    "area_code": 577,
    "post_code": 325300,
    "pid": 330300,
    "id": 968,
    "area_id": 330328
  },
  {
    "area_name": "泰顺县",
    "pinyin": "Taishun",
    "level": 3,
    "area_code": 577,
    "post_code": 325500,
    "pid": 330300,
    "id": 969,
    "area_id": 330329
  },
  {
    "area_name": "瑞安市",
    "pinyin": "Rui'an",
    "level": 3,
    "area_code": 577,
    "post_code": 325200,
    "pid": 330300,
    "id": 970,
    "area_id": 330381
  },
  {
    "area_name": "乐清市",
    "pinyin": "Yueqing",
    "level": 3,
    "area_code": 577,
    "post_code": 325600,
    "pid": 330300,
    "id": 971,
    "area_id": 330382
  },
  {
    "area_name": "嘉兴市",
    "pinyin": "Jiaxing",
    "level": 2,
    "area_code": 573,
    "post_code": 314000,
    "pid": 330000,
    "id": 972,
    "area_id": 330400
  },
  {
    "area_name": "南湖区",
    "pinyin": "Nanhu",
    "level": 3,
    "area_code": 573,
    "post_code": 314051,
    "pid": 330400,
    "id": 973,
    "area_id": 330402
  },
  {
    "area_name": "秀洲区",
    "pinyin": "Xiuzhou",
    "level": 3,
    "area_code": 573,
    "post_code": 314031,
    "pid": 330400,
    "id": 974,
    "area_id": 330411
  },
  {
    "area_name": "嘉善县",
    "pinyin": "Jiashan",
    "level": 3,
    "area_code": 573,
    "post_code": 314100,
    "pid": 330400,
    "id": 975,
    "area_id": 330421
  },
  {
    "area_name": "海盐县",
    "pinyin": "Haiyan",
    "level": 3,
    "area_code": 573,
    "post_code": 314300,
    "pid": 330400,
    "id": 976,
    "area_id": 330424
  },
  {
    "area_name": "海宁市",
    "pinyin": "Haining",
    "level": 3,
    "area_code": 573,
    "post_code": 314400,
    "pid": 330400,
    "id": 977,
    "area_id": 330481
  },
  {
    "area_name": "平湖市",
    "pinyin": "Pinghu",
    "level": 3,
    "area_code": 573,
    "post_code": 314200,
    "pid": 330400,
    "id": 978,
    "area_id": 330482
  },
  {
    "area_name": "桐乡市",
    "pinyin": "Tongxiang",
    "level": 3,
    "area_code": 573,
    "post_code": 314500,
    "pid": 330400,
    "id": 979,
    "area_id": 330483
  },
  {
    "area_name": "湖州市",
    "pinyin": "Huzhou",
    "level": 2,
    "area_code": 572,
    "post_code": 313000,
    "pid": 330000,
    "id": 980,
    "area_id": 330500
  },
  {
    "area_name": "吴兴区",
    "pinyin": "Wuxing",
    "level": 3,
    "area_code": 572,
    "post_code": 313000,
    "pid": 330500,
    "id": 981,
    "area_id": 330502
  },
  {
    "area_name": "南浔区",
    "pinyin": "Nanxun",
    "level": 3,
    "area_code": 572,
    "post_code": 313009,
    "pid": 330500,
    "id": 982,
    "area_id": 330503
  },
  {
    "area_name": "德清县",
    "pinyin": "Deqing",
    "level": 3,
    "area_code": 572,
    "post_code": 313200,
    "pid": 330500,
    "id": 983,
    "area_id": 330521
  },
  {
    "area_name": "长兴县",
    "pinyin": "Changxing",
    "level": 3,
    "area_code": 572,
    "post_code": 313100,
    "pid": 330500,
    "id": 984,
    "area_id": 330522
  },
  {
    "area_name": "安吉县",
    "pinyin": "Anji",
    "level": 3,
    "area_code": 572,
    "post_code": 313300,
    "pid": 330500,
    "id": 985,
    "area_id": 330523
  },
  {
    "area_name": "绍兴市",
    "pinyin": "Shaoxing",
    "level": 2,
    "area_code": 575,
    "post_code": 312000,
    "pid": 330000,
    "id": 986,
    "area_id": 330600
  },
  {
    "area_name": "越城区",
    "pinyin": "Yuecheng",
    "level": 3,
    "area_code": 575,
    "post_code": 312000,
    "pid": 330600,
    "id": 987,
    "area_id": 330602
  },
  {
    "area_name": "柯桥区",
    "pinyin": "Keqiao ",
    "level": 3,
    "area_code": 575,
    "post_code": 312030,
    "pid": 330600,
    "id": 988,
    "area_id": 330603
  },
  {
    "area_name": "上虞区",
    "pinyin": "Shangyu",
    "level": 3,
    "area_code": 575,
    "post_code": 312300,
    "pid": 330600,
    "id": 989,
    "area_id": 330604
  },
  {
    "area_name": "新昌县",
    "pinyin": "Xinchang",
    "level": 3,
    "area_code": 575,
    "post_code": 312500,
    "pid": 330600,
    "id": 990,
    "area_id": 330624
  },
  {
    "area_name": "诸暨市",
    "pinyin": "Zhuji",
    "level": 3,
    "area_code": 575,
    "post_code": 311800,
    "pid": 330600,
    "id": 991,
    "area_id": 330681
  },
  {
    "area_name": "嵊州市",
    "pinyin": "Shengzhou",
    "level": 3,
    "area_code": 575,
    "post_code": 312400,
    "pid": 330600,
    "id": 992,
    "area_id": 330683
  },
  {
    "area_name": "金华市",
    "pinyin": "Jinhua",
    "level": 2,
    "area_code": 579,
    "post_code": 321000,
    "pid": 330000,
    "id": 993,
    "area_id": 330700
  },
  {
    "area_name": "婺城区",
    "pinyin": "Wucheng",
    "level": 3,
    "area_code": 579,
    "post_code": 321000,
    "pid": 330700,
    "id": 994,
    "area_id": 330702
  },
  {
    "area_name": "金东区",
    "pinyin": "Jindong",
    "level": 3,
    "area_code": 579,
    "post_code": 321000,
    "pid": 330700,
    "id": 995,
    "area_id": 330703
  },
  {
    "area_name": "武义县",
    "pinyin": "Wuyi",
    "level": 3,
    "area_code": 579,
    "post_code": 321200,
    "pid": 330700,
    "id": 996,
    "area_id": 330723
  },
  {
    "area_name": "浦江县",
    "pinyin": "Pujiang",
    "level": 3,
    "area_code": 579,
    "post_code": 322200,
    "pid": 330700,
    "id": 997,
    "area_id": 330726
  },
  {
    "area_name": "磐安县",
    "pinyin": "Pan'an",
    "level": 3,
    "area_code": 579,
    "post_code": 322300,
    "pid": 330700,
    "id": 998,
    "area_id": 330727
  },
  {
    "area_name": "兰溪市",
    "pinyin": "Lanxi",
    "level": 3,
    "area_code": 579,
    "post_code": 321100,
    "pid": 330700,
    "id": 999,
    "area_id": 330781
  },
  {
    "area_name": "义乌市",
    "pinyin": "Yiwu",
    "level": 3,
    "area_code": 579,
    "post_code": 322000,
    "pid": 330700,
    "id": 1000,
    "area_id": 330782
  },
  {
    "area_name": "东阳市",
    "pinyin": "Dongyang",
    "level": 3,
    "area_code": 579,
    "post_code": 322100,
    "pid": 330700,
    "id": 1001,
    "area_id": 330783
  },
  {
    "area_name": "永康市",
    "pinyin": "Yongkang",
    "level": 3,
    "area_code": 579,
    "post_code": 321300,
    "pid": 330700,
    "id": 1002,
    "area_id": 330784
  },
  {
    "area_name": "衢州市",
    "pinyin": "Quzhou",
    "level": 2,
    "area_code": 570,
    "post_code": 324002,
    "pid": 330000,
    "id": 1003,
    "area_id": 330800
  },
  {
    "area_name": "柯城区",
    "pinyin": "Kecheng",
    "level": 3,
    "area_code": 570,
    "post_code": 324100,
    "pid": 330800,
    "id": 1004,
    "area_id": 330802
  },
  {
    "area_name": "衢江区",
    "pinyin": "Qujiang",
    "level": 3,
    "area_code": 570,
    "post_code": 324022,
    "pid": 330800,
    "id": 1005,
    "area_id": 330803
  },
  {
    "area_name": "常山县",
    "pinyin": "Changshan",
    "level": 3,
    "area_code": 570,
    "post_code": 324200,
    "pid": 330800,
    "id": 1006,
    "area_id": 330822
  },
  {
    "area_name": "开化县",
    "pinyin": "Kaihua",
    "level": 3,
    "area_code": 570,
    "post_code": 324300,
    "pid": 330800,
    "id": 1007,
    "area_id": 330824
  },
  {
    "area_name": "龙游县",
    "pinyin": "Longyou",
    "level": 3,
    "area_code": 570,
    "post_code": 324400,
    "pid": 330800,
    "id": 1008,
    "area_id": 330825
  },
  {
    "area_name": "江山市",
    "pinyin": "Jiangshan",
    "level": 3,
    "area_code": 570,
    "post_code": 324100,
    "pid": 330800,
    "id": 1009,
    "area_id": 330881
  },
  {
    "area_name": "舟山市",
    "pinyin": "Zhoushan",
    "level": 2,
    "area_code": 580,
    "post_code": 316000,
    "pid": 330000,
    "id": 1010,
    "area_id": 330900
  },
  {
    "area_name": "定海区",
    "pinyin": "Dinghai",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 330900,
    "id": 1011,
    "area_id": 330902
  },
  {
    "area_name": "普陀区",
    "pinyin": "Putuo",
    "level": 3,
    "area_code": 580,
    "post_code": 316100,
    "pid": 330900,
    "id": 1012,
    "area_id": 330903
  },
  {
    "area_name": "岱山县",
    "pinyin": "Daishan",
    "level": 3,
    "area_code": 580,
    "post_code": 316200,
    "pid": 330900,
    "id": 1013,
    "area_id": 330921
  },
  {
    "area_name": "嵊泗县",
    "pinyin": "Shengsi",
    "level": 3,
    "area_code": 580,
    "post_code": 202450,
    "pid": 330900,
    "id": 1014,
    "area_id": 330922
  },
  {
    "area_name": "台州市",
    "pinyin": "Taizhou",
    "level": 2,
    "area_code": 576,
    "post_code": 318000,
    "pid": 330000,
    "id": 1015,
    "area_id": 331000
  },
  {
    "area_name": "椒江区",
    "pinyin": "Jiaojiang",
    "level": 3,
    "area_code": 576,
    "post_code": 318000,
    "pid": 331000,
    "id": 1016,
    "area_id": 331002
  },
  {
    "area_name": "黄岩区",
    "pinyin": "Huangyan",
    "level": 3,
    "area_code": 576,
    "post_code": 318020,
    "pid": 331000,
    "id": 1017,
    "area_id": 331003
  },
  {
    "area_name": "路桥区",
    "pinyin": "Luqiao",
    "level": 3,
    "area_code": 576,
    "post_code": 318050,
    "pid": 331000,
    "id": 1018,
    "area_id": 331004
  },
  {
    "area_name": "玉环县",
    "pinyin": "Yuhuan",
    "level": 3,
    "area_code": 576,
    "post_code": 317600,
    "pid": 331000,
    "id": 1019,
    "area_id": 331021
  },
  {
    "area_name": "三门县",
    "pinyin": "Sanmen",
    "level": 3,
    "area_code": 576,
    "post_code": 317100,
    "pid": 331000,
    "id": 1020,
    "area_id": 331022
  },
  {
    "area_name": "天台县",
    "pinyin": "Tiantai",
    "level": 3,
    "area_code": 576,
    "post_code": 317200,
    "pid": 331000,
    "id": 1021,
    "area_id": 331023
  },
  {
    "area_name": "仙居县",
    "pinyin": "Xianju",
    "level": 3,
    "area_code": 576,
    "post_code": 317300,
    "pid": 331000,
    "id": 1022,
    "area_id": 331024
  },
  {
    "area_name": "温岭市",
    "pinyin": "Wenling",
    "level": 3,
    "area_code": 576,
    "post_code": 317500,
    "pid": 331000,
    "id": 1023,
    "area_id": 331081
  },
  {
    "area_name": "临海市",
    "pinyin": "Linhai",
    "level": 3,
    "area_code": 576,
    "post_code": 317000,
    "pid": 331000,
    "id": 1024,
    "area_id": 331082
  },
  {
    "area_name": "丽水市",
    "pinyin": "Lishui",
    "level": 2,
    "area_code": 578,
    "post_code": 323000,
    "pid": 330000,
    "id": 1025,
    "area_id": 331100
  },
  {
    "area_name": "莲都区",
    "pinyin": "Liandu",
    "level": 3,
    "area_code": 578,
    "post_code": 323000,
    "pid": 331100,
    "id": 1026,
    "area_id": 331102
  },
  {
    "area_name": "青田县",
    "pinyin": "Qingtian",
    "level": 3,
    "area_code": 578,
    "post_code": 323900,
    "pid": 331100,
    "id": 1027,
    "area_id": 331121
  },
  {
    "area_name": "缙云县",
    "pinyin": "Jinyun",
    "level": 3,
    "area_code": 578,
    "post_code": 321400,
    "pid": 331100,
    "id": 1028,
    "area_id": 331122
  },
  {
    "area_name": "遂昌县",
    "pinyin": "Suichang",
    "level": 3,
    "area_code": 578,
    "post_code": 323300,
    "pid": 331100,
    "id": 1029,
    "area_id": 331123
  },
  {
    "area_name": "松阳县",
    "pinyin": "Songyang",
    "level": 3,
    "area_code": 578,
    "post_code": 323400,
    "pid": 331100,
    "id": 1030,
    "area_id": 331124
  },
  {
    "area_name": "云和县",
    "pinyin": "Yunhe",
    "level": 3,
    "area_code": 578,
    "post_code": 323600,
    "pid": 331100,
    "id": 1031,
    "area_id": 331125
  },
  {
    "area_name": "庆元县",
    "pinyin": "Qingyuan",
    "level": 3,
    "area_code": 578,
    "post_code": 323800,
    "pid": 331100,
    "id": 1032,
    "area_id": 331126
  },
  {
    "area_name": "景宁畲族自治县",
    "pinyin": "Jingning",
    "level": 3,
    "area_code": 578,
    "post_code": 323500,
    "pid": 331100,
    "id": 1033,
    "area_id": 331127
  },
  {
    "area_name": "龙泉市",
    "pinyin": "Longquan",
    "level": 3,
    "area_code": 578,
    "post_code": 323700,
    "pid": 331100,
    "id": 1034,
    "area_id": 331181
  },
  {
    "area_name": "舟山群岛新区",
    "pinyin": "Zhoushan",
    "level": 2,
    "area_code": 580,
    "post_code": 316000,
    "pid": 330000,
    "id": 1035,
    "area_id": 331200
  },
  {
    "area_name": "金塘岛",
    "pinyin": "Jintang",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1036,
    "area_id": 331201
  },
  {
    "area_name": "六横岛",
    "pinyin": "Liuheng",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1037,
    "area_id": 331202
  },
  {
    "area_name": "衢山岛",
    "pinyin": "Qushan",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1038,
    "area_id": 331203
  },
  {
    "area_name": "舟山本岛西北部",
    "pinyin": "Zhoushan",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1039,
    "area_id": 331204
  },
  {
    "area_name": "岱山岛西南部",
    "pinyin": "Daishan",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1040,
    "area_id": 331205
  },
  {
    "area_name": "泗礁岛",
    "pinyin": "Sijiao",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1041,
    "area_id": 331206
  },
  {
    "area_name": "朱家尖岛",
    "pinyin": "Zhujiajian",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1042,
    "area_id": 331207
  },
  {
    "area_name": "洋山岛",
    "pinyin": "Yangshan",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1043,
    "area_id": 331208
  },
  {
    "area_name": "长涂岛",
    "pinyin": "Changtu",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1044,
    "area_id": 331209
  },
  {
    "area_name": "虾峙岛",
    "pinyin": "Xiazhi",
    "level": 3,
    "area_code": 580,
    "post_code": 316000,
    "pid": 331200,
    "id": 1045,
    "area_id": 331210
  },
  {
    "area_name": "安徽省",
    "pinyin": "Anhui",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1046,
    "area_id": 340000
  },
  {
    "area_name": "合肥市",
    "pinyin": "Hefei",
    "level": 2,
    "area_code": 551,
    "post_code": 230001,
    "pid": 340000,
    "id": 1047,
    "area_id": 340100
  },
  {
    "area_name": "瑶海区",
    "pinyin": "Yaohai",
    "level": 3,
    "area_code": 551,
    "post_code": 230011,
    "pid": 340100,
    "id": 1048,
    "area_id": 340102
  },
  {
    "area_name": "庐阳区",
    "pinyin": "Luyang",
    "level": 3,
    "area_code": 551,
    "post_code": 230001,
    "pid": 340100,
    "id": 1049,
    "area_id": 340103
  },
  {
    "area_name": "蜀山区",
    "pinyin": "Shushan",
    "level": 3,
    "area_code": 551,
    "post_code": 230031,
    "pid": 340100,
    "id": 1050,
    "area_id": 340104
  },
  {
    "area_name": "包河区",
    "pinyin": "Baohe",
    "level": 3,
    "area_code": 551,
    "post_code": 230041,
    "pid": 340100,
    "id": 1051,
    "area_id": 340111
  },
  {
    "area_name": "长丰县",
    "pinyin": "Changfeng",
    "level": 3,
    "area_code": 551,
    "post_code": 231100,
    "pid": 340100,
    "id": 1052,
    "area_id": 340121
  },
  {
    "area_name": "肥东县",
    "pinyin": "Feidong",
    "level": 3,
    "area_code": 551,
    "post_code": 231600,
    "pid": 340100,
    "id": 1053,
    "area_id": 340122
  },
  {
    "area_name": "肥西县",
    "pinyin": "Feixi",
    "level": 3,
    "area_code": 551,
    "post_code": 231200,
    "pid": 340100,
    "id": 1054,
    "area_id": 340123
  },
  {
    "area_name": "庐江县",
    "pinyin": "Lujiang",
    "level": 3,
    "area_code": 565,
    "post_code": 231500,
    "pid": 340100,
    "id": 1055,
    "area_id": 340124
  },
  {
    "area_name": "巢湖市",
    "pinyin": "Chaohu",
    "level": 3,
    "area_code": 565,
    "post_code": 238000,
    "pid": 340100,
    "id": 1056,
    "area_id": 340181
  },
  {
    "area_name": "芜湖市",
    "pinyin": "Wuhu",
    "level": 2,
    "area_code": 551,
    "post_code": 241000,
    "pid": 340000,
    "id": 1057,
    "area_id": 340200
  },
  {
    "area_name": "镜湖区",
    "pinyin": "Jinghu",
    "level": 3,
    "area_code": 553,
    "post_code": 241000,
    "pid": 340200,
    "id": 1058,
    "area_id": 340202
  },
  {
    "area_name": "弋江区",
    "pinyin": "Yijiang",
    "level": 3,
    "area_code": 553,
    "post_code": 241000,
    "pid": 340200,
    "id": 1059,
    "area_id": 340203
  },
  {
    "area_name": "鸠江区",
    "pinyin": "Jiujiang",
    "level": 3,
    "area_code": 553,
    "post_code": 241000,
    "pid": 340200,
    "id": 1060,
    "area_id": 340207
  },
  {
    "area_name": "三山区",
    "pinyin": "Sanshan",
    "level": 3,
    "area_code": 553,
    "post_code": 241000,
    "pid": 340200,
    "id": 1061,
    "area_id": 340208
  },
  {
    "area_name": "芜湖县",
    "pinyin": "Wuhu",
    "level": 3,
    "area_code": 553,
    "post_code": 241100,
    "pid": 340200,
    "id": 1062,
    "area_id": 340221
  },
  {
    "area_name": "繁昌县",
    "pinyin": "Fanchang",
    "level": 3,
    "area_code": 553,
    "post_code": 241200,
    "pid": 340200,
    "id": 1063,
    "area_id": 340222
  },
  {
    "area_name": "南陵县",
    "pinyin": "Nanling",
    "level": 3,
    "area_code": 553,
    "post_code": 242400,
    "pid": 340200,
    "id": 1064,
    "area_id": 340223
  },
  {
    "area_name": "无为县",
    "pinyin": "Wuwei",
    "level": 3,
    "area_code": 565,
    "post_code": 238300,
    "pid": 340200,
    "id": 1065,
    "area_id": 340225
  },
  {
    "area_name": "蚌埠市",
    "pinyin": "Bengbu",
    "level": 2,
    "area_code": 552,
    "post_code": 233000,
    "pid": 340000,
    "id": 1066,
    "area_id": 340300
  },
  {
    "area_name": "龙子湖区",
    "pinyin": "Longzihu",
    "level": 3,
    "area_code": 552,
    "post_code": 233000,
    "pid": 340300,
    "id": 1067,
    "area_id": 340302
  },
  {
    "area_name": "蚌山区",
    "pinyin": "Bengshan",
    "level": 3,
    "area_code": 552,
    "post_code": 233000,
    "pid": 340300,
    "id": 1068,
    "area_id": 340303
  },
  {
    "area_name": "禹会区",
    "pinyin": "Yuhui",
    "level": 3,
    "area_code": 552,
    "post_code": 233010,
    "pid": 340300,
    "id": 1069,
    "area_id": 340304
  },
  {
    "area_name": "淮上区",
    "pinyin": "Huaishang",
    "level": 3,
    "area_code": 552,
    "post_code": 233002,
    "pid": 340300,
    "id": 1070,
    "area_id": 340311
  },
  {
    "area_name": "怀远县",
    "pinyin": "Huaiyuan",
    "level": 3,
    "area_code": 552,
    "post_code": 233400,
    "pid": 340300,
    "id": 1071,
    "area_id": 340321
  },
  {
    "area_name": "五河县",
    "pinyin": "Wuhe",
    "level": 3,
    "area_code": 552,
    "post_code": 233300,
    "pid": 340300,
    "id": 1072,
    "area_id": 340322
  },
  {
    "area_name": "固镇县",
    "pinyin": "Guzhen",
    "level": 3,
    "area_code": 552,
    "post_code": 233700,
    "pid": 340300,
    "id": 1073,
    "area_id": 340323
  },
  {
    "area_name": "淮南市",
    "pinyin": "Huainan",
    "level": 2,
    "area_code": 554,
    "post_code": 232001,
    "pid": 340000,
    "id": 1074,
    "area_id": 340400
  },
  {
    "area_name": "大通区",
    "pinyin": "Datong",
    "level": 3,
    "area_code": 554,
    "post_code": 232033,
    "pid": 340400,
    "id": 1075,
    "area_id": 340402
  },
  {
    "area_name": "田家庵区",
    "pinyin": "Tianjiaan",
    "level": 3,
    "area_code": 554,
    "post_code": 232000,
    "pid": 340400,
    "id": 1076,
    "area_id": 340403
  },
  {
    "area_name": "谢家集区",
    "pinyin": "Xiejiaji",
    "level": 3,
    "area_code": 554,
    "post_code": 232052,
    "pid": 340400,
    "id": 1077,
    "area_id": 340404
  },
  {
    "area_name": "八公山区",
    "pinyin": "Bagongshan",
    "level": 3,
    "area_code": 554,
    "post_code": 232072,
    "pid": 340400,
    "id": 1078,
    "area_id": 340405
  },
  {
    "area_name": "潘集区",
    "pinyin": "Panji",
    "level": 3,
    "area_code": 554,
    "post_code": 232082,
    "pid": 340400,
    "id": 1079,
    "area_id": 340406
  },
  {
    "area_name": "凤台县",
    "pinyin": "Fengtai",
    "level": 3,
    "area_code": 554,
    "post_code": 232100,
    "pid": 340400,
    "id": 1080,
    "area_id": 340421
  },
  {
    "area_name": "马鞍山市",
    "pinyin": "Ma'anshan",
    "level": 2,
    "area_code": 555,
    "post_code": 243001,
    "pid": 340000,
    "id": 1081,
    "area_id": 340500
  },
  {
    "area_name": "花山区",
    "pinyin": "Huashan",
    "level": 3,
    "area_code": 555,
    "post_code": 243000,
    "pid": 340500,
    "id": 1082,
    "area_id": 340503
  },
  {
    "area_name": "雨山区",
    "pinyin": "Yushan",
    "level": 3,
    "area_code": 555,
    "post_code": 243071,
    "pid": 340500,
    "id": 1083,
    "area_id": 340504
  },
  {
    "area_name": "博望区",
    "pinyin": "Bowang",
    "level": 3,
    "area_code": 555,
    "post_code": 243131,
    "pid": 340500,
    "id": 1084,
    "area_id": 340506
  },
  {
    "area_name": "当涂县",
    "pinyin": "Dangtu",
    "level": 3,
    "area_code": 555,
    "post_code": 243100,
    "pid": 340500,
    "id": 1085,
    "area_id": 340521
  },
  {
    "area_name": "含山县",
    "pinyin": "Hanshan ",
    "level": 3,
    "area_code": 555,
    "post_code": 238100,
    "pid": 340500,
    "id": 1086,
    "area_id": 340522
  },
  {
    "area_name": "和县",
    "pinyin": "Hexian",
    "level": 3,
    "area_code": 555,
    "post_code": 238200,
    "pid": 340500,
    "id": 1087,
    "area_id": 340523
  },
  {
    "area_name": "淮北市",
    "pinyin": "Huaibei",
    "level": 2,
    "area_code": 561,
    "post_code": 235000,
    "pid": 340000,
    "id": 1088,
    "area_id": 340600
  },
  {
    "area_name": "杜集区",
    "pinyin": "Duji",
    "level": 3,
    "area_code": 561,
    "post_code": 235000,
    "pid": 340600,
    "id": 1089,
    "area_id": 340602
  },
  {
    "area_name": "相山区",
    "pinyin": "Xiangshan",
    "level": 3,
    "area_code": 561,
    "post_code": 235000,
    "pid": 340600,
    "id": 1090,
    "area_id": 340603
  },
  {
    "area_name": "烈山区",
    "pinyin": "Lieshan",
    "level": 3,
    "area_code": 561,
    "post_code": 235000,
    "pid": 340600,
    "id": 1091,
    "area_id": 340604
  },
  {
    "area_name": "濉溪县",
    "pinyin": "Suixi",
    "level": 3,
    "area_code": 561,
    "post_code": 235100,
    "pid": 340600,
    "id": 1092,
    "area_id": 340621
  },
  {
    "area_name": "铜陵市",
    "pinyin": "Tongling",
    "level": 2,
    "area_code": 562,
    "post_code": 244000,
    "pid": 340000,
    "id": 1093,
    "area_id": 340700
  },
  {
    "area_name": "铜官山区",
    "pinyin": "Tongguanshan",
    "level": 3,
    "area_code": 562,
    "post_code": 244000,
    "pid": 340700,
    "id": 1094,
    "area_id": 340702
  },
  {
    "area_name": "狮子山区",
    "pinyin": "Shizishan",
    "level": 3,
    "area_code": 562,
    "post_code": 244000,
    "pid": 340700,
    "id": 1095,
    "area_id": 340703
  },
  {
    "area_name": "郊区",
    "pinyin": "Jiaoqu",
    "level": 3,
    "area_code": 562,
    "post_code": 244000,
    "pid": 340700,
    "id": 1096,
    "area_id": 340711
  },
  {
    "area_name": "铜陵县",
    "pinyin": "Tongling",
    "level": 3,
    "area_code": 562,
    "post_code": 244100,
    "pid": 340700,
    "id": 1097,
    "area_id": 340721
  },
  {
    "area_name": "安庆市",
    "pinyin": "Anqing",
    "level": 2,
    "area_code": 556,
    "post_code": 246001,
    "pid": 340000,
    "id": 1098,
    "area_id": 340800
  },
  {
    "area_name": "迎江区",
    "pinyin": "Yingjiang",
    "level": 3,
    "area_code": 556,
    "post_code": 246001,
    "pid": 340800,
    "id": 1099,
    "area_id": 340802
  },
  {
    "area_name": "大观区",
    "pinyin": "Daguan",
    "level": 3,
    "area_code": 556,
    "post_code": 246002,
    "pid": 340800,
    "id": 1100,
    "area_id": 340803
  },
  {
    "area_name": "宜秀区",
    "pinyin": "Yixiu",
    "level": 3,
    "area_code": 556,
    "post_code": 246003,
    "pid": 340800,
    "id": 1101,
    "area_id": 340811
  },
  {
    "area_name": "怀宁县",
    "pinyin": "Huaining",
    "level": 3,
    "area_code": 556,
    "post_code": 246100,
    "pid": 340800,
    "id": 1102,
    "area_id": 340822
  },
  {
    "area_name": "枞阳县",
    "pinyin": "Zongyang",
    "level": 3,
    "area_code": 556,
    "post_code": 246700,
    "pid": 340800,
    "id": 1103,
    "area_id": 340823
  },
  {
    "area_name": "潜山县",
    "pinyin": "Qianshan",
    "level": 3,
    "area_code": 556,
    "post_code": 246300,
    "pid": 340800,
    "id": 1104,
    "area_id": 340824
  },
  {
    "area_name": "太湖县",
    "pinyin": "Taihu",
    "level": 3,
    "area_code": 556,
    "post_code": 246400,
    "pid": 340800,
    "id": 1105,
    "area_id": 340825
  },
  {
    "area_name": "宿松县",
    "pinyin": "Susong",
    "level": 3,
    "area_code": 556,
    "post_code": 246500,
    "pid": 340800,
    "id": 1106,
    "area_id": 340826
  },
  {
    "area_name": "望江县",
    "pinyin": "Wangjiang",
    "level": 3,
    "area_code": 556,
    "post_code": 246200,
    "pid": 340800,
    "id": 1107,
    "area_id": 340827
  },
  {
    "area_name": "岳西县",
    "pinyin": "Yuexi",
    "level": 3,
    "area_code": 556,
    "post_code": 246600,
    "pid": 340800,
    "id": 1108,
    "area_id": 340828
  },
  {
    "area_name": "桐城市",
    "pinyin": "Tongcheng",
    "level": 3,
    "area_code": 556,
    "post_code": 231400,
    "pid": 340800,
    "id": 1109,
    "area_id": 340881
  },
  {
    "area_name": "黄山市",
    "pinyin": "Huangshan",
    "level": 2,
    "area_code": 559,
    "post_code": 245000,
    "pid": 340000,
    "id": 1110,
    "area_id": 341000
  },
  {
    "area_name": "屯溪区",
    "pinyin": "Tunxi",
    "level": 3,
    "area_code": 559,
    "post_code": 245000,
    "pid": 341000,
    "id": 1111,
    "area_id": 341002
  },
  {
    "area_name": "黄山区",
    "pinyin": "Huangshan",
    "level": 3,
    "area_code": 559,
    "post_code": 242700,
    "pid": 341000,
    "id": 1112,
    "area_id": 341003
  },
  {
    "area_name": "徽州区",
    "pinyin": "Huizhou",
    "level": 3,
    "area_code": 559,
    "post_code": 245061,
    "pid": 341000,
    "id": 1113,
    "area_id": 341004
  },
  {
    "area_name": "歙县",
    "pinyin": "Shexian",
    "level": 3,
    "area_code": 559,
    "post_code": 245200,
    "pid": 341000,
    "id": 1114,
    "area_id": 341021
  },
  {
    "area_name": "休宁县",
    "pinyin": "Xiuning",
    "level": 3,
    "area_code": 559,
    "post_code": 245400,
    "pid": 341000,
    "id": 1115,
    "area_id": 341022
  },
  {
    "area_name": "黟县",
    "pinyin": "Yixian",
    "level": 3,
    "area_code": 559,
    "post_code": 245500,
    "pid": 341000,
    "id": 1116,
    "area_id": 341023
  },
  {
    "area_name": "祁门县",
    "pinyin": "Qimen",
    "level": 3,
    "area_code": 559,
    "post_code": 245600,
    "pid": 341000,
    "id": 1117,
    "area_id": 341024
  },
  {
    "area_name": "滁州市",
    "pinyin": "Chuzhou",
    "level": 2,
    "area_code": 550,
    "post_code": 239000,
    "pid": 340000,
    "id": 1118,
    "area_id": 341100
  },
  {
    "area_name": "琅琊区",
    "pinyin": "Langya",
    "level": 3,
    "area_code": 550,
    "post_code": 239000,
    "pid": 341100,
    "id": 1119,
    "area_id": 341102
  },
  {
    "area_name": "南谯区",
    "pinyin": "Nanqiao",
    "level": 3,
    "area_code": 550,
    "post_code": 239000,
    "pid": 341100,
    "id": 1120,
    "area_id": 341103
  },
  {
    "area_name": "来安县",
    "pinyin": "Lai'an",
    "level": 3,
    "area_code": 550,
    "post_code": 239200,
    "pid": 341100,
    "id": 1121,
    "area_id": 341122
  },
  {
    "area_name": "全椒县",
    "pinyin": "Quanjiao",
    "level": 3,
    "area_code": 550,
    "post_code": 239500,
    "pid": 341100,
    "id": 1122,
    "area_id": 341124
  },
  {
    "area_name": "定远县",
    "pinyin": "Dingyuan",
    "level": 3,
    "area_code": 550,
    "post_code": 233200,
    "pid": 341100,
    "id": 1123,
    "area_id": 341125
  },
  {
    "area_name": "凤阳县",
    "pinyin": "Fengyang",
    "level": 3,
    "area_code": 550,
    "post_code": 233100,
    "pid": 341100,
    "id": 1124,
    "area_id": 341126
  },
  {
    "area_name": "天长市",
    "pinyin": "Tianchang",
    "level": 3,
    "area_code": 550,
    "post_code": 239300,
    "pid": 341100,
    "id": 1125,
    "area_id": 341181
  },
  {
    "area_name": "明光市",
    "pinyin": "Mingguang",
    "level": 3,
    "area_code": 550,
    "post_code": 239400,
    "pid": 341100,
    "id": 1126,
    "area_id": 341182
  },
  {
    "area_name": "阜阳市",
    "pinyin": "Fuyang",
    "level": 2,
    "area_code": 558,
    "post_code": 236033,
    "pid": 340000,
    "id": 1127,
    "area_id": 341200
  },
  {
    "area_name": "颍州区",
    "pinyin": "Yingzhou",
    "level": 3,
    "area_code": 558,
    "post_code": 236001,
    "pid": 341200,
    "id": 1128,
    "area_id": 341202
  },
  {
    "area_name": "颍东区",
    "pinyin": "Yingdong",
    "level": 3,
    "area_code": 558,
    "post_code": 236058,
    "pid": 341200,
    "id": 1129,
    "area_id": 341203
  },
  {
    "area_name": "颍泉区",
    "pinyin": "Yingquan",
    "level": 3,
    "area_code": 558,
    "post_code": 236045,
    "pid": 341200,
    "id": 1130,
    "area_id": 341204
  },
  {
    "area_name": "临泉县",
    "pinyin": "Linquan",
    "level": 3,
    "area_code": 558,
    "post_code": 236400,
    "pid": 341200,
    "id": 1131,
    "area_id": 341221
  },
  {
    "area_name": "太和县",
    "pinyin": "Taihe",
    "level": 3,
    "area_code": 558,
    "post_code": 236600,
    "pid": 341200,
    "id": 1132,
    "area_id": 341222
  },
  {
    "area_name": "阜南县",
    "pinyin": "Funan",
    "level": 3,
    "area_code": 558,
    "post_code": 236300,
    "pid": 341200,
    "id": 1133,
    "area_id": 341225
  },
  {
    "area_name": "颍上县",
    "pinyin": "Yingshang",
    "level": 3,
    "area_code": 558,
    "post_code": 236200,
    "pid": 341200,
    "id": 1134,
    "area_id": 341226
  },
  {
    "area_name": "界首市",
    "pinyin": "Jieshou",
    "level": 3,
    "area_code": 558,
    "post_code": 236500,
    "pid": 341200,
    "id": 1135,
    "area_id": 341282
  },
  {
    "area_name": "宿州市",
    "pinyin": "Suzhou",
    "level": 2,
    "area_code": 557,
    "post_code": 234000,
    "pid": 340000,
    "id": 1136,
    "area_id": 341300
  },
  {
    "area_name": "埇桥区",
    "pinyin": "Yongqiao",
    "level": 3,
    "area_code": 557,
    "post_code": 234000,
    "pid": 341300,
    "id": 1137,
    "area_id": 341302
  },
  {
    "area_name": "砀山县",
    "pinyin": "Dangshan",
    "level": 3,
    "area_code": 557,
    "post_code": 235300,
    "pid": 341300,
    "id": 1138,
    "area_id": 341321
  },
  {
    "area_name": "萧县",
    "pinyin": "Xiaoxian",
    "level": 3,
    "area_code": 557,
    "post_code": 235200,
    "pid": 341300,
    "id": 1139,
    "area_id": 341322
  },
  {
    "area_name": "灵璧县",
    "pinyin": "Lingbi",
    "level": 3,
    "area_code": 557,
    "post_code": 234200,
    "pid": 341300,
    "id": 1140,
    "area_id": 341323
  },
  {
    "area_name": "泗县",
    "pinyin": "Sixian",
    "level": 3,
    "area_code": 557,
    "post_code": 234300,
    "pid": 341300,
    "id": 1141,
    "area_id": 341324
  },
  {
    "area_name": "六安市",
    "pinyin": "Lu'an",
    "level": 2,
    "area_code": 564,
    "post_code": 237000,
    "pid": 340000,
    "id": 1142,
    "area_id": 341500
  },
  {
    "area_name": "金安区",
    "pinyin": "Jin'an",
    "level": 3,
    "area_code": 564,
    "post_code": 237005,
    "pid": 341500,
    "id": 1143,
    "area_id": 341502
  },
  {
    "area_name": "裕安区",
    "pinyin": "Yu'an",
    "level": 3,
    "area_code": 564,
    "post_code": 237010,
    "pid": 341500,
    "id": 1144,
    "area_id": 341503
  },
  {
    "area_name": "寿县",
    "pinyin": "Shouxian",
    "level": 3,
    "area_code": 564,
    "post_code": 232200,
    "pid": 341500,
    "id": 1145,
    "area_id": 341521
  },
  {
    "area_name": "霍邱县",
    "pinyin": "Huoqiu",
    "level": 3,
    "area_code": 564,
    "post_code": 237400,
    "pid": 341500,
    "id": 1146,
    "area_id": 341522
  },
  {
    "area_name": "舒城县",
    "pinyin": "Shucheng",
    "level": 3,
    "area_code": 564,
    "post_code": 231300,
    "pid": 341500,
    "id": 1147,
    "area_id": 341523
  },
  {
    "area_name": "金寨县",
    "pinyin": "Jinzhai",
    "level": 3,
    "area_code": 564,
    "post_code": 237300,
    "pid": 341500,
    "id": 1148,
    "area_id": 341524
  },
  {
    "area_name": "霍山县",
    "pinyin": "Huoshan",
    "level": 3,
    "area_code": 564,
    "post_code": 237200,
    "pid": 341500,
    "id": 1149,
    "area_id": 341525
  },
  {
    "area_name": "亳州市",
    "pinyin": "Bozhou",
    "level": 2,
    "area_code": 558,
    "post_code": 236802,
    "pid": 340000,
    "id": 1150,
    "area_id": 341600
  },
  {
    "area_name": "谯城区",
    "pinyin": "Qiaocheng",
    "level": 3,
    "area_code": 558,
    "post_code": 236800,
    "pid": 341600,
    "id": 1151,
    "area_id": 341602
  },
  {
    "area_name": "涡阳县",
    "pinyin": "Guoyang",
    "level": 3,
    "area_code": 558,
    "post_code": 233600,
    "pid": 341600,
    "id": 1152,
    "area_id": 341621
  },
  {
    "area_name": "蒙城县",
    "pinyin": "Mengcheng",
    "level": 3,
    "area_code": 558,
    "post_code": 233500,
    "pid": 341600,
    "id": 1153,
    "area_id": 341622
  },
  {
    "area_name": "利辛县",
    "pinyin": "Lixin",
    "level": 3,
    "area_code": 558,
    "post_code": 236700,
    "pid": 341600,
    "id": 1154,
    "area_id": 341623
  },
  {
    "area_name": "池州市",
    "pinyin": "Chizhou",
    "level": 2,
    "area_code": 566,
    "post_code": 247100,
    "pid": 340000,
    "id": 1155,
    "area_id": 341700
  },
  {
    "area_name": "贵池区",
    "pinyin": "Guichi",
    "level": 3,
    "area_code": 566,
    "post_code": 247100,
    "pid": 341700,
    "id": 1156,
    "area_id": 341702
  },
  {
    "area_name": "东至县",
    "pinyin": "Dongzhi",
    "level": 3,
    "area_code": 566,
    "post_code": 247200,
    "pid": 341700,
    "id": 1157,
    "area_id": 341721
  },
  {
    "area_name": "石台县",
    "pinyin": "Shitai",
    "level": 3,
    "area_code": 566,
    "post_code": 245100,
    "pid": 341700,
    "id": 1158,
    "area_id": 341722
  },
  {
    "area_name": "青阳县",
    "pinyin": "Qingyang",
    "level": 3,
    "area_code": 566,
    "post_code": 242800,
    "pid": 341700,
    "id": 1159,
    "area_id": 341723
  },
  {
    "area_name": "宣城市",
    "pinyin": "Xuancheng",
    "level": 2,
    "area_code": 563,
    "post_code": 242000,
    "pid": 340000,
    "id": 1160,
    "area_id": 341800
  },
  {
    "area_name": "宣州区",
    "pinyin": "Xuanzhou",
    "level": 3,
    "area_code": 563,
    "post_code": 242000,
    "pid": 341800,
    "id": 1161,
    "area_id": 341802
  },
  {
    "area_name": "郎溪县",
    "pinyin": "Langxi",
    "level": 3,
    "area_code": 563,
    "post_code": 242100,
    "pid": 341800,
    "id": 1162,
    "area_id": 341821
  },
  {
    "area_name": "广德县",
    "pinyin": "Guangde",
    "level": 3,
    "area_code": 563,
    "post_code": 242200,
    "pid": 341800,
    "id": 1163,
    "area_id": 341822
  },
  {
    "area_name": "泾县",
    "pinyin": "Jingxian",
    "level": 3,
    "area_code": 563,
    "post_code": 242500,
    "pid": 341800,
    "id": 1164,
    "area_id": 341823
  },
  {
    "area_name": "绩溪县",
    "pinyin": "Jixi",
    "level": 3,
    "area_code": 563,
    "post_code": 245300,
    "pid": 341800,
    "id": 1165,
    "area_id": 341824
  },
  {
    "area_name": "旌德县",
    "pinyin": "Jingde",
    "level": 3,
    "area_code": 563,
    "post_code": 242600,
    "pid": 341800,
    "id": 1166,
    "area_id": 341825
  },
  {
    "area_name": "宁国市",
    "pinyin": "Ningguo",
    "level": 3,
    "area_code": 563,
    "post_code": 242300,
    "pid": 341800,
    "id": 1167,
    "area_id": 341881
  },
  {
    "area_name": "福建省",
    "pinyin": "Fujian",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1168,
    "area_id": 350000
  },
  {
    "area_name": "福州市",
    "pinyin": "Fuzhou",
    "level": 2,
    "area_code": 591,
    "post_code": 350001,
    "pid": 350000,
    "id": 1169,
    "area_id": 350100
  },
  {
    "area_name": "鼓楼区",
    "pinyin": "Gulou",
    "level": 3,
    "area_code": 591,
    "post_code": 350001,
    "pid": 350100,
    "id": 1170,
    "area_id": 350102
  },
  {
    "area_name": "台江区",
    "pinyin": "Taijiang",
    "level": 3,
    "area_code": 591,
    "post_code": 350004,
    "pid": 350100,
    "id": 1171,
    "area_id": 350103
  },
  {
    "area_name": "仓山区",
    "pinyin": "Cangshan",
    "level": 3,
    "area_code": 591,
    "post_code": 350007,
    "pid": 350100,
    "id": 1172,
    "area_id": 350104
  },
  {
    "area_name": "马尾区",
    "pinyin": "Mawei",
    "level": 3,
    "area_code": 591,
    "post_code": 350015,
    "pid": 350100,
    "id": 1173,
    "area_id": 350105
  },
  {
    "area_name": "晋安区",
    "pinyin": "Jin'an",
    "level": 3,
    "area_code": 591,
    "post_code": 350011,
    "pid": 350100,
    "id": 1174,
    "area_id": 350111
  },
  {
    "area_name": "闽侯县",
    "pinyin": "Minhou",
    "level": 3,
    "area_code": 591,
    "post_code": 350100,
    "pid": 350100,
    "id": 1175,
    "area_id": 350121
  },
  {
    "area_name": "连江县",
    "pinyin": "Lianjiang",
    "level": 3,
    "area_code": 591,
    "post_code": 350500,
    "pid": 350100,
    "id": 1176,
    "area_id": 350122
  },
  {
    "area_name": "罗源县",
    "pinyin": "Luoyuan",
    "level": 3,
    "area_code": 591,
    "post_code": 350600,
    "pid": 350100,
    "id": 1177,
    "area_id": 350123
  },
  {
    "area_name": "闽清县",
    "pinyin": "Minqing",
    "level": 3,
    "area_code": 591,
    "post_code": 350800,
    "pid": 350100,
    "id": 1178,
    "area_id": 350124
  },
  {
    "area_name": "永泰县",
    "pinyin": "Yongtai",
    "level": 3,
    "area_code": 591,
    "post_code": 350700,
    "pid": 350100,
    "id": 1179,
    "area_id": 350125
  },
  {
    "area_name": "平潭县",
    "pinyin": "Pingtan",
    "level": 3,
    "area_code": 591,
    "post_code": 350400,
    "pid": 350100,
    "id": 1180,
    "area_id": 350128
  },
  {
    "area_name": "福清市",
    "pinyin": "Fuqing",
    "level": 3,
    "area_code": 591,
    "post_code": 350300,
    "pid": 350100,
    "id": 1181,
    "area_id": 350181
  },
  {
    "area_name": "长乐市",
    "pinyin": "Changle",
    "level": 3,
    "area_code": 591,
    "post_code": 350200,
    "pid": 350100,
    "id": 1182,
    "area_id": 350182
  },
  {
    "area_name": "厦门市",
    "pinyin": "Xiamen",
    "level": 2,
    "area_code": 592,
    "post_code": 361003,
    "pid": 350000,
    "id": 1183,
    "area_id": 350200
  },
  {
    "area_name": "思明区",
    "pinyin": "Siming",
    "level": 3,
    "area_code": 592,
    "post_code": 361001,
    "pid": 350200,
    "id": 1184,
    "area_id": 350203
  },
  {
    "area_name": "海沧区",
    "pinyin": "Haicang",
    "level": 3,
    "area_code": 592,
    "post_code": 361026,
    "pid": 350200,
    "id": 1185,
    "area_id": 350205
  },
  {
    "area_name": "湖里区",
    "pinyin": "Huli",
    "level": 3,
    "area_code": 592,
    "post_code": 361006,
    "pid": 350200,
    "id": 1186,
    "area_id": 350206
  },
  {
    "area_name": "集美区",
    "pinyin": "Jimei",
    "level": 3,
    "area_code": 592,
    "post_code": 361021,
    "pid": 350200,
    "id": 1187,
    "area_id": 350211
  },
  {
    "area_name": "同安区",
    "pinyin": "Tong'an",
    "level": 3,
    "area_code": 592,
    "post_code": 361100,
    "pid": 350200,
    "id": 1188,
    "area_id": 350212
  },
  {
    "area_name": "翔安区",
    "pinyin": "Xiang'an",
    "level": 3,
    "area_code": 592,
    "post_code": 361101,
    "pid": 350200,
    "id": 1189,
    "area_id": 350213
  },
  {
    "area_name": "莆田市",
    "pinyin": "Putian",
    "level": 2,
    "area_code": 594,
    "post_code": 351100,
    "pid": 350000,
    "id": 1190,
    "area_id": 350300
  },
  {
    "area_name": "城厢区",
    "pinyin": "Chengxiang",
    "level": 3,
    "area_code": 594,
    "post_code": 351100,
    "pid": 350300,
    "id": 1191,
    "area_id": 350302
  },
  {
    "area_name": "涵江区",
    "pinyin": "Hanjiang",
    "level": 3,
    "area_code": 594,
    "post_code": 351111,
    "pid": 350300,
    "id": 1192,
    "area_id": 350303
  },
  {
    "area_name": "荔城区",
    "pinyin": "Licheng",
    "level": 3,
    "area_code": 594,
    "post_code": 351100,
    "pid": 350300,
    "id": 1193,
    "area_id": 350304
  },
  {
    "area_name": "秀屿区",
    "pinyin": "Xiuyu",
    "level": 3,
    "area_code": 594,
    "post_code": 351152,
    "pid": 350300,
    "id": 1194,
    "area_id": 350305
  },
  {
    "area_name": "仙游县",
    "pinyin": "Xianyou",
    "level": 3,
    "area_code": 594,
    "post_code": 351200,
    "pid": 350300,
    "id": 1195,
    "area_id": 350322
  },
  {
    "area_name": "三明市",
    "pinyin": "Sanming",
    "level": 2,
    "area_code": 598,
    "post_code": 365000,
    "pid": 350000,
    "id": 1196,
    "area_id": 350400
  },
  {
    "area_name": "梅列区",
    "pinyin": "Meilie",
    "level": 3,
    "area_code": 598,
    "post_code": 365000,
    "pid": 350400,
    "id": 1197,
    "area_id": 350402
  },
  {
    "area_name": "三元区",
    "pinyin": "Sanyuan",
    "level": 3,
    "area_code": 598,
    "post_code": 365001,
    "pid": 350400,
    "id": 1198,
    "area_id": 350403
  },
  {
    "area_name": "明溪县",
    "pinyin": "Mingxi",
    "level": 3,
    "area_code": 598,
    "post_code": 365200,
    "pid": 350400,
    "id": 1199,
    "area_id": 350421
  },
  {
    "area_name": "清流县",
    "pinyin": "Qingliu",
    "level": 3,
    "area_code": 598,
    "post_code": 365300,
    "pid": 350400,
    "id": 1200,
    "area_id": 350423
  },
  {
    "area_name": "宁化县",
    "pinyin": "Ninghua",
    "level": 3,
    "area_code": 598,
    "post_code": 365400,
    "pid": 350400,
    "id": 1201,
    "area_id": 350424
  },
  {
    "area_name": "大田县",
    "pinyin": "Datian",
    "level": 3,
    "area_code": 598,
    "post_code": 366100,
    "pid": 350400,
    "id": 1202,
    "area_id": 350425
  },
  {
    "area_name": "尤溪县",
    "pinyin": "Youxi",
    "level": 3,
    "area_code": 598,
    "post_code": 365100,
    "pid": 350400,
    "id": 1203,
    "area_id": 350426
  },
  {
    "area_name": "沙县",
    "pinyin": "Shaxian",
    "level": 3,
    "area_code": 598,
    "post_code": 365500,
    "pid": 350400,
    "id": 1204,
    "area_id": 350427
  },
  {
    "area_name": "将乐县",
    "pinyin": "Jiangle",
    "level": 3,
    "area_code": 598,
    "post_code": 353300,
    "pid": 350400,
    "id": 1205,
    "area_id": 350428
  },
  {
    "area_name": "泰宁县",
    "pinyin": "Taining",
    "level": 3,
    "area_code": 598,
    "post_code": 354400,
    "pid": 350400,
    "id": 1206,
    "area_id": 350429
  },
  {
    "area_name": "建宁县",
    "pinyin": "Jianning",
    "level": 3,
    "area_code": 598,
    "post_code": 354500,
    "pid": 350400,
    "id": 1207,
    "area_id": 350430
  },
  {
    "area_name": "永安市",
    "pinyin": "Yong'an",
    "level": 3,
    "area_code": 598,
    "post_code": 366000,
    "pid": 350400,
    "id": 1208,
    "area_id": 350481
  },
  {
    "area_name": "泉州市",
    "pinyin": "Quanzhou",
    "level": 2,
    "area_code": 595,
    "post_code": 362000,
    "pid": 350000,
    "id": 1209,
    "area_id": 350500
  },
  {
    "area_name": "鲤城区",
    "pinyin": "Licheng",
    "level": 3,
    "area_code": 595,
    "post_code": 362000,
    "pid": 350500,
    "id": 1210,
    "area_id": 350502
  },
  {
    "area_name": "丰泽区",
    "pinyin": "Fengze",
    "level": 3,
    "area_code": 595,
    "post_code": 362000,
    "pid": 350500,
    "id": 1211,
    "area_id": 350503
  },
  {
    "area_name": "洛江区",
    "pinyin": "Luojiang",
    "level": 3,
    "area_code": 595,
    "post_code": 362011,
    "pid": 350500,
    "id": 1212,
    "area_id": 350504
  },
  {
    "area_name": "泉港区",
    "pinyin": "Quangang",
    "level": 3,
    "area_code": 595,
    "post_code": 362114,
    "pid": 350500,
    "id": 1213,
    "area_id": 350505
  },
  {
    "area_name": "惠安县",
    "pinyin": "Hui'an",
    "level": 3,
    "area_code": 595,
    "post_code": 362100,
    "pid": 350500,
    "id": 1214,
    "area_id": 350521
  },
  {
    "area_name": "安溪县",
    "pinyin": "Anxi",
    "level": 3,
    "area_code": 595,
    "post_code": 362400,
    "pid": 350500,
    "id": 1215,
    "area_id": 350524
  },
  {
    "area_name": "永春县",
    "pinyin": "Yongchun",
    "level": 3,
    "area_code": 595,
    "post_code": 362600,
    "pid": 350500,
    "id": 1216,
    "area_id": 350525
  },
  {
    "area_name": "德化县",
    "pinyin": "Dehua",
    "level": 3,
    "area_code": 595,
    "post_code": 362500,
    "pid": 350500,
    "id": 1217,
    "area_id": 350526
  },
  {
    "area_name": "金门县",
    "pinyin": "Jinmen",
    "level": 3,
    "area_code": null,
    "post_code": null,
    "pid": 350500,
    "id": 1218,
    "area_id": 350527
  },
  {
    "area_name": "石狮市",
    "pinyin": "Shishi",
    "level": 3,
    "area_code": 595,
    "post_code": 362700,
    "pid": 350500,
    "id": 1219,
    "area_id": 350581
  },
  {
    "area_name": "晋江市",
    "pinyin": "Jinjiang",
    "level": 3,
    "area_code": 595,
    "post_code": 362200,
    "pid": 350500,
    "id": 1220,
    "area_id": 350582
  },
  {
    "area_name": "南安市",
    "pinyin": "Nan'an",
    "level": 3,
    "area_code": 595,
    "post_code": 362300,
    "pid": 350500,
    "id": 1221,
    "area_id": 350583
  },
  {
    "area_name": "漳州市",
    "pinyin": "Zhangzhou",
    "level": 2,
    "area_code": 596,
    "post_code": 363005,
    "pid": 350000,
    "id": 1222,
    "area_id": 350600
  },
  {
    "area_name": "芗城区",
    "pinyin": "Xiangcheng",
    "level": 3,
    "area_code": 596,
    "post_code": 363000,
    "pid": 350600,
    "id": 1223,
    "area_id": 350602
  },
  {
    "area_name": "龙文区",
    "pinyin": "Longwen",
    "level": 3,
    "area_code": 596,
    "post_code": 363005,
    "pid": 350600,
    "id": 1224,
    "area_id": 350603
  },
  {
    "area_name": "云霄县",
    "pinyin": "Yunxiao",
    "level": 3,
    "area_code": 596,
    "post_code": 363300,
    "pid": 350600,
    "id": 1225,
    "area_id": 350622
  },
  {
    "area_name": "漳浦县",
    "pinyin": "Zhangpu",
    "level": 3,
    "area_code": 596,
    "post_code": 363200,
    "pid": 350600,
    "id": 1226,
    "area_id": 350623
  },
  {
    "area_name": "诏安县",
    "pinyin": "Zhao'an",
    "level": 3,
    "area_code": 596,
    "post_code": 363500,
    "pid": 350600,
    "id": 1227,
    "area_id": 350624
  },
  {
    "area_name": "长泰县",
    "pinyin": "Changtai",
    "level": 3,
    "area_code": 596,
    "post_code": 363900,
    "pid": 350600,
    "id": 1228,
    "area_id": 350625
  },
  {
    "area_name": "东山县",
    "pinyin": "Dongshan",
    "level": 3,
    "area_code": 596,
    "post_code": 363400,
    "pid": 350600,
    "id": 1229,
    "area_id": 350626
  },
  {
    "area_name": "南靖县",
    "pinyin": "Nanjing",
    "level": 3,
    "area_code": 596,
    "post_code": 363600,
    "pid": 350600,
    "id": 1230,
    "area_id": 350627
  },
  {
    "area_name": "平和县",
    "pinyin": "Pinghe",
    "level": 3,
    "area_code": 596,
    "post_code": 363700,
    "pid": 350600,
    "id": 1231,
    "area_id": 350628
  },
  {
    "area_name": "华安县",
    "pinyin": "Hua'an",
    "level": 3,
    "area_code": 596,
    "post_code": 363800,
    "pid": 350600,
    "id": 1232,
    "area_id": 350629
  },
  {
    "area_name": "龙海市",
    "pinyin": "Longhai",
    "level": 3,
    "area_code": 596,
    "post_code": 363100,
    "pid": 350600,
    "id": 1233,
    "area_id": 350681
  },
  {
    "area_name": "南平市",
    "pinyin": "Nanping",
    "level": 2,
    "area_code": 599,
    "post_code": 353000,
    "pid": 350000,
    "id": 1234,
    "area_id": 350700
  },
  {
    "area_name": "延平区",
    "pinyin": "Yanping",
    "level": 3,
    "area_code": 600,
    "post_code": 353000,
    "pid": 350700,
    "id": 1235,
    "area_id": 350702
  },
  {
    "area_name": "建阳区",
    "pinyin": "Jianyang",
    "level": 3,
    "area_code": 599,
    "post_code": 354200,
    "pid": 350700,
    "id": 1236,
    "area_id": 350703
  },
  {
    "area_name": "顺昌县",
    "pinyin": "Shunchang",
    "level": 3,
    "area_code": 605,
    "post_code": 353200,
    "pid": 350700,
    "id": 1237,
    "area_id": 350721
  },
  {
    "area_name": "浦城县",
    "pinyin": "Pucheng",
    "level": 3,
    "area_code": 606,
    "post_code": 353400,
    "pid": 350700,
    "id": 1238,
    "area_id": 350722
  },
  {
    "area_name": "光泽县",
    "pinyin": "Guangze",
    "level": 3,
    "area_code": 607,
    "post_code": 354100,
    "pid": 350700,
    "id": 1239,
    "area_id": 350723
  },
  {
    "area_name": "松溪县",
    "pinyin": "Songxi",
    "level": 3,
    "area_code": 608,
    "post_code": 353500,
    "pid": 350700,
    "id": 1240,
    "area_id": 350724
  },
  {
    "area_name": "政和县",
    "pinyin": "Zhenghe",
    "level": 3,
    "area_code": 609,
    "post_code": 353600,
    "pid": 350700,
    "id": 1241,
    "area_id": 350725
  },
  {
    "area_name": "邵武市",
    "pinyin": "Shaowu",
    "level": 3,
    "area_code": 601,
    "post_code": 354000,
    "pid": 350700,
    "id": 1242,
    "area_id": 350781
  },
  {
    "area_name": "武夷山市",
    "pinyin": "Wuyishan",
    "level": 3,
    "area_code": 602,
    "post_code": 354300,
    "pid": 350700,
    "id": 1243,
    "area_id": 350782
  },
  {
    "area_name": "建瓯市",
    "pinyin": "Jianou",
    "level": 3,
    "area_code": 603,
    "post_code": 353100,
    "pid": 350700,
    "id": 1244,
    "area_id": 350783
  },
  {
    "area_name": "龙岩市",
    "pinyin": "Longyan",
    "level": 2,
    "area_code": 597,
    "post_code": 364000,
    "pid": 350000,
    "id": 1245,
    "area_id": 350800
  },
  {
    "area_name": "新罗区",
    "pinyin": "Xinluo",
    "level": 3,
    "area_code": 597,
    "post_code": 364000,
    "pid": 350800,
    "id": 1246,
    "area_id": 350802
  },
  {
    "area_name": "长汀县",
    "pinyin": "Changting",
    "level": 3,
    "area_code": 597,
    "post_code": 366300,
    "pid": 350800,
    "id": 1247,
    "area_id": 350821
  },
  {
    "area_name": "永定区",
    "pinyin": "Yongding",
    "level": 3,
    "area_code": 597,
    "post_code": 364100,
    "pid": 350800,
    "id": 1248,
    "area_id": 350822
  },
  {
    "area_name": "上杭县",
    "pinyin": "Shanghang",
    "level": 3,
    "area_code": 597,
    "post_code": 364200,
    "pid": 350800,
    "id": 1249,
    "area_id": 350823
  },
  {
    "area_name": "武平县",
    "pinyin": "Wuping",
    "level": 3,
    "area_code": 597,
    "post_code": 364300,
    "pid": 350800,
    "id": 1250,
    "area_id": 350824
  },
  {
    "area_name": "连城县",
    "pinyin": "Liancheng",
    "level": 3,
    "area_code": 597,
    "post_code": 366200,
    "pid": 350800,
    "id": 1251,
    "area_id": 350825
  },
  {
    "area_name": "漳平市",
    "pinyin": "Zhangping",
    "level": 3,
    "area_code": 597,
    "post_code": 364400,
    "pid": 350800,
    "id": 1252,
    "area_id": 350881
  },
  {
    "area_name": "宁德市",
    "pinyin": "Ningde",
    "level": 2,
    "area_code": 593,
    "post_code": 352100,
    "pid": 350000,
    "id": 1253,
    "area_id": 350900
  },
  {
    "area_name": "蕉城区",
    "pinyin": "Jiaocheng",
    "level": 3,
    "area_code": 593,
    "post_code": 352100,
    "pid": 350900,
    "id": 1254,
    "area_id": 350902
  },
  {
    "area_name": "霞浦县",
    "pinyin": "Xiapu",
    "level": 3,
    "area_code": 593,
    "post_code": 355100,
    "pid": 350900,
    "id": 1255,
    "area_id": 350921
  },
  {
    "area_name": "古田县",
    "pinyin": "Gutian",
    "level": 3,
    "area_code": 593,
    "post_code": 352200,
    "pid": 350900,
    "id": 1256,
    "area_id": 350922
  },
  {
    "area_name": "屏南县",
    "pinyin": "Pingnan",
    "level": 3,
    "area_code": 593,
    "post_code": 352300,
    "pid": 350900,
    "id": 1257,
    "area_id": 350923
  },
  {
    "area_name": "寿宁县",
    "pinyin": "Shouning",
    "level": 3,
    "area_code": 593,
    "post_code": 355500,
    "pid": 350900,
    "id": 1258,
    "area_id": 350924
  },
  {
    "area_name": "周宁县",
    "pinyin": "Zhouning",
    "level": 3,
    "area_code": 593,
    "post_code": 355400,
    "pid": 350900,
    "id": 1259,
    "area_id": 350925
  },
  {
    "area_name": "柘荣县",
    "pinyin": "Zherong",
    "level": 3,
    "area_code": 593,
    "post_code": 355300,
    "pid": 350900,
    "id": 1260,
    "area_id": 350926
  },
  {
    "area_name": "福安市",
    "pinyin": "Fu'an",
    "level": 3,
    "area_code": 593,
    "post_code": 355000,
    "pid": 350900,
    "id": 1261,
    "area_id": 350981
  },
  {
    "area_name": "福鼎市",
    "pinyin": "Fuding",
    "level": 3,
    "area_code": 593,
    "post_code": 355200,
    "pid": 350900,
    "id": 1262,
    "area_id": 350982
  },
  {
    "area_name": "江西省",
    "pinyin": "Jiangxi",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1263,
    "area_id": 360000
  },
  {
    "area_name": "南昌市",
    "pinyin": "Nanchang",
    "level": 2,
    "area_code": 791,
    "post_code": 330008,
    "pid": 360000,
    "id": 1264,
    "area_id": 360100
  },
  {
    "area_name": "东湖区",
    "pinyin": "Donghu",
    "level": 3,
    "area_code": 791,
    "post_code": 330006,
    "pid": 360100,
    "id": 1265,
    "area_id": 360102
  },
  {
    "area_name": "西湖区",
    "pinyin": "Xihu",
    "level": 3,
    "area_code": 791,
    "post_code": 330009,
    "pid": 360100,
    "id": 1266,
    "area_id": 360103
  },
  {
    "area_name": "青云谱区",
    "pinyin": "Qingyunpu",
    "level": 3,
    "area_code": 791,
    "post_code": 330001,
    "pid": 360100,
    "id": 1267,
    "area_id": 360104
  },
  {
    "area_name": "湾里区",
    "pinyin": "Wanli",
    "level": 3,
    "area_code": 791,
    "post_code": 330004,
    "pid": 360100,
    "id": 1268,
    "area_id": 360105
  },
  {
    "area_name": "青山湖区",
    "pinyin": "Qingshanhu",
    "level": 3,
    "area_code": 791,
    "post_code": 330029,
    "pid": 360100,
    "id": 1269,
    "area_id": 360111
  },
  {
    "area_name": "南昌县",
    "pinyin": "Nanchang",
    "level": 3,
    "area_code": 791,
    "post_code": 330200,
    "pid": 360100,
    "id": 1270,
    "area_id": 360121
  },
  {
    "area_name": "新建县",
    "pinyin": "Xinjian",
    "level": 3,
    "area_code": 791,
    "post_code": 330100,
    "pid": 360100,
    "id": 1271,
    "area_id": 360122
  },
  {
    "area_name": "安义县",
    "pinyin": "Anyi",
    "level": 3,
    "area_code": 791,
    "post_code": 330500,
    "pid": 360100,
    "id": 1272,
    "area_id": 360123
  },
  {
    "area_name": "进贤县",
    "pinyin": "Jinxian",
    "level": 3,
    "area_code": 791,
    "post_code": 331700,
    "pid": 360100,
    "id": 1273,
    "area_id": 360124
  },
  {
    "area_name": "景德镇市",
    "pinyin": "Jingdezhen",
    "level": 2,
    "area_code": 798,
    "post_code": 333000,
    "pid": 360000,
    "id": 1274,
    "area_id": 360200
  },
  {
    "area_name": "昌江区",
    "pinyin": "Changjiang",
    "level": 3,
    "area_code": 799,
    "post_code": 333000,
    "pid": 360200,
    "id": 1275,
    "area_id": 360202
  },
  {
    "area_name": "珠山区",
    "pinyin": "Zhushan",
    "level": 3,
    "area_code": 800,
    "post_code": 333000,
    "pid": 360200,
    "id": 1276,
    "area_id": 360203
  },
  {
    "area_name": "浮梁县",
    "pinyin": "Fuliang",
    "level": 3,
    "area_code": 802,
    "post_code": 333400,
    "pid": 360200,
    "id": 1277,
    "area_id": 360222
  },
  {
    "area_name": "乐平市",
    "pinyin": "Leping",
    "level": 3,
    "area_code": 801,
    "post_code": 333300,
    "pid": 360200,
    "id": 1278,
    "area_id": 360281
  },
  {
    "area_name": "萍乡市",
    "pinyin": "Pingxiang",
    "level": 2,
    "area_code": 799,
    "post_code": 337000,
    "pid": 360000,
    "id": 1279,
    "area_id": 360300
  },
  {
    "area_name": "安源区",
    "pinyin": "Anyuan",
    "level": 3,
    "area_code": 800,
    "post_code": 337000,
    "pid": 360300,
    "id": 1280,
    "area_id": 360302
  },
  {
    "area_name": "湘东区",
    "pinyin": "Xiangdong",
    "level": 3,
    "area_code": 801,
    "post_code": 337016,
    "pid": 360300,
    "id": 1281,
    "area_id": 360313
  },
  {
    "area_name": "莲花县",
    "pinyin": "Lianhua",
    "level": 3,
    "area_code": 802,
    "post_code": 337100,
    "pid": 360300,
    "id": 1282,
    "area_id": 360321
  },
  {
    "area_name": "上栗县",
    "pinyin": "Shangli",
    "level": 3,
    "area_code": 803,
    "post_code": 337009,
    "pid": 360300,
    "id": 1283,
    "area_id": 360322
  },
  {
    "area_name": "芦溪县",
    "pinyin": "Luxi",
    "level": 3,
    "area_code": 804,
    "post_code": 337053,
    "pid": 360300,
    "id": 1284,
    "area_id": 360323
  },
  {
    "area_name": "九江市",
    "pinyin": "Jiujiang",
    "level": 2,
    "area_code": 792,
    "post_code": 332000,
    "pid": 360000,
    "id": 1285,
    "area_id": 360400
  },
  {
    "area_name": "庐山区",
    "pinyin": "Lushan",
    "level": 3,
    "area_code": 792,
    "post_code": 332005,
    "pid": 360400,
    "id": 1286,
    "area_id": 360402
  },
  {
    "area_name": "浔阳区",
    "pinyin": "Xunyang",
    "level": 3,
    "area_code": 792,
    "post_code": 332000,
    "pid": 360400,
    "id": 1287,
    "area_id": 360403
  },
  {
    "area_name": "九江县",
    "pinyin": "Jiujiang",
    "level": 3,
    "area_code": 792,
    "post_code": 332100,
    "pid": 360400,
    "id": 1288,
    "area_id": 360421
  },
  {
    "area_name": "武宁县",
    "pinyin": "Wuning",
    "level": 3,
    "area_code": 792,
    "post_code": 332300,
    "pid": 360400,
    "id": 1289,
    "area_id": 360423
  },
  {
    "area_name": "修水县",
    "pinyin": "Xiushui",
    "level": 3,
    "area_code": 792,
    "post_code": 332400,
    "pid": 360400,
    "id": 1290,
    "area_id": 360424
  },
  {
    "area_name": "永修县",
    "pinyin": "Yongxiu",
    "level": 3,
    "area_code": 792,
    "post_code": 330300,
    "pid": 360400,
    "id": 1291,
    "area_id": 360425
  },
  {
    "area_name": "德安县",
    "pinyin": "De'an",
    "level": 3,
    "area_code": 792,
    "post_code": 330400,
    "pid": 360400,
    "id": 1292,
    "area_id": 360426
  },
  {
    "area_name": "星子县",
    "pinyin": "Xingzi",
    "level": 3,
    "area_code": 792,
    "post_code": 332800,
    "pid": 360400,
    "id": 1293,
    "area_id": 360427
  },
  {
    "area_name": "都昌县",
    "pinyin": "Duchang",
    "level": 3,
    "area_code": 792,
    "post_code": 332600,
    "pid": 360400,
    "id": 1294,
    "area_id": 360428
  },
  {
    "area_name": "湖口县",
    "pinyin": "Hukou",
    "level": 3,
    "area_code": 792,
    "post_code": 332500,
    "pid": 360400,
    "id": 1295,
    "area_id": 360429
  },
  {
    "area_name": "彭泽县",
    "pinyin": "Pengze",
    "level": 3,
    "area_code": 792,
    "post_code": 332700,
    "pid": 360400,
    "id": 1296,
    "area_id": 360430
  },
  {
    "area_name": "瑞昌市",
    "pinyin": "Ruichang",
    "level": 3,
    "area_code": 792,
    "post_code": 332200,
    "pid": 360400,
    "id": 1297,
    "area_id": 360481
  },
  {
    "area_name": "共青城市",
    "pinyin": "Gongqingcheng",
    "level": 3,
    "area_code": 792,
    "post_code": 332020,
    "pid": 360400,
    "id": 1298,
    "area_id": 360482
  },
  {
    "area_name": "新余市",
    "pinyin": "Xinyu",
    "level": 2,
    "area_code": 790,
    "post_code": 338025,
    "pid": 360000,
    "id": 1299,
    "area_id": 360500
  },
  {
    "area_name": "渝水区",
    "pinyin": "Yushui",
    "level": 3,
    "area_code": 790,
    "post_code": 338025,
    "pid": 360500,
    "id": 1300,
    "area_id": 360502
  },
  {
    "area_name": "分宜县",
    "pinyin": "Fenyi",
    "level": 3,
    "area_code": 790,
    "post_code": 336600,
    "pid": 360500,
    "id": 1301,
    "area_id": 360521
  },
  {
    "area_name": "鹰潭市",
    "pinyin": "Yingtan",
    "level": 2,
    "area_code": 701,
    "post_code": 335000,
    "pid": 360000,
    "id": 1302,
    "area_id": 360600
  },
  {
    "area_name": "月湖区",
    "pinyin": "Yuehu",
    "level": 3,
    "area_code": 701,
    "post_code": 335000,
    "pid": 360600,
    "id": 1303,
    "area_id": 360602
  },
  {
    "area_name": "余江县",
    "pinyin": "Yujiang",
    "level": 3,
    "area_code": 701,
    "post_code": 335200,
    "pid": 360600,
    "id": 1304,
    "area_id": 360622
  },
  {
    "area_name": "贵溪市",
    "pinyin": "Guixi",
    "level": 3,
    "area_code": 701,
    "post_code": 335400,
    "pid": 360600,
    "id": 1305,
    "area_id": 360681
  },
  {
    "area_name": "赣州市",
    "pinyin": "Ganzhou",
    "level": 2,
    "area_code": 797,
    "post_code": 341000,
    "pid": 360000,
    "id": 1306,
    "area_id": 360700
  },
  {
    "area_name": "章贡区",
    "pinyin": "Zhanggong",
    "level": 3,
    "area_code": 797,
    "post_code": 341000,
    "pid": 360700,
    "id": 1307,
    "area_id": 360702
  },
  {
    "area_name": "南康区",
    "pinyin": "Nankang",
    "level": 3,
    "area_code": 797,
    "post_code": 341400,
    "pid": 360700,
    "id": 1308,
    "area_id": 360703
  },
  {
    "area_name": "赣县",
    "pinyin": "Ganxian",
    "level": 3,
    "area_code": 797,
    "post_code": 341100,
    "pid": 360700,
    "id": 1309,
    "area_id": 360721
  },
  {
    "area_name": "信丰县",
    "pinyin": "Xinfeng",
    "level": 3,
    "area_code": 797,
    "post_code": 341600,
    "pid": 360700,
    "id": 1310,
    "area_id": 360722
  },
  {
    "area_name": "大余县",
    "pinyin": "Dayu",
    "level": 3,
    "area_code": 797,
    "post_code": 341500,
    "pid": 360700,
    "id": 1311,
    "area_id": 360723
  },
  {
    "area_name": "上犹县",
    "pinyin": "Shangyou",
    "level": 3,
    "area_code": 797,
    "post_code": 341200,
    "pid": 360700,
    "id": 1312,
    "area_id": 360724
  },
  {
    "area_name": "崇义县",
    "pinyin": "Chongyi",
    "level": 3,
    "area_code": 797,
    "post_code": 341300,
    "pid": 360700,
    "id": 1313,
    "area_id": 360725
  },
  {
    "area_name": "安远县",
    "pinyin": "Anyuan",
    "level": 3,
    "area_code": 797,
    "post_code": 342100,
    "pid": 360700,
    "id": 1314,
    "area_id": 360726
  },
  {
    "area_name": "龙南县",
    "pinyin": "Longnan",
    "level": 3,
    "area_code": 797,
    "post_code": 341700,
    "pid": 360700,
    "id": 1315,
    "area_id": 360727
  },
  {
    "area_name": "定南县",
    "pinyin": "Dingnan",
    "level": 3,
    "area_code": 797,
    "post_code": 341900,
    "pid": 360700,
    "id": 1316,
    "area_id": 360728
  },
  {
    "area_name": "全南县",
    "pinyin": "Quannan",
    "level": 3,
    "area_code": 797,
    "post_code": 341800,
    "pid": 360700,
    "id": 1317,
    "area_id": 360729
  },
  {
    "area_name": "宁都县",
    "pinyin": "Ningdu",
    "level": 3,
    "area_code": 797,
    "post_code": 342800,
    "pid": 360700,
    "id": 1318,
    "area_id": 360730
  },
  {
    "area_name": "于都县",
    "pinyin": "Yudu",
    "level": 3,
    "area_code": 797,
    "post_code": 342300,
    "pid": 360700,
    "id": 1319,
    "area_id": 360731
  },
  {
    "area_name": "兴国县",
    "pinyin": "Xingguo",
    "level": 3,
    "area_code": 797,
    "post_code": 342400,
    "pid": 360700,
    "id": 1320,
    "area_id": 360732
  },
  {
    "area_name": "会昌县",
    "pinyin": "Huichang",
    "level": 3,
    "area_code": 797,
    "post_code": 342600,
    "pid": 360700,
    "id": 1321,
    "area_id": 360733
  },
  {
    "area_name": "寻乌县",
    "pinyin": "Xunwu",
    "level": 3,
    "area_code": 797,
    "post_code": 342200,
    "pid": 360700,
    "id": 1322,
    "area_id": 360734
  },
  {
    "area_name": "石城县",
    "pinyin": "Shicheng",
    "level": 3,
    "area_code": 797,
    "post_code": 342700,
    "pid": 360700,
    "id": 1323,
    "area_id": 360735
  },
  {
    "area_name": "瑞金市",
    "pinyin": "Ruijin",
    "level": 3,
    "area_code": 797,
    "post_code": 342500,
    "pid": 360700,
    "id": 1324,
    "area_id": 360781
  },
  {
    "area_name": "吉安市",
    "pinyin": "Ji'an",
    "level": 2,
    "area_code": 796,
    "post_code": 343000,
    "pid": 360000,
    "id": 1325,
    "area_id": 360800
  },
  {
    "area_name": "吉州区",
    "pinyin": "Jizhou",
    "level": 3,
    "area_code": 796,
    "post_code": 343000,
    "pid": 360800,
    "id": 1326,
    "area_id": 360802
  },
  {
    "area_name": "青原区",
    "pinyin": "Qingyuan",
    "level": 3,
    "area_code": 796,
    "post_code": 343009,
    "pid": 360800,
    "id": 1327,
    "area_id": 360803
  },
  {
    "area_name": "吉安县",
    "pinyin": "Ji'an",
    "level": 3,
    "area_code": 796,
    "post_code": 343100,
    "pid": 360800,
    "id": 1328,
    "area_id": 360821
  },
  {
    "area_name": "吉水县",
    "pinyin": "Jishui",
    "level": 3,
    "area_code": 796,
    "post_code": 331600,
    "pid": 360800,
    "id": 1329,
    "area_id": 360822
  },
  {
    "area_name": "峡江县",
    "pinyin": "Xiajiang",
    "level": 3,
    "area_code": 796,
    "post_code": 331409,
    "pid": 360800,
    "id": 1330,
    "area_id": 360823
  },
  {
    "area_name": "新干县",
    "pinyin": "Xingan",
    "level": 3,
    "area_code": 796,
    "post_code": 331300,
    "pid": 360800,
    "id": 1331,
    "area_id": 360824
  },
  {
    "area_name": "永丰县",
    "pinyin": "Yongfeng",
    "level": 3,
    "area_code": 796,
    "post_code": 331500,
    "pid": 360800,
    "id": 1332,
    "area_id": 360825
  },
  {
    "area_name": "泰和县",
    "pinyin": "Taihe",
    "level": 3,
    "area_code": 796,
    "post_code": 343700,
    "pid": 360800,
    "id": 1333,
    "area_id": 360826
  },
  {
    "area_name": "遂川县",
    "pinyin": "Suichuan",
    "level": 3,
    "area_code": 796,
    "post_code": 343900,
    "pid": 360800,
    "id": 1334,
    "area_id": 360827
  },
  {
    "area_name": "万安县",
    "pinyin": "Wan'an",
    "level": 3,
    "area_code": 796,
    "post_code": 343800,
    "pid": 360800,
    "id": 1335,
    "area_id": 360828
  },
  {
    "area_name": "安福县",
    "pinyin": "Anfu",
    "level": 3,
    "area_code": 796,
    "post_code": 343200,
    "pid": 360800,
    "id": 1336,
    "area_id": 360829
  },
  {
    "area_name": "永新县",
    "pinyin": "Yongxin",
    "level": 3,
    "area_code": 796,
    "post_code": 343400,
    "pid": 360800,
    "id": 1337,
    "area_id": 360830
  },
  {
    "area_name": "井冈山市",
    "pinyin": "Jinggangshan",
    "level": 3,
    "area_code": 796,
    "post_code": 343600,
    "pid": 360800,
    "id": 1338,
    "area_id": 360881
  },
  {
    "area_name": "宜春市",
    "pinyin": "Yichun",
    "level": 2,
    "area_code": 795,
    "post_code": 336000,
    "pid": 360000,
    "id": 1339,
    "area_id": 360900
  },
  {
    "area_name": "袁州区",
    "pinyin": "Yuanzhou",
    "level": 3,
    "area_code": 795,
    "post_code": 336000,
    "pid": 360900,
    "id": 1340,
    "area_id": 360902
  },
  {
    "area_name": "奉新县",
    "pinyin": "Fengxin",
    "level": 3,
    "area_code": 795,
    "post_code": 330700,
    "pid": 360900,
    "id": 1341,
    "area_id": 360921
  },
  {
    "area_name": "万载县",
    "pinyin": "Wanzai",
    "level": 3,
    "area_code": 795,
    "post_code": 336100,
    "pid": 360900,
    "id": 1342,
    "area_id": 360922
  },
  {
    "area_name": "上高县",
    "pinyin": "Shanggao",
    "level": 3,
    "area_code": 795,
    "post_code": 336400,
    "pid": 360900,
    "id": 1343,
    "area_id": 360923
  },
  {
    "area_name": "宜丰县",
    "pinyin": "Yifeng",
    "level": 3,
    "area_code": 795,
    "post_code": 336300,
    "pid": 360900,
    "id": 1344,
    "area_id": 360924
  },
  {
    "area_name": "靖安县",
    "pinyin": "Jing'an",
    "level": 3,
    "area_code": 795,
    "post_code": 330600,
    "pid": 360900,
    "id": 1345,
    "area_id": 360925
  },
  {
    "area_name": "铜鼓县",
    "pinyin": "Tonggu",
    "level": 3,
    "area_code": 795,
    "post_code": 336200,
    "pid": 360900,
    "id": 1346,
    "area_id": 360926
  },
  {
    "area_name": "丰城市",
    "pinyin": "Fengcheng",
    "level": 3,
    "area_code": 795,
    "post_code": 331100,
    "pid": 360900,
    "id": 1347,
    "area_id": 360981
  },
  {
    "area_name": "樟树市",
    "pinyin": "Zhangshu",
    "level": 3,
    "area_code": 795,
    "post_code": 331200,
    "pid": 360900,
    "id": 1348,
    "area_id": 360982
  },
  {
    "area_name": "高安市",
    "pinyin": "Gao'an",
    "level": 3,
    "area_code": 795,
    "post_code": 330800,
    "pid": 360900,
    "id": 1349,
    "area_id": 360983
  },
  {
    "area_name": "抚州市",
    "pinyin": "Fuzhou",
    "level": 2,
    "area_code": 794,
    "post_code": 344000,
    "pid": 360000,
    "id": 1350,
    "area_id": 361000
  },
  {
    "area_name": "临川区",
    "pinyin": "Linchuan",
    "level": 3,
    "area_code": 794,
    "post_code": 344000,
    "pid": 361000,
    "id": 1351,
    "area_id": 361002
  },
  {
    "area_name": "南城县",
    "pinyin": "Nancheng",
    "level": 3,
    "area_code": 794,
    "post_code": 344700,
    "pid": 361000,
    "id": 1352,
    "area_id": 361021
  },
  {
    "area_name": "黎川县",
    "pinyin": "Lichuan",
    "level": 3,
    "area_code": 794,
    "post_code": 344600,
    "pid": 361000,
    "id": 1353,
    "area_id": 361022
  },
  {
    "area_name": "南丰县",
    "pinyin": "Nanfeng",
    "level": 3,
    "area_code": 794,
    "post_code": 344500,
    "pid": 361000,
    "id": 1354,
    "area_id": 361023
  },
  {
    "area_name": "崇仁县",
    "pinyin": "Chongren",
    "level": 3,
    "area_code": 794,
    "post_code": 344200,
    "pid": 361000,
    "id": 1355,
    "area_id": 361024
  },
  {
    "area_name": "乐安县",
    "pinyin": "Le'an",
    "level": 3,
    "area_code": 794,
    "post_code": 344300,
    "pid": 361000,
    "id": 1356,
    "area_id": 361025
  },
  {
    "area_name": "宜黄县",
    "pinyin": "Yihuang",
    "level": 3,
    "area_code": 794,
    "post_code": 344400,
    "pid": 361000,
    "id": 1357,
    "area_id": 361026
  },
  {
    "area_name": "金溪县",
    "pinyin": "Jinxi",
    "level": 3,
    "area_code": 794,
    "post_code": 344800,
    "pid": 361000,
    "id": 1358,
    "area_id": 361027
  },
  {
    "area_name": "资溪县",
    "pinyin": "Zixi",
    "level": 3,
    "area_code": 794,
    "post_code": 335300,
    "pid": 361000,
    "id": 1359,
    "area_id": 361028
  },
  {
    "area_name": "东乡县",
    "pinyin": "Dongxiang",
    "level": 3,
    "area_code": 794,
    "post_code": 331800,
    "pid": 361000,
    "id": 1360,
    "area_id": 361029
  },
  {
    "area_name": "广昌县",
    "pinyin": "Guangchang",
    "level": 3,
    "area_code": 794,
    "post_code": 344900,
    "pid": 361000,
    "id": 1361,
    "area_id": 361030
  },
  {
    "area_name": "上饶市",
    "pinyin": "Shangrao",
    "level": 2,
    "area_code": 793,
    "post_code": 334000,
    "pid": 360000,
    "id": 1362,
    "area_id": 361100
  },
  {
    "area_name": "信州区",
    "pinyin": "Xinzhou",
    "level": 3,
    "area_code": 793,
    "post_code": 334000,
    "pid": 361100,
    "id": 1363,
    "area_id": 361102
  },
  {
    "area_name": "上饶县",
    "pinyin": "Shangrao",
    "level": 3,
    "area_code": 793,
    "post_code": 334100,
    "pid": 361100,
    "id": 1364,
    "area_id": 361121
  },
  {
    "area_name": "广丰县",
    "pinyin": "Guangfeng",
    "level": 3,
    "area_code": 793,
    "post_code": 334600,
    "pid": 361100,
    "id": 1365,
    "area_id": 361122
  },
  {
    "area_name": "玉山县",
    "pinyin": "Yushan",
    "level": 3,
    "area_code": 793,
    "post_code": 334700,
    "pid": 361100,
    "id": 1366,
    "area_id": 361123
  },
  {
    "area_name": "铅山县",
    "pinyin": "Yanshan",
    "level": 3,
    "area_code": 793,
    "post_code": 334500,
    "pid": 361100,
    "id": 1367,
    "area_id": 361124
  },
  {
    "area_name": "横峰县",
    "pinyin": "Hengfeng",
    "level": 3,
    "area_code": 793,
    "post_code": 334300,
    "pid": 361100,
    "id": 1368,
    "area_id": 361125
  },
  {
    "area_name": "弋阳县",
    "pinyin": "Yiyang",
    "level": 3,
    "area_code": 793,
    "post_code": 334400,
    "pid": 361100,
    "id": 1369,
    "area_id": 361126
  },
  {
    "area_name": "余干县",
    "pinyin": "Yugan",
    "level": 3,
    "area_code": 793,
    "post_code": 335100,
    "pid": 361100,
    "id": 1370,
    "area_id": 361127
  },
  {
    "area_name": "鄱阳县",
    "pinyin": "Poyang",
    "level": 3,
    "area_code": 793,
    "post_code": 333100,
    "pid": 361100,
    "id": 1371,
    "area_id": 361128
  },
  {
    "area_name": "万年县",
    "pinyin": "Wannian",
    "level": 3,
    "area_code": 793,
    "post_code": 335500,
    "pid": 361100,
    "id": 1372,
    "area_id": 361129
  },
  {
    "area_name": "婺源县",
    "pinyin": "Wuyuan",
    "level": 3,
    "area_code": 793,
    "post_code": 333200,
    "pid": 361100,
    "id": 1373,
    "area_id": 361130
  },
  {
    "area_name": "德兴市",
    "pinyin": "Dexing",
    "level": 3,
    "area_code": 793,
    "post_code": 334200,
    "pid": 361100,
    "id": 1374,
    "area_id": 361181
  },
  {
    "area_name": "山东省",
    "pinyin": "Shandong",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1375,
    "area_id": 370000
  },
  {
    "area_name": "济南市",
    "pinyin": "Jinan",
    "level": 2,
    "area_code": 531,
    "post_code": 250001,
    "pid": 370000,
    "id": 1376,
    "area_id": 370100
  },
  {
    "area_name": "历下区",
    "pinyin": "Lixia",
    "level": 3,
    "area_code": 531,
    "post_code": 250014,
    "pid": 370100,
    "id": 1377,
    "area_id": 370102
  },
  {
    "area_name": "市中区",
    "pinyin": "Shizhongqu",
    "level": 3,
    "area_code": 531,
    "post_code": 250001,
    "pid": 370100,
    "id": 1378,
    "area_id": 370103
  },
  {
    "area_name": "槐荫区",
    "pinyin": "Huaiyin",
    "level": 3,
    "area_code": 531,
    "post_code": 250117,
    "pid": 370100,
    "id": 1379,
    "area_id": 370104
  },
  {
    "area_name": "天桥区",
    "pinyin": "Tianqiao",
    "level": 3,
    "area_code": 531,
    "post_code": 250031,
    "pid": 370100,
    "id": 1380,
    "area_id": 370105
  },
  {
    "area_name": "历城区",
    "pinyin": "Licheng",
    "level": 3,
    "area_code": 531,
    "post_code": 250100,
    "pid": 370100,
    "id": 1381,
    "area_id": 370112
  },
  {
    "area_name": "长清区",
    "pinyin": "Changqing",
    "level": 3,
    "area_code": 531,
    "post_code": 250300,
    "pid": 370100,
    "id": 1382,
    "area_id": 370113
  },
  {
    "area_name": "平阴县",
    "pinyin": "Pingyin",
    "level": 3,
    "area_code": 531,
    "post_code": 250400,
    "pid": 370100,
    "id": 1383,
    "area_id": 370124
  },
  {
    "area_name": "济阳县",
    "pinyin": "Jiyang",
    "level": 3,
    "area_code": 531,
    "post_code": 251400,
    "pid": 370100,
    "id": 1384,
    "area_id": 370125
  },
  {
    "area_name": "商河县",
    "pinyin": "Shanghe",
    "level": 3,
    "area_code": 531,
    "post_code": 251600,
    "pid": 370100,
    "id": 1385,
    "area_id": 370126
  },
  {
    "area_name": "章丘市",
    "pinyin": "Zhangqiu",
    "level": 3,
    "area_code": 531,
    "post_code": 250200,
    "pid": 370100,
    "id": 1386,
    "area_id": 370181
  },
  {
    "area_name": "青岛市",
    "pinyin": "Qingdao",
    "level": 2,
    "area_code": 532,
    "post_code": 266001,
    "pid": 370000,
    "id": 1387,
    "area_id": 370200
  },
  {
    "area_name": "市南区",
    "pinyin": "Shinan",
    "level": 3,
    "area_code": 532,
    "post_code": 266001,
    "pid": 370200,
    "id": 1388,
    "area_id": 370202
  },
  {
    "area_name": "市北区",
    "pinyin": "Shibei",
    "level": 3,
    "area_code": 532,
    "post_code": 266011,
    "pid": 370200,
    "id": 1389,
    "area_id": 370203
  },
  {
    "area_name": "黄岛区",
    "pinyin": "Huangdao",
    "level": 3,
    "area_code": 532,
    "post_code": 266500,
    "pid": 370200,
    "id": 1390,
    "area_id": 370211
  },
  {
    "area_name": "崂山区",
    "pinyin": "Laoshan",
    "level": 3,
    "area_code": 532,
    "post_code": 266100,
    "pid": 370200,
    "id": 1391,
    "area_id": 370212
  },
  {
    "area_name": "李沧区",
    "pinyin": "Licang",
    "level": 3,
    "area_code": 532,
    "post_code": 266021,
    "pid": 370200,
    "id": 1392,
    "area_id": 370213
  },
  {
    "area_name": "城阳区",
    "pinyin": "Chengyang",
    "level": 3,
    "area_code": 532,
    "post_code": 266041,
    "pid": 370200,
    "id": 1393,
    "area_id": 370214
  },
  {
    "area_name": "胶州市",
    "pinyin": "Jiaozhou",
    "level": 3,
    "area_code": 532,
    "post_code": 266300,
    "pid": 370200,
    "id": 1394,
    "area_id": 370281
  },
  {
    "area_name": "即墨市",
    "pinyin": "Jimo",
    "level": 3,
    "area_code": 532,
    "post_code": 266200,
    "pid": 370200,
    "id": 1395,
    "area_id": 370282
  },
  {
    "area_name": "平度市",
    "pinyin": "Pingdu",
    "level": 3,
    "area_code": 532,
    "post_code": 266700,
    "pid": 370200,
    "id": 1396,
    "area_id": 370283
  },
  {
    "area_name": "莱西市",
    "pinyin": "Laixi",
    "level": 3,
    "area_code": 532,
    "post_code": 266600,
    "pid": 370200,
    "id": 1397,
    "area_id": 370285
  },
  {
    "area_name": "西海岸新区",
    "pinyin": "Xihai'an",
    "level": 3,
    "area_code": 532,
    "post_code": 266500,
    "pid": 370200,
    "id": 1398,
    "area_id": 370286
  },
  {
    "area_name": "淄博市",
    "pinyin": "Zibo",
    "level": 2,
    "area_code": 533,
    "post_code": 255039,
    "pid": 370000,
    "id": 1399,
    "area_id": 370300
  },
  {
    "area_name": "淄川区",
    "pinyin": "Zichuan",
    "level": 3,
    "area_code": 533,
    "post_code": 255100,
    "pid": 370300,
    "id": 1400,
    "area_id": 370302
  },
  {
    "area_name": "张店区",
    "pinyin": "Zhangdian",
    "level": 3,
    "area_code": 533,
    "post_code": 255022,
    "pid": 370300,
    "id": 1401,
    "area_id": 370303
  },
  {
    "area_name": "博山区",
    "pinyin": "Boshan",
    "level": 3,
    "area_code": 533,
    "post_code": 255200,
    "pid": 370300,
    "id": 1402,
    "area_id": 370304
  },
  {
    "area_name": "临淄区",
    "pinyin": "Linzi",
    "level": 3,
    "area_code": 533,
    "post_code": 255400,
    "pid": 370300,
    "id": 1403,
    "area_id": 370305
  },
  {
    "area_name": "周村区",
    "pinyin": "Zhoucun",
    "level": 3,
    "area_code": 533,
    "post_code": 255300,
    "pid": 370300,
    "id": 1404,
    "area_id": 370306
  },
  {
    "area_name": "桓台县",
    "pinyin": "Huantai",
    "level": 3,
    "area_code": 533,
    "post_code": 256400,
    "pid": 370300,
    "id": 1405,
    "area_id": 370321
  },
  {
    "area_name": "高青县",
    "pinyin": "Gaoqing",
    "level": 3,
    "area_code": 533,
    "post_code": 256300,
    "pid": 370300,
    "id": 1406,
    "area_id": 370322
  },
  {
    "area_name": "沂源县",
    "pinyin": "Yiyuan",
    "level": 3,
    "area_code": 533,
    "post_code": 256100,
    "pid": 370300,
    "id": 1407,
    "area_id": 370323
  },
  {
    "area_name": "枣庄市",
    "pinyin": "Zaozhuang",
    "level": 2,
    "area_code": 632,
    "post_code": 277101,
    "pid": 370000,
    "id": 1408,
    "area_id": 370400
  },
  {
    "area_name": "市中区",
    "pinyin": "Shizhongqu",
    "level": 3,
    "area_code": 632,
    "post_code": 277101,
    "pid": 370400,
    "id": 1409,
    "area_id": 370402
  },
  {
    "area_name": "薛城区",
    "pinyin": "Xuecheng",
    "level": 3,
    "area_code": 632,
    "post_code": 277000,
    "pid": 370400,
    "id": 1410,
    "area_id": 370403
  },
  {
    "area_name": "峄城区",
    "pinyin": "Yicheng",
    "level": 3,
    "area_code": 632,
    "post_code": 277300,
    "pid": 370400,
    "id": 1411,
    "area_id": 370404
  },
  {
    "area_name": "台儿庄区",
    "pinyin": "Taierzhuang",
    "level": 3,
    "area_code": 632,
    "post_code": 277400,
    "pid": 370400,
    "id": 1412,
    "area_id": 370405
  },
  {
    "area_name": "山亭区",
    "pinyin": "Shanting",
    "level": 3,
    "area_code": 632,
    "post_code": 277200,
    "pid": 370400,
    "id": 1413,
    "area_id": 370406
  },
  {
    "area_name": "滕州市",
    "pinyin": "Tengzhou",
    "level": 3,
    "area_code": 632,
    "post_code": 277500,
    "pid": 370400,
    "id": 1414,
    "area_id": 370481
  },
  {
    "area_name": "东营市",
    "pinyin": "Dongying",
    "level": 2,
    "area_code": 546,
    "post_code": 257093,
    "pid": 370000,
    "id": 1415,
    "area_id": 370500
  },
  {
    "area_name": "东营区",
    "pinyin": "Dongying",
    "level": 3,
    "area_code": 546,
    "post_code": 257029,
    "pid": 370500,
    "id": 1416,
    "area_id": 370502
  },
  {
    "area_name": "河口区",
    "pinyin": "Hekou",
    "level": 3,
    "area_code": 546,
    "post_code": 257200,
    "pid": 370500,
    "id": 1417,
    "area_id": 370503
  },
  {
    "area_name": "垦利县",
    "pinyin": "Kenli",
    "level": 3,
    "area_code": 546,
    "post_code": 257500,
    "pid": 370500,
    "id": 1418,
    "area_id": 370521
  },
  {
    "area_name": "利津县",
    "pinyin": "Lijin",
    "level": 3,
    "area_code": 546,
    "post_code": 257400,
    "pid": 370500,
    "id": 1419,
    "area_id": 370522
  },
  {
    "area_name": "广饶县",
    "pinyin": "Guangrao",
    "level": 3,
    "area_code": 546,
    "post_code": 257300,
    "pid": 370500,
    "id": 1420,
    "area_id": 370523
  },
  {
    "area_name": "烟台市",
    "pinyin": "Yantai",
    "level": 2,
    "area_code": 635,
    "post_code": 264010,
    "pid": 370000,
    "id": 1421,
    "area_id": 370600
  },
  {
    "area_name": "芝罘区",
    "pinyin": "Zhifu",
    "level": 3,
    "area_code": 635,
    "post_code": 264001,
    "pid": 370600,
    "id": 1422,
    "area_id": 370602
  },
  {
    "area_name": "福山区",
    "pinyin": "Fushan",
    "level": 3,
    "area_code": 635,
    "post_code": 265500,
    "pid": 370600,
    "id": 1423,
    "area_id": 370611
  },
  {
    "area_name": "牟平区",
    "pinyin": "Muping",
    "level": 3,
    "area_code": 635,
    "post_code": 264100,
    "pid": 370600,
    "id": 1424,
    "area_id": 370612
  },
  {
    "area_name": "莱山区",
    "pinyin": "Laishan",
    "level": 3,
    "area_code": 635,
    "post_code": 264600,
    "pid": 370600,
    "id": 1425,
    "area_id": 370613
  },
  {
    "area_name": "长岛县",
    "pinyin": "Changdao",
    "level": 3,
    "area_code": 635,
    "post_code": 265800,
    "pid": 370600,
    "id": 1426,
    "area_id": 370634
  },
  {
    "area_name": "龙口市",
    "pinyin": "Longkou",
    "level": 3,
    "area_code": 635,
    "post_code": 265700,
    "pid": 370600,
    "id": 1427,
    "area_id": 370681
  },
  {
    "area_name": "莱阳市",
    "pinyin": "Laiyang",
    "level": 3,
    "area_code": 635,
    "post_code": 265200,
    "pid": 370600,
    "id": 1428,
    "area_id": 370682
  },
  {
    "area_name": "莱州市",
    "pinyin": "Laizhou",
    "level": 3,
    "area_code": 635,
    "post_code": 261400,
    "pid": 370600,
    "id": 1429,
    "area_id": 370683
  },
  {
    "area_name": "蓬莱市",
    "pinyin": "Penglai",
    "level": 3,
    "area_code": 635,
    "post_code": 265600,
    "pid": 370600,
    "id": 1430,
    "area_id": 370684
  },
  {
    "area_name": "招远市",
    "pinyin": "Zhaoyuan",
    "level": 3,
    "area_code": 635,
    "post_code": 265400,
    "pid": 370600,
    "id": 1431,
    "area_id": 370685
  },
  {
    "area_name": "栖霞市",
    "pinyin": "Qixia",
    "level": 3,
    "area_code": 635,
    "post_code": 265300,
    "pid": 370600,
    "id": 1432,
    "area_id": 370686
  },
  {
    "area_name": "海阳市",
    "pinyin": "Haiyang",
    "level": 3,
    "area_code": 635,
    "post_code": 265100,
    "pid": 370600,
    "id": 1433,
    "area_id": 370687
  },
  {
    "area_name": "潍坊市",
    "pinyin": "Weifang",
    "level": 2,
    "area_code": 536,
    "post_code": 261041,
    "pid": 370000,
    "id": 1434,
    "area_id": 370700
  },
  {
    "area_name": "潍城区",
    "pinyin": "Weicheng",
    "level": 3,
    "area_code": 536,
    "post_code": 261021,
    "pid": 370700,
    "id": 1435,
    "area_id": 370702
  },
  {
    "area_name": "寒亭区",
    "pinyin": "Hanting",
    "level": 3,
    "area_code": 536,
    "post_code": 261100,
    "pid": 370700,
    "id": 1436,
    "area_id": 370703
  },
  {
    "area_name": "坊子区",
    "pinyin": "Fangzi",
    "level": 3,
    "area_code": 536,
    "post_code": 261200,
    "pid": 370700,
    "id": 1437,
    "area_id": 370704
  },
  {
    "area_name": "奎文区",
    "pinyin": "Kuiwen",
    "level": 3,
    "area_code": 536,
    "post_code": 261031,
    "pid": 370700,
    "id": 1438,
    "area_id": 370705
  },
  {
    "area_name": "临朐县",
    "pinyin": "Linqu",
    "level": 3,
    "area_code": 536,
    "post_code": 262600,
    "pid": 370700,
    "id": 1439,
    "area_id": 370724
  },
  {
    "area_name": "昌乐县",
    "pinyin": "Changle",
    "level": 3,
    "area_code": 536,
    "post_code": 262400,
    "pid": 370700,
    "id": 1440,
    "area_id": 370725
  },
  {
    "area_name": "青州市",
    "pinyin": "Qingzhou",
    "level": 3,
    "area_code": 536,
    "post_code": 262500,
    "pid": 370700,
    "id": 1441,
    "area_id": 370781
  },
  {
    "area_name": "诸城市",
    "pinyin": "Zhucheng",
    "level": 3,
    "area_code": 536,
    "post_code": 262200,
    "pid": 370700,
    "id": 1442,
    "area_id": 370782
  },
  {
    "area_name": "寿光市",
    "pinyin": "Shouguang",
    "level": 3,
    "area_code": 536,
    "post_code": 262700,
    "pid": 370700,
    "id": 1443,
    "area_id": 370783
  },
  {
    "area_name": "安丘市",
    "pinyin": "Anqiu",
    "level": 3,
    "area_code": 536,
    "post_code": 262100,
    "pid": 370700,
    "id": 1444,
    "area_id": 370784
  },
  {
    "area_name": "高密市",
    "pinyin": "Gaomi",
    "level": 3,
    "area_code": 536,
    "post_code": 261500,
    "pid": 370700,
    "id": 1445,
    "area_id": 370785
  },
  {
    "area_name": "昌邑市",
    "pinyin": "Changyi",
    "level": 3,
    "area_code": 536,
    "post_code": 261300,
    "pid": 370700,
    "id": 1446,
    "area_id": 370786
  },
  {
    "area_name": "济宁市",
    "pinyin": "Jining",
    "level": 2,
    "area_code": 537,
    "post_code": 272119,
    "pid": 370000,
    "id": 1447,
    "area_id": 370800
  },
  {
    "area_name": "任城区",
    "pinyin": "Rencheng",
    "level": 3,
    "area_code": 537,
    "post_code": 272113,
    "pid": 370800,
    "id": 1448,
    "area_id": 370811
  },
  {
    "area_name": "兖州区",
    "pinyin": "Yanzhou ",
    "level": 3,
    "area_code": 537,
    "post_code": 272000,
    "pid": 370800,
    "id": 1449,
    "area_id": 370812
  },
  {
    "area_name": "微山县",
    "pinyin": "Weishan",
    "level": 3,
    "area_code": 537,
    "post_code": 277600,
    "pid": 370800,
    "id": 1450,
    "area_id": 370826
  },
  {
    "area_name": "鱼台县",
    "pinyin": "Yutai",
    "level": 3,
    "area_code": 537,
    "post_code": 272300,
    "pid": 370800,
    "id": 1451,
    "area_id": 370827
  },
  {
    "area_name": "金乡县",
    "pinyin": "Jinxiang",
    "level": 3,
    "area_code": 537,
    "post_code": 272200,
    "pid": 370800,
    "id": 1452,
    "area_id": 370828
  },
  {
    "area_name": "嘉祥县",
    "pinyin": "Jiaxiang",
    "level": 3,
    "area_code": 537,
    "post_code": 272400,
    "pid": 370800,
    "id": 1453,
    "area_id": 370829
  },
  {
    "area_name": "汶上县",
    "pinyin": "Wenshang",
    "level": 3,
    "area_code": 537,
    "post_code": 272501,
    "pid": 370800,
    "id": 1454,
    "area_id": 370830
  },
  {
    "area_name": "泗水县",
    "pinyin": "Sishui",
    "level": 3,
    "area_code": 537,
    "post_code": 273200,
    "pid": 370800,
    "id": 1455,
    "area_id": 370831
  },
  {
    "area_name": "梁山县",
    "pinyin": "Liangshan",
    "level": 3,
    "area_code": 537,
    "post_code": 272600,
    "pid": 370800,
    "id": 1456,
    "area_id": 370832
  },
  {
    "area_name": "曲阜市",
    "pinyin": "Qufu",
    "level": 3,
    "area_code": 537,
    "post_code": 273100,
    "pid": 370800,
    "id": 1457,
    "area_id": 370881
  },
  {
    "area_name": "邹城市",
    "pinyin": "Zoucheng",
    "level": 3,
    "area_code": 537,
    "post_code": 273500,
    "pid": 370800,
    "id": 1458,
    "area_id": 370883
  },
  {
    "area_name": "泰安市",
    "pinyin": "Tai'an",
    "level": 2,
    "area_code": 538,
    "post_code": 271000,
    "pid": 370000,
    "id": 1459,
    "area_id": 370900
  },
  {
    "area_name": "泰山区",
    "pinyin": "Taishan",
    "level": 3,
    "area_code": 538,
    "post_code": 271000,
    "pid": 370900,
    "id": 1460,
    "area_id": 370902
  },
  {
    "area_name": "岱岳区",
    "pinyin": "Daiyue",
    "level": 3,
    "area_code": 538,
    "post_code": 271000,
    "pid": 370900,
    "id": 1461,
    "area_id": 370911
  },
  {
    "area_name": "宁阳县",
    "pinyin": "Ningyang",
    "level": 3,
    "area_code": 538,
    "post_code": 271400,
    "pid": 370900,
    "id": 1462,
    "area_id": 370921
  },
  {
    "area_name": "东平县",
    "pinyin": "Dongping",
    "level": 3,
    "area_code": 538,
    "post_code": 271500,
    "pid": 370900,
    "id": 1463,
    "area_id": 370923
  },
  {
    "area_name": "新泰市",
    "pinyin": "Xintai",
    "level": 3,
    "area_code": 538,
    "post_code": 271200,
    "pid": 370900,
    "id": 1464,
    "area_id": 370982
  },
  {
    "area_name": "肥城市",
    "pinyin": "Feicheng",
    "level": 3,
    "area_code": 538,
    "post_code": 271600,
    "pid": 370900,
    "id": 1465,
    "area_id": 370983
  },
  {
    "area_name": "威海市",
    "pinyin": "Weihai",
    "level": 2,
    "area_code": 631,
    "post_code": 264200,
    "pid": 370000,
    "id": 1466,
    "area_id": 371000
  },
  {
    "area_name": "环翠区",
    "pinyin": "Huancui",
    "level": 3,
    "area_code": 631,
    "post_code": 264200,
    "pid": 371000,
    "id": 1467,
    "area_id": 371002
  },
  {
    "area_name": "文登区",
    "pinyin": "Wendeng",
    "level": 3,
    "area_code": 631,
    "post_code": 266440,
    "pid": 371000,
    "id": 1468,
    "area_id": 371003
  },
  {
    "area_name": "荣成市",
    "pinyin": "Rongcheng",
    "level": 3,
    "area_code": 631,
    "post_code": 264300,
    "pid": 371000,
    "id": 1469,
    "area_id": 371082
  },
  {
    "area_name": "乳山市",
    "pinyin": "Rushan",
    "level": 3,
    "area_code": 631,
    "post_code": 264500,
    "pid": 371000,
    "id": 1470,
    "area_id": 371083
  },
  {
    "area_name": "日照市",
    "pinyin": "Rizhao",
    "level": 2,
    "area_code": 633,
    "post_code": 276800,
    "pid": 370000,
    "id": 1471,
    "area_id": 371100
  },
  {
    "area_name": "东港区",
    "pinyin": "Donggang",
    "level": 3,
    "area_code": 633,
    "post_code": 276800,
    "pid": 371100,
    "id": 1472,
    "area_id": 371102
  },
  {
    "area_name": "岚山区",
    "pinyin": "Lanshan",
    "level": 3,
    "area_code": 633,
    "post_code": 276808,
    "pid": 371100,
    "id": 1473,
    "area_id": 371103
  },
  {
    "area_name": "五莲县",
    "pinyin": "Wulian",
    "level": 3,
    "area_code": 633,
    "post_code": 262300,
    "pid": 371100,
    "id": 1474,
    "area_id": 371121
  },
  {
    "area_name": "莒县",
    "pinyin": "Juxian",
    "level": 3,
    "area_code": 633,
    "post_code": 276500,
    "pid": 371100,
    "id": 1475,
    "area_id": 371122
  },
  {
    "area_name": "莱芜市",
    "pinyin": "Laiwu",
    "level": 2,
    "area_code": 634,
    "post_code": 271100,
    "pid": 370000,
    "id": 1476,
    "area_id": 371200
  },
  {
    "area_name": "莱城区",
    "pinyin": "Laicheng",
    "level": 3,
    "area_code": 634,
    "post_code": 271199,
    "pid": 371200,
    "id": 1477,
    "area_id": 371202
  },
  {
    "area_name": "钢城区",
    "pinyin": "Gangcheng",
    "level": 3,
    "area_code": 634,
    "post_code": 271100,
    "pid": 371200,
    "id": 1478,
    "area_id": 371203
  },
  {
    "area_name": "临沂市",
    "pinyin": "Linyi",
    "level": 2,
    "area_code": 539,
    "post_code": 253000,
    "pid": 370000,
    "id": 1479,
    "area_id": 371300
  },
  {
    "area_name": "兰山区",
    "pinyin": "Lanshan",
    "level": 3,
    "area_code": 539,
    "post_code": 276002,
    "pid": 371300,
    "id": 1480,
    "area_id": 371302
  },
  {
    "area_name": "罗庄区",
    "pinyin": "Luozhuang",
    "level": 3,
    "area_code": 539,
    "post_code": 276022,
    "pid": 371300,
    "id": 1481,
    "area_id": 371311
  },
  {
    "area_name": "河东区",
    "pinyin": "Hedong",
    "level": 3,
    "area_code": 539,
    "post_code": 276034,
    "pid": 371300,
    "id": 1482,
    "area_id": 371312
  },
  {
    "area_name": "沂南县",
    "pinyin": "Yinan",
    "level": 3,
    "area_code": 539,
    "post_code": 276300,
    "pid": 371300,
    "id": 1483,
    "area_id": 371321
  },
  {
    "area_name": "郯城县",
    "pinyin": "Tancheng",
    "level": 3,
    "area_code": 539,
    "post_code": 276100,
    "pid": 371300,
    "id": 1484,
    "area_id": 371322
  },
  {
    "area_name": "沂水县",
    "pinyin": "Yishui",
    "level": 3,
    "area_code": 539,
    "post_code": 276400,
    "pid": 371300,
    "id": 1485,
    "area_id": 371323
  },
  {
    "area_name": "兰陵县",
    "pinyin": "Lanling",
    "level": 3,
    "area_code": 539,
    "post_code": 277700,
    "pid": 371300,
    "id": 1486,
    "area_id": 371324
  },
  {
    "area_name": "费县",
    "pinyin": "Feixian",
    "level": 3,
    "area_code": 539,
    "post_code": 273400,
    "pid": 371300,
    "id": 1487,
    "area_id": 371325
  },
  {
    "area_name": "平邑县",
    "pinyin": "Pingyi",
    "level": 3,
    "area_code": 539,
    "post_code": 273300,
    "pid": 371300,
    "id": 1488,
    "area_id": 371326
  },
  {
    "area_name": "莒南县",
    "pinyin": "Junan",
    "level": 3,
    "area_code": 539,
    "post_code": 276600,
    "pid": 371300,
    "id": 1489,
    "area_id": 371327
  },
  {
    "area_name": "蒙阴县",
    "pinyin": "Mengyin",
    "level": 3,
    "area_code": 539,
    "post_code": 276200,
    "pid": 371300,
    "id": 1490,
    "area_id": 371328
  },
  {
    "area_name": "临沭县",
    "pinyin": "Linshu",
    "level": 3,
    "area_code": 539,
    "post_code": 276700,
    "pid": 371300,
    "id": 1491,
    "area_id": 371329
  },
  {
    "area_name": "德州市",
    "pinyin": "Dezhou",
    "level": 2,
    "area_code": 534,
    "post_code": 253000,
    "pid": 370000,
    "id": 1492,
    "area_id": 371400
  },
  {
    "area_name": "德城区",
    "pinyin": "Decheng",
    "level": 3,
    "area_code": 534,
    "post_code": 253012,
    "pid": 371400,
    "id": 1493,
    "area_id": 371402
  },
  {
    "area_name": "陵城区",
    "pinyin": "Lingcheng",
    "level": 3,
    "area_code": 534,
    "post_code": 253500,
    "pid": 371400,
    "id": 1494,
    "area_id": 371403
  },
  {
    "area_name": "宁津县",
    "pinyin": "Ningjin",
    "level": 3,
    "area_code": 534,
    "post_code": 253400,
    "pid": 371400,
    "id": 1495,
    "area_id": 371422
  },
  {
    "area_name": "庆云县",
    "pinyin": "Qingyun",
    "level": 3,
    "area_code": 534,
    "post_code": 253700,
    "pid": 371400,
    "id": 1496,
    "area_id": 371423
  },
  {
    "area_name": "临邑县",
    "pinyin": "Linyi",
    "level": 3,
    "area_code": 534,
    "post_code": 251500,
    "pid": 371400,
    "id": 1497,
    "area_id": 371424
  },
  {
    "area_name": "齐河县",
    "pinyin": "Qihe",
    "level": 3,
    "area_code": 534,
    "post_code": 251100,
    "pid": 371400,
    "id": 1498,
    "area_id": 371425
  },
  {
    "area_name": "平原县",
    "pinyin": "Pingyuan",
    "level": 3,
    "area_code": 534,
    "post_code": 253100,
    "pid": 371400,
    "id": 1499,
    "area_id": 371426
  },
  {
    "area_name": "夏津县",
    "pinyin": "Xiajin",
    "level": 3,
    "area_code": 534,
    "post_code": 253200,
    "pid": 371400,
    "id": 1500,
    "area_id": 371427
  },
  {
    "area_name": "武城县",
    "pinyin": "Wucheng",
    "level": 3,
    "area_code": 534,
    "post_code": 253300,
    "pid": 371400,
    "id": 1501,
    "area_id": 371428
  },
  {
    "area_name": "乐陵市",
    "pinyin": "Leling",
    "level": 3,
    "area_code": 534,
    "post_code": 253600,
    "pid": 371400,
    "id": 1502,
    "area_id": 371481
  },
  {
    "area_name": "禹城市",
    "pinyin": "Yucheng",
    "level": 3,
    "area_code": 534,
    "post_code": 251200,
    "pid": 371400,
    "id": 1503,
    "area_id": 371482
  },
  {
    "area_name": "聊城市",
    "pinyin": "Liaocheng",
    "level": 2,
    "area_code": 635,
    "post_code": 252052,
    "pid": 370000,
    "id": 1504,
    "area_id": 371500
  },
  {
    "area_name": "东昌府区",
    "pinyin": "Dongchangfu",
    "level": 3,
    "area_code": 635,
    "post_code": 252000,
    "pid": 371500,
    "id": 1505,
    "area_id": 371502
  },
  {
    "area_name": "阳谷县",
    "pinyin": "Yanggu",
    "level": 3,
    "area_code": 635,
    "post_code": 252300,
    "pid": 371500,
    "id": 1506,
    "area_id": 371521
  },
  {
    "area_name": "莘县",
    "pinyin": "Shenxian",
    "level": 3,
    "area_code": 635,
    "post_code": 252400,
    "pid": 371500,
    "id": 1507,
    "area_id": 371522
  },
  {
    "area_name": "茌平县",
    "pinyin": "Chiping",
    "level": 3,
    "area_code": 635,
    "post_code": 252100,
    "pid": 371500,
    "id": 1508,
    "area_id": 371523
  },
  {
    "area_name": "东阿县",
    "pinyin": "Dong'e",
    "level": 3,
    "area_code": 635,
    "post_code": 252200,
    "pid": 371500,
    "id": 1509,
    "area_id": 371524
  },
  {
    "area_name": "冠县",
    "pinyin": "Guanxian",
    "level": 3,
    "area_code": 635,
    "post_code": 252500,
    "pid": 371500,
    "id": 1510,
    "area_id": 371525
  },
  {
    "area_name": "高唐县",
    "pinyin": "Gaotang",
    "level": 3,
    "area_code": 635,
    "post_code": 252800,
    "pid": 371500,
    "id": 1511,
    "area_id": 371526
  },
  {
    "area_name": "临清市",
    "pinyin": "Linqing",
    "level": 3,
    "area_code": 635,
    "post_code": 252600,
    "pid": 371500,
    "id": 1512,
    "area_id": 371581
  },
  {
    "area_name": "滨州市",
    "pinyin": "Binzhou",
    "level": 2,
    "area_code": 543,
    "post_code": 256619,
    "pid": 370000,
    "id": 1513,
    "area_id": 371600
  },
  {
    "area_name": "滨城区",
    "pinyin": "Bincheng",
    "level": 3,
    "area_code": 543,
    "post_code": 256613,
    "pid": 371600,
    "id": 1514,
    "area_id": 371602
  },
  {
    "area_name": "沾化区",
    "pinyin": "Zhanhua",
    "level": 3,
    "area_code": 543,
    "post_code": 256800,
    "pid": 371600,
    "id": 1515,
    "area_id": 371603
  },
  {
    "area_name": "惠民县",
    "pinyin": "Huimin",
    "level": 3,
    "area_code": 543,
    "post_code": 251700,
    "pid": 371600,
    "id": 1516,
    "area_id": 371621
  },
  {
    "area_name": "阳信县",
    "pinyin": "Yangxin",
    "level": 3,
    "area_code": 543,
    "post_code": 251800,
    "pid": 371600,
    "id": 1517,
    "area_id": 371622
  },
  {
    "area_name": "无棣县",
    "pinyin": "Wudi",
    "level": 3,
    "area_code": 543,
    "post_code": 251900,
    "pid": 371600,
    "id": 1518,
    "area_id": 371623
  },
  {
    "area_name": "博兴县",
    "pinyin": "Boxing",
    "level": 3,
    "area_code": 543,
    "post_code": 256500,
    "pid": 371600,
    "id": 1519,
    "area_id": 371625
  },
  {
    "area_name": "邹平县",
    "pinyin": "Zouping",
    "level": 3,
    "area_code": 543,
    "post_code": 256200,
    "pid": 371600,
    "id": 1520,
    "area_id": 371626
  },
  {
    "area_name": "北海新区",
    "pinyin": "Beihaixinqu",
    "level": 3,
    "area_code": 543,
    "post_code": 256200,
    "pid": 371600,
    "id": 1521,
    "area_id": 371627
  },
  {
    "area_name": "菏泽市",
    "pinyin": "Heze",
    "level": 2,
    "area_code": 530,
    "post_code": 274020,
    "pid": 370000,
    "id": 1522,
    "area_id": 371700
  },
  {
    "area_name": "牡丹区",
    "pinyin": "Mudan",
    "level": 3,
    "area_code": 530,
    "post_code": 274009,
    "pid": 371700,
    "id": 1523,
    "area_id": 371702
  },
  {
    "area_name": "曹县",
    "pinyin": "Caoxian",
    "level": 3,
    "area_code": 530,
    "post_code": 274400,
    "pid": 371700,
    "id": 1524,
    "area_id": 371721
  },
  {
    "area_name": "单县",
    "pinyin": "Shanxian",
    "level": 3,
    "area_code": 530,
    "post_code": 273700,
    "pid": 371700,
    "id": 1525,
    "area_id": 371722
  },
  {
    "area_name": "成武县",
    "pinyin": "Chengwu",
    "level": 3,
    "area_code": 530,
    "post_code": 274200,
    "pid": 371700,
    "id": 1526,
    "area_id": 371723
  },
  {
    "area_name": "巨野县",
    "pinyin": "Juye",
    "level": 3,
    "area_code": 530,
    "post_code": 274900,
    "pid": 371700,
    "id": 1527,
    "area_id": 371724
  },
  {
    "area_name": "郓城县",
    "pinyin": "Yuncheng",
    "level": 3,
    "area_code": 530,
    "post_code": 274700,
    "pid": 371700,
    "id": 1528,
    "area_id": 371725
  },
  {
    "area_name": "鄄城县",
    "pinyin": "Juancheng",
    "level": 3,
    "area_code": 530,
    "post_code": 274600,
    "pid": 371700,
    "id": 1529,
    "area_id": 371726
  },
  {
    "area_name": "定陶县",
    "pinyin": "Dingtao",
    "level": 3,
    "area_code": 530,
    "post_code": 274100,
    "pid": 371700,
    "id": 1530,
    "area_id": 371727
  },
  {
    "area_name": "东明县",
    "pinyin": "Dongming",
    "level": 3,
    "area_code": 530,
    "post_code": 274500,
    "pid": 371700,
    "id": 1531,
    "area_id": 371728
  },
  {
    "area_name": "河南省",
    "pinyin": "Henan",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1532,
    "area_id": 410000
  },
  {
    "area_name": "郑州市",
    "pinyin": "Zhengzhou",
    "level": 2,
    "area_code": 371,
    "post_code": 450000,
    "pid": 410000,
    "id": 1533,
    "area_id": 410100
  },
  {
    "area_name": "中原区",
    "pinyin": "Zhongyuan",
    "level": 3,
    "area_code": 371,
    "post_code": 450007,
    "pid": 410100,
    "id": 1534,
    "area_id": 410102
  },
  {
    "area_name": "二七区",
    "pinyin": "Erqi",
    "level": 3,
    "area_code": 371,
    "post_code": 450052,
    "pid": 410100,
    "id": 1535,
    "area_id": 410103
  },
  {
    "area_name": "管城回族区",
    "pinyin": "Guancheng",
    "level": 3,
    "area_code": 371,
    "post_code": 450000,
    "pid": 410100,
    "id": 1536,
    "area_id": 410104
  },
  {
    "area_name": "金水区",
    "pinyin": "Jinshui",
    "level": 3,
    "area_code": 371,
    "post_code": 450003,
    "pid": 410100,
    "id": 1537,
    "area_id": 410105
  },
  {
    "area_name": "上街区",
    "pinyin": "Shangjie",
    "level": 3,
    "area_code": 371,
    "post_code": 450041,
    "pid": 410100,
    "id": 1538,
    "area_id": 410106
  },
  {
    "area_name": "惠济区",
    "pinyin": "Huiji",
    "level": 3,
    "area_code": 371,
    "post_code": 450053,
    "pid": 410100,
    "id": 1539,
    "area_id": 410108
  },
  {
    "area_name": "中牟县",
    "pinyin": "Zhongmu",
    "level": 3,
    "area_code": 371,
    "post_code": 451450,
    "pid": 410100,
    "id": 1540,
    "area_id": 410122
  },
  {
    "area_name": "巩义市",
    "pinyin": "Gongyi",
    "level": 3,
    "area_code": 371,
    "post_code": 451200,
    "pid": 410100,
    "id": 1541,
    "area_id": 410181
  },
  {
    "area_name": "荥阳市",
    "pinyin": "Xingyang",
    "level": 3,
    "area_code": 371,
    "post_code": 450100,
    "pid": 410100,
    "id": 1542,
    "area_id": 410182
  },
  {
    "area_name": "新密市",
    "pinyin": "Xinmi",
    "level": 3,
    "area_code": 371,
    "post_code": 452300,
    "pid": 410100,
    "id": 1543,
    "area_id": 410183
  },
  {
    "area_name": "新郑市",
    "pinyin": "Xinzheng",
    "level": 3,
    "area_code": 371,
    "post_code": 451100,
    "pid": 410100,
    "id": 1544,
    "area_id": 410184
  },
  {
    "area_name": "登封市",
    "pinyin": "Dengfeng",
    "level": 3,
    "area_code": 371,
    "post_code": 452470,
    "pid": 410100,
    "id": 1545,
    "area_id": 410185
  },
  {
    "area_name": "开封市",
    "pinyin": "Kaifeng",
    "level": 2,
    "area_code": 378,
    "post_code": 475001,
    "pid": 410000,
    "id": 1546,
    "area_id": 410200
  },
  {
    "area_name": "龙亭区",
    "pinyin": "Longting",
    "level": 3,
    "area_code": 378,
    "post_code": 475100,
    "pid": 410200,
    "id": 1547,
    "area_id": 410202
  },
  {
    "area_name": "顺河回族区",
    "pinyin": "Shunhe",
    "level": 3,
    "area_code": 378,
    "post_code": 475000,
    "pid": 410200,
    "id": 1548,
    "area_id": 410203
  },
  {
    "area_name": "鼓楼区",
    "pinyin": "Gulou",
    "level": 3,
    "area_code": 378,
    "post_code": 475000,
    "pid": 410200,
    "id": 1549,
    "area_id": 410204
  },
  {
    "area_name": "禹王台区",
    "pinyin": "Yuwangtai",
    "level": 3,
    "area_code": 378,
    "post_code": 475003,
    "pid": 410200,
    "id": 1550,
    "area_id": 410205
  },
  {
    "area_name": "祥符区",
    "pinyin": "Xiangfu",
    "level": 3,
    "area_code": 378,
    "post_code": 475100,
    "pid": 410200,
    "id": 1551,
    "area_id": 410212
  },
  {
    "area_name": "杞县",
    "pinyin": "Qixian",
    "level": 3,
    "area_code": 378,
    "post_code": 475200,
    "pid": 410200,
    "id": 1552,
    "area_id": 410221
  },
  {
    "area_name": "通许县",
    "pinyin": "Tongxu",
    "level": 3,
    "area_code": 378,
    "post_code": 475400,
    "pid": 410200,
    "id": 1553,
    "area_id": 410222
  },
  {
    "area_name": "尉氏县",
    "pinyin": "Weishi",
    "level": 3,
    "area_code": 378,
    "post_code": 475500,
    "pid": 410200,
    "id": 1554,
    "area_id": 410223
  },
  {
    "area_name": "兰考县",
    "pinyin": "Lankao",
    "level": 3,
    "area_code": 378,
    "post_code": 475300,
    "pid": 410200,
    "id": 1555,
    "area_id": 410225
  },
  {
    "area_name": "洛阳市",
    "pinyin": "Luoyang",
    "level": 2,
    "area_code": 379,
    "post_code": 471000,
    "pid": 410000,
    "id": 1556,
    "area_id": 410300
  },
  {
    "area_name": "老城区",
    "pinyin": "Laocheng",
    "level": 3,
    "area_code": 379,
    "post_code": 471002,
    "pid": 410300,
    "id": 1557,
    "area_id": 410302
  },
  {
    "area_name": "西工区",
    "pinyin": "Xigong",
    "level": 3,
    "area_code": 379,
    "post_code": 471000,
    "pid": 410300,
    "id": 1558,
    "area_id": 410303
  },
  {
    "area_name": "瀍河回族区",
    "pinyin": "Chanhe",
    "level": 3,
    "area_code": 379,
    "post_code": 471002,
    "pid": 410300,
    "id": 1559,
    "area_id": 410304
  },
  {
    "area_name": "涧西区",
    "pinyin": "Jianxi",
    "level": 3,
    "area_code": 379,
    "post_code": 471003,
    "pid": 410300,
    "id": 1560,
    "area_id": 410305
  },
  {
    "area_name": "吉利区",
    "pinyin": "Jili",
    "level": 3,
    "area_code": 379,
    "post_code": 471012,
    "pid": 410300,
    "id": 1561,
    "area_id": 410306
  },
  {
    "area_name": "洛龙区",
    "pinyin": "Luolong",
    "level": 3,
    "area_code": 379,
    "post_code": 471000,
    "pid": 410300,
    "id": 1562,
    "area_id": 410311
  },
  {
    "area_name": "孟津县",
    "pinyin": "Mengjin",
    "level": 3,
    "area_code": 379,
    "post_code": 471100,
    "pid": 410300,
    "id": 1563,
    "area_id": 410322
  },
  {
    "area_name": "新安县",
    "pinyin": "Xin'an",
    "level": 3,
    "area_code": 379,
    "post_code": 471800,
    "pid": 410300,
    "id": 1564,
    "area_id": 410323
  },
  {
    "area_name": "栾川县",
    "pinyin": "Luanchuan",
    "level": 3,
    "area_code": 379,
    "post_code": 471500,
    "pid": 410300,
    "id": 1565,
    "area_id": 410324
  },
  {
    "area_name": "嵩县",
    "pinyin": "Songxian",
    "level": 3,
    "area_code": 379,
    "post_code": 471400,
    "pid": 410300,
    "id": 1566,
    "area_id": 410325
  },
  {
    "area_name": "汝阳县",
    "pinyin": "Ruyang",
    "level": 3,
    "area_code": 379,
    "post_code": 471200,
    "pid": 410300,
    "id": 1567,
    "area_id": 410326
  },
  {
    "area_name": "宜阳县",
    "pinyin": "Yiyang",
    "level": 3,
    "area_code": 379,
    "post_code": 471600,
    "pid": 410300,
    "id": 1568,
    "area_id": 410327
  },
  {
    "area_name": "洛宁县",
    "pinyin": "Luoning",
    "level": 3,
    "area_code": 379,
    "post_code": 471700,
    "pid": 410300,
    "id": 1569,
    "area_id": 410328
  },
  {
    "area_name": "伊川县",
    "pinyin": "Yichuan",
    "level": 3,
    "area_code": 379,
    "post_code": 471300,
    "pid": 410300,
    "id": 1570,
    "area_id": 410329
  },
  {
    "area_name": "偃师市",
    "pinyin": "Yanshi",
    "level": 3,
    "area_code": 379,
    "post_code": 471900,
    "pid": 410300,
    "id": 1571,
    "area_id": 410381
  },
  {
    "area_name": "平顶山市",
    "pinyin": "Pingdingshan",
    "level": 2,
    "area_code": 375,
    "post_code": 467000,
    "pid": 410000,
    "id": 1572,
    "area_id": 410400
  },
  {
    "area_name": "新华区",
    "pinyin": "Xinhua",
    "level": 3,
    "area_code": 375,
    "post_code": 467002,
    "pid": 410400,
    "id": 1573,
    "area_id": 410402
  },
  {
    "area_name": "卫东区",
    "pinyin": "Weidong",
    "level": 3,
    "area_code": 375,
    "post_code": 467021,
    "pid": 410400,
    "id": 1574,
    "area_id": 410403
  },
  {
    "area_name": "石龙区",
    "pinyin": "Shilong",
    "level": 3,
    "area_code": 375,
    "post_code": 467045,
    "pid": 410400,
    "id": 1575,
    "area_id": 410404
  },
  {
    "area_name": "湛河区",
    "pinyin": "Zhanhe",
    "level": 3,
    "area_code": 375,
    "post_code": 467000,
    "pid": 410400,
    "id": 1576,
    "area_id": 410411
  },
  {
    "area_name": "宝丰县",
    "pinyin": "Baofeng",
    "level": 3,
    "area_code": 375,
    "post_code": 467400,
    "pid": 410400,
    "id": 1577,
    "area_id": 410421
  },
  {
    "area_name": "叶县",
    "pinyin": "Yexian",
    "level": 3,
    "area_code": 375,
    "post_code": 467200,
    "pid": 410400,
    "id": 1578,
    "area_id": 410422
  },
  {
    "area_name": "鲁山县",
    "pinyin": "Lushan",
    "level": 3,
    "area_code": 375,
    "post_code": 467300,
    "pid": 410400,
    "id": 1579,
    "area_id": 410423
  },
  {
    "area_name": "郏县",
    "pinyin": "Jiaxian",
    "level": 3,
    "area_code": 375,
    "post_code": 467100,
    "pid": 410400,
    "id": 1580,
    "area_id": 410425
  },
  {
    "area_name": "舞钢市",
    "pinyin": "Wugang",
    "level": 3,
    "area_code": 375,
    "post_code": 462500,
    "pid": 410400,
    "id": 1581,
    "area_id": 410481
  },
  {
    "area_name": "汝州市",
    "pinyin": "Ruzhou",
    "level": 3,
    "area_code": 375,
    "post_code": 467500,
    "pid": 410400,
    "id": 1582,
    "area_id": 410482
  },
  {
    "area_name": "安阳市",
    "pinyin": "Anyang",
    "level": 2,
    "area_code": 372,
    "post_code": 455000,
    "pid": 410000,
    "id": 1583,
    "area_id": 410500
  },
  {
    "area_name": "文峰区",
    "pinyin": "Wenfeng",
    "level": 3,
    "area_code": 372,
    "post_code": 455000,
    "pid": 410500,
    "id": 1584,
    "area_id": 410502
  },
  {
    "area_name": "北关区",
    "pinyin": "Beiguan",
    "level": 3,
    "area_code": 372,
    "post_code": 455001,
    "pid": 410500,
    "id": 1585,
    "area_id": 410503
  },
  {
    "area_name": "殷都区",
    "pinyin": "Yindu",
    "level": 3,
    "area_code": 372,
    "post_code": 455004,
    "pid": 410500,
    "id": 1586,
    "area_id": 410505
  },
  {
    "area_name": "龙安区",
    "pinyin": "Long'an",
    "level": 3,
    "area_code": 372,
    "post_code": 455001,
    "pid": 410500,
    "id": 1587,
    "area_id": 410506
  },
  {
    "area_name": "安阳县",
    "pinyin": "Anyang",
    "level": 3,
    "area_code": 372,
    "post_code": 455000,
    "pid": 410500,
    "id": 1588,
    "area_id": 410522
  },
  {
    "area_name": "汤阴县",
    "pinyin": "Tangyin",
    "level": 3,
    "area_code": 372,
    "post_code": 456150,
    "pid": 410500,
    "id": 1589,
    "area_id": 410523
  },
  {
    "area_name": "滑县",
    "pinyin": "Huaxian",
    "level": 3,
    "area_code": 372,
    "post_code": 456400,
    "pid": 410500,
    "id": 1590,
    "area_id": 410526
  },
  {
    "area_name": "内黄县",
    "pinyin": "Neihuang",
    "level": 3,
    "area_code": 372,
    "post_code": 456350,
    "pid": 410500,
    "id": 1591,
    "area_id": 410527
  },
  {
    "area_name": "林州市",
    "pinyin": "Linzhou",
    "level": 3,
    "area_code": 372,
    "post_code": 456550,
    "pid": 410500,
    "id": 1592,
    "area_id": 410581
  },
  {
    "area_name": "鹤壁市",
    "pinyin": "Hebi",
    "level": 2,
    "area_code": 392,
    "post_code": 458030,
    "pid": 410000,
    "id": 1593,
    "area_id": 410600
  },
  {
    "area_name": "鹤山区",
    "pinyin": "Heshan",
    "level": 3,
    "area_code": 392,
    "post_code": 458010,
    "pid": 410600,
    "id": 1594,
    "area_id": 410602
  },
  {
    "area_name": "山城区",
    "pinyin": "Shancheng",
    "level": 3,
    "area_code": 392,
    "post_code": 458000,
    "pid": 410600,
    "id": 1595,
    "area_id": 410603
  },
  {
    "area_name": "淇滨区",
    "pinyin": "Qibin",
    "level": 3,
    "area_code": 392,
    "post_code": 458000,
    "pid": 410600,
    "id": 1596,
    "area_id": 410611
  },
  {
    "area_name": "浚县",
    "pinyin": "Xunxian",
    "level": 3,
    "area_code": 392,
    "post_code": 456250,
    "pid": 410600,
    "id": 1597,
    "area_id": 410621
  },
  {
    "area_name": "淇县",
    "pinyin": "Qixian",
    "level": 3,
    "area_code": 392,
    "post_code": 456750,
    "pid": 410600,
    "id": 1598,
    "area_id": 410622
  },
  {
    "area_name": "新乡市",
    "pinyin": "Xinxiang",
    "level": 2,
    "area_code": 373,
    "post_code": 453000,
    "pid": 410000,
    "id": 1599,
    "area_id": 410700
  },
  {
    "area_name": "红旗区",
    "pinyin": "Hongqi",
    "level": 3,
    "area_code": 373,
    "post_code": 453000,
    "pid": 410700,
    "id": 1600,
    "area_id": 410702
  },
  {
    "area_name": "卫滨区",
    "pinyin": "Weibin",
    "level": 3,
    "area_code": 373,
    "post_code": 453000,
    "pid": 410700,
    "id": 1601,
    "area_id": 410703
  },
  {
    "area_name": "凤泉区",
    "pinyin": "Fengquan",
    "level": 3,
    "area_code": 373,
    "post_code": 453011,
    "pid": 410700,
    "id": 1602,
    "area_id": 410704
  },
  {
    "area_name": "牧野区",
    "pinyin": "Muye",
    "level": 3,
    "area_code": 373,
    "post_code": 453002,
    "pid": 410700,
    "id": 1603,
    "area_id": 410711
  },
  {
    "area_name": "新乡县",
    "pinyin": "Xinxiang",
    "level": 3,
    "area_code": 373,
    "post_code": 453700,
    "pid": 410700,
    "id": 1604,
    "area_id": 410721
  },
  {
    "area_name": "获嘉县",
    "pinyin": "Huojia",
    "level": 3,
    "area_code": 373,
    "post_code": 453800,
    "pid": 410700,
    "id": 1605,
    "area_id": 410724
  },
  {
    "area_name": "原阳县",
    "pinyin": "Yuanyang",
    "level": 3,
    "area_code": 373,
    "post_code": 453500,
    "pid": 410700,
    "id": 1606,
    "area_id": 410725
  },
  {
    "area_name": "延津县",
    "pinyin": "Yanjin",
    "level": 3,
    "area_code": 373,
    "post_code": 453200,
    "pid": 410700,
    "id": 1607,
    "area_id": 410726
  },
  {
    "area_name": "封丘县",
    "pinyin": "Fengqiu",
    "level": 3,
    "area_code": 373,
    "post_code": 453300,
    "pid": 410700,
    "id": 1608,
    "area_id": 410727
  },
  {
    "area_name": "长垣县",
    "pinyin": "Changyuan",
    "level": 3,
    "area_code": 373,
    "post_code": 453400,
    "pid": 410700,
    "id": 1609,
    "area_id": 410728
  },
  {
    "area_name": "卫辉市",
    "pinyin": "Weihui",
    "level": 3,
    "area_code": 373,
    "post_code": 453100,
    "pid": 410700,
    "id": 1610,
    "area_id": 410781
  },
  {
    "area_name": "辉县市",
    "pinyin": "Huixian",
    "level": 3,
    "area_code": 373,
    "post_code": 453600,
    "pid": 410700,
    "id": 1611,
    "area_id": 410782
  },
  {
    "area_name": "焦作市",
    "pinyin": "Jiaozuo",
    "level": 2,
    "area_code": 391,
    "post_code": 454002,
    "pid": 410000,
    "id": 1612,
    "area_id": 410800
  },
  {
    "area_name": "解放区",
    "pinyin": "Jiefang",
    "level": 3,
    "area_code": 391,
    "post_code": 454000,
    "pid": 410800,
    "id": 1613,
    "area_id": 410802
  },
  {
    "area_name": "中站区",
    "pinyin": "Zhongzhan",
    "level": 3,
    "area_code": 391,
    "post_code": 454191,
    "pid": 410800,
    "id": 1614,
    "area_id": 410803
  },
  {
    "area_name": "马村区",
    "pinyin": "Macun",
    "level": 3,
    "area_code": 391,
    "post_code": 454171,
    "pid": 410800,
    "id": 1615,
    "area_id": 410804
  },
  {
    "area_name": "山阳区",
    "pinyin": "Shanyang",
    "level": 3,
    "area_code": 391,
    "post_code": 454002,
    "pid": 410800,
    "id": 1616,
    "area_id": 410811
  },
  {
    "area_name": "修武县",
    "pinyin": "Xiuwu",
    "level": 3,
    "area_code": 391,
    "post_code": 454350,
    "pid": 410800,
    "id": 1617,
    "area_id": 410821
  },
  {
    "area_name": "博爱县",
    "pinyin": "Boai",
    "level": 3,
    "area_code": 391,
    "post_code": 454450,
    "pid": 410800,
    "id": 1618,
    "area_id": 410822
  },
  {
    "area_name": "武陟县",
    "pinyin": "Wuzhi",
    "level": 3,
    "area_code": 391,
    "post_code": 454950,
    "pid": 410800,
    "id": 1619,
    "area_id": 410823
  },
  {
    "area_name": "温县",
    "pinyin": "Wenxian",
    "level": 3,
    "area_code": 391,
    "post_code": 454850,
    "pid": 410800,
    "id": 1620,
    "area_id": 410825
  },
  {
    "area_name": "沁阳市",
    "pinyin": "Qinyang",
    "level": 3,
    "area_code": 391,
    "post_code": 454550,
    "pid": 410800,
    "id": 1621,
    "area_id": 410882
  },
  {
    "area_name": "孟州市",
    "pinyin": "Mengzhou",
    "level": 3,
    "area_code": 391,
    "post_code": 454750,
    "pid": 410800,
    "id": 1622,
    "area_id": 410883
  },
  {
    "area_name": "濮阳市",
    "pinyin": "Puyang",
    "level": 2,
    "area_code": 393,
    "post_code": 457000,
    "pid": 410000,
    "id": 1623,
    "area_id": 410900
  },
  {
    "area_name": "华龙区",
    "pinyin": "Hualong",
    "level": 3,
    "area_code": 393,
    "post_code": 457001,
    "pid": 410900,
    "id": 1624,
    "area_id": 410902
  },
  {
    "area_name": "清丰县",
    "pinyin": "Qingfeng",
    "level": 3,
    "area_code": 393,
    "post_code": 457300,
    "pid": 410900,
    "id": 1625,
    "area_id": 410922
  },
  {
    "area_name": "南乐县",
    "pinyin": "Nanle",
    "level": 3,
    "area_code": 393,
    "post_code": 457400,
    "pid": 410900,
    "id": 1626,
    "area_id": 410923
  },
  {
    "area_name": "范县",
    "pinyin": "Fanxian",
    "level": 3,
    "area_code": 393,
    "post_code": 457500,
    "pid": 410900,
    "id": 1627,
    "area_id": 410926
  },
  {
    "area_name": "台前县",
    "pinyin": "Taiqian",
    "level": 3,
    "area_code": 393,
    "post_code": 457600,
    "pid": 410900,
    "id": 1628,
    "area_id": 410927
  },
  {
    "area_name": "濮阳县",
    "pinyin": "Puyang",
    "level": 3,
    "area_code": 393,
    "post_code": 457100,
    "pid": 410900,
    "id": 1629,
    "area_id": 410928
  },
  {
    "area_name": "许昌市",
    "pinyin": "Xuchang",
    "level": 2,
    "area_code": 374,
    "post_code": 461000,
    "pid": 410000,
    "id": 1630,
    "area_id": 411000
  },
  {
    "area_name": "魏都区",
    "pinyin": "Weidu",
    "level": 3,
    "area_code": 374,
    "post_code": 461000,
    "pid": 411000,
    "id": 1631,
    "area_id": 411002
  },
  {
    "area_name": "许昌县",
    "pinyin": "Xuchang",
    "level": 3,
    "area_code": 374,
    "post_code": 461100,
    "pid": 411000,
    "id": 1632,
    "area_id": 411023
  },
  {
    "area_name": "鄢陵县",
    "pinyin": "Yanling",
    "level": 3,
    "area_code": 374,
    "post_code": 461200,
    "pid": 411000,
    "id": 1633,
    "area_id": 411024
  },
  {
    "area_name": "襄城县",
    "pinyin": "Xiangcheng",
    "level": 3,
    "area_code": 374,
    "post_code": 461700,
    "pid": 411000,
    "id": 1634,
    "area_id": 411025
  },
  {
    "area_name": "禹州市",
    "pinyin": "Yuzhou",
    "level": 3,
    "area_code": 374,
    "post_code": 461670,
    "pid": 411000,
    "id": 1635,
    "area_id": 411081
  },
  {
    "area_name": "长葛市",
    "pinyin": "Changge",
    "level": 3,
    "area_code": 374,
    "post_code": 461500,
    "pid": 411000,
    "id": 1636,
    "area_id": 411082
  },
  {
    "area_name": "漯河市",
    "pinyin": "Luohe",
    "level": 2,
    "area_code": 395,
    "post_code": 462000,
    "pid": 410000,
    "id": 1637,
    "area_id": 411100
  },
  {
    "area_name": "源汇区",
    "pinyin": "Yuanhui",
    "level": 3,
    "area_code": 395,
    "post_code": 462000,
    "pid": 411100,
    "id": 1638,
    "area_id": 411102
  },
  {
    "area_name": "郾城区",
    "pinyin": "Yancheng",
    "level": 3,
    "area_code": 395,
    "post_code": 462300,
    "pid": 411100,
    "id": 1639,
    "area_id": 411103
  },
  {
    "area_name": "召陵区",
    "pinyin": "Zhaoling",
    "level": 3,
    "area_code": 395,
    "post_code": 462300,
    "pid": 411100,
    "id": 1640,
    "area_id": 411104
  },
  {
    "area_name": "舞阳县",
    "pinyin": "Wuyang",
    "level": 3,
    "area_code": 395,
    "post_code": 462400,
    "pid": 411100,
    "id": 1641,
    "area_id": 411121
  },
  {
    "area_name": "临颍县",
    "pinyin": "Linying",
    "level": 3,
    "area_code": 395,
    "post_code": 462600,
    "pid": 411100,
    "id": 1642,
    "area_id": 411122
  },
  {
    "area_name": "三门峡市",
    "pinyin": "Sanmenxia",
    "level": 2,
    "area_code": 398,
    "post_code": 472000,
    "pid": 410000,
    "id": 1643,
    "area_id": 411200
  },
  {
    "area_name": "湖滨区",
    "pinyin": "Hubin",
    "level": 3,
    "area_code": 398,
    "post_code": 472000,
    "pid": 411200,
    "id": 1644,
    "area_id": 411202
  },
  {
    "area_name": "渑池县",
    "pinyin": "Mianchi",
    "level": 3,
    "area_code": 398,
    "post_code": 472400,
    "pid": 411200,
    "id": 1645,
    "area_id": 411221
  },
  {
    "area_name": "陕县",
    "pinyin": "Shanxian",
    "level": 3,
    "area_code": 398,
    "post_code": 472100,
    "pid": 411200,
    "id": 1646,
    "area_id": 411222
  },
  {
    "area_name": "卢氏县",
    "pinyin": "Lushi",
    "level": 3,
    "area_code": 398,
    "post_code": 472200,
    "pid": 411200,
    "id": 1647,
    "area_id": 411224
  },
  {
    "area_name": "义马市",
    "pinyin": "Yima",
    "level": 3,
    "area_code": 398,
    "post_code": 472300,
    "pid": 411200,
    "id": 1648,
    "area_id": 411281
  },
  {
    "area_name": "灵宝市",
    "pinyin": "Lingbao",
    "level": 3,
    "area_code": 398,
    "post_code": 472500,
    "pid": 411200,
    "id": 1649,
    "area_id": 411282
  },
  {
    "area_name": "南阳市",
    "pinyin": "Nanyang",
    "level": 2,
    "area_code": 377,
    "post_code": 473002,
    "pid": 410000,
    "id": 1650,
    "area_id": 411300
  },
  {
    "area_name": "宛城区",
    "pinyin": "Wancheng",
    "level": 3,
    "area_code": 377,
    "post_code": 473001,
    "pid": 411300,
    "id": 1651,
    "area_id": 411302
  },
  {
    "area_name": "卧龙区",
    "pinyin": "Wolong",
    "level": 3,
    "area_code": 377,
    "post_code": 473003,
    "pid": 411300,
    "id": 1652,
    "area_id": 411303
  },
  {
    "area_name": "南召县",
    "pinyin": "Nanzhao",
    "level": 3,
    "area_code": 377,
    "post_code": 474650,
    "pid": 411300,
    "id": 1653,
    "area_id": 411321
  },
  {
    "area_name": "方城县",
    "pinyin": "Fangcheng",
    "level": 3,
    "area_code": 377,
    "post_code": 473200,
    "pid": 411300,
    "id": 1654,
    "area_id": 411322
  },
  {
    "area_name": "西峡县",
    "pinyin": "Xixia",
    "level": 3,
    "area_code": 377,
    "post_code": 474550,
    "pid": 411300,
    "id": 1655,
    "area_id": 411323
  },
  {
    "area_name": "镇平县",
    "pinyin": "Zhenping",
    "level": 3,
    "area_code": 377,
    "post_code": 474250,
    "pid": 411300,
    "id": 1656,
    "area_id": 411324
  },
  {
    "area_name": "内乡县",
    "pinyin": "Neixiang",
    "level": 3,
    "area_code": 377,
    "post_code": 474350,
    "pid": 411300,
    "id": 1657,
    "area_id": 411325
  },
  {
    "area_name": "淅川县",
    "pinyin": "Xichuan",
    "level": 3,
    "area_code": 377,
    "post_code": 474450,
    "pid": 411300,
    "id": 1658,
    "area_id": 411326
  },
  {
    "area_name": "社旗县",
    "pinyin": "Sheqi",
    "level": 3,
    "area_code": 377,
    "post_code": 473300,
    "pid": 411300,
    "id": 1659,
    "area_id": 411327
  },
  {
    "area_name": "唐河县",
    "pinyin": "Tanghe",
    "level": 3,
    "area_code": 377,
    "post_code": 473400,
    "pid": 411300,
    "id": 1660,
    "area_id": 411328
  },
  {
    "area_name": "新野县",
    "pinyin": "Xinye",
    "level": 3,
    "area_code": 377,
    "post_code": 473500,
    "pid": 411300,
    "id": 1661,
    "area_id": 411329
  },
  {
    "area_name": "桐柏县",
    "pinyin": "Tongbai",
    "level": 3,
    "area_code": 377,
    "post_code": 474750,
    "pid": 411300,
    "id": 1662,
    "area_id": 411330
  },
  {
    "area_name": "邓州市",
    "pinyin": "Dengzhou",
    "level": 3,
    "area_code": 377,
    "post_code": 474150,
    "pid": 411300,
    "id": 1663,
    "area_id": 411381
  },
  {
    "area_name": "商丘市",
    "pinyin": "Shangqiu",
    "level": 2,
    "area_code": 370,
    "post_code": 476000,
    "pid": 410000,
    "id": 1664,
    "area_id": 411400
  },
  {
    "area_name": "梁园区",
    "pinyin": "Liangyuan",
    "level": 3,
    "area_code": 370,
    "post_code": 476000,
    "pid": 411400,
    "id": 1665,
    "area_id": 411402
  },
  {
    "area_name": "睢阳区",
    "pinyin": "Suiyang",
    "level": 3,
    "area_code": 370,
    "post_code": 476100,
    "pid": 411400,
    "id": 1666,
    "area_id": 411403
  },
  {
    "area_name": "民权县",
    "pinyin": "Minquan",
    "level": 3,
    "area_code": 370,
    "post_code": 476800,
    "pid": 411400,
    "id": 1667,
    "area_id": 411421
  },
  {
    "area_name": "睢县",
    "pinyin": "Suixian",
    "level": 3,
    "area_code": 370,
    "post_code": 476900,
    "pid": 411400,
    "id": 1668,
    "area_id": 411422
  },
  {
    "area_name": "宁陵县",
    "pinyin": "Ningling",
    "level": 3,
    "area_code": 370,
    "post_code": 476700,
    "pid": 411400,
    "id": 1669,
    "area_id": 411423
  },
  {
    "area_name": "柘城县",
    "pinyin": "Zhecheng",
    "level": 3,
    "area_code": 370,
    "post_code": 476200,
    "pid": 411400,
    "id": 1670,
    "area_id": 411424
  },
  {
    "area_name": "虞城县",
    "pinyin": "Yucheng",
    "level": 3,
    "area_code": 370,
    "post_code": 476300,
    "pid": 411400,
    "id": 1671,
    "area_id": 411425
  },
  {
    "area_name": "夏邑县",
    "pinyin": "Xiayi",
    "level": 3,
    "area_code": 370,
    "post_code": 476400,
    "pid": 411400,
    "id": 1672,
    "area_id": 411426
  },
  {
    "area_name": "永城市",
    "pinyin": "Yongcheng",
    "level": 3,
    "area_code": 370,
    "post_code": 476600,
    "pid": 411400,
    "id": 1673,
    "area_id": 411481
  },
  {
    "area_name": "信阳市",
    "pinyin": "Xinyang",
    "level": 2,
    "area_code": 376,
    "post_code": 464000,
    "pid": 410000,
    "id": 1674,
    "area_id": 411500
  },
  {
    "area_name": "浉河区",
    "pinyin": "Shihe",
    "level": 3,
    "area_code": 376,
    "post_code": 464000,
    "pid": 411500,
    "id": 1675,
    "area_id": 411502
  },
  {
    "area_name": "平桥区",
    "pinyin": "Pingqiao",
    "level": 3,
    "area_code": 376,
    "post_code": 464100,
    "pid": 411500,
    "id": 1676,
    "area_id": 411503
  },
  {
    "area_name": "罗山县",
    "pinyin": "Luoshan",
    "level": 3,
    "area_code": 376,
    "post_code": 464200,
    "pid": 411500,
    "id": 1677,
    "area_id": 411521
  },
  {
    "area_name": "光山县",
    "pinyin": "Guangshan",
    "level": 3,
    "area_code": 376,
    "post_code": 465450,
    "pid": 411500,
    "id": 1678,
    "area_id": 411522
  },
  {
    "area_name": "新县",
    "pinyin": "Xinxian",
    "level": 3,
    "area_code": 376,
    "post_code": 465550,
    "pid": 411500,
    "id": 1679,
    "area_id": 411523
  },
  {
    "area_name": "商城县",
    "pinyin": "Shangcheng",
    "level": 3,
    "area_code": 376,
    "post_code": 465350,
    "pid": 411500,
    "id": 1680,
    "area_id": 411524
  },
  {
    "area_name": "固始县",
    "pinyin": "Gushi",
    "level": 3,
    "area_code": 376,
    "post_code": 465250,
    "pid": 411500,
    "id": 1681,
    "area_id": 411525
  },
  {
    "area_name": "潢川县",
    "pinyin": "Huangchuan",
    "level": 3,
    "area_code": 376,
    "post_code": 465150,
    "pid": 411500,
    "id": 1682,
    "area_id": 411526
  },
  {
    "area_name": "淮滨县",
    "pinyin": "Huaibin",
    "level": 3,
    "area_code": 376,
    "post_code": 464400,
    "pid": 411500,
    "id": 1683,
    "area_id": 411527
  },
  {
    "area_name": "息县",
    "pinyin": "Xixian",
    "level": 3,
    "area_code": 376,
    "post_code": 464300,
    "pid": 411500,
    "id": 1684,
    "area_id": 411528
  },
  {
    "area_name": "周口市",
    "pinyin": "Zhoukou",
    "level": 2,
    "area_code": 394,
    "post_code": 466000,
    "pid": 410000,
    "id": 1685,
    "area_id": 411600
  },
  {
    "area_name": "川汇区",
    "pinyin": "Chuanhui",
    "level": 3,
    "area_code": 394,
    "post_code": 466000,
    "pid": 411600,
    "id": 1686,
    "area_id": 411602
  },
  {
    "area_name": "扶沟县",
    "pinyin": "Fugou",
    "level": 3,
    "area_code": 394,
    "post_code": 461300,
    "pid": 411600,
    "id": 1687,
    "area_id": 411621
  },
  {
    "area_name": "西华县",
    "pinyin": "Xihua",
    "level": 3,
    "area_code": 394,
    "post_code": 466600,
    "pid": 411600,
    "id": 1688,
    "area_id": 411622
  },
  {
    "area_name": "商水县",
    "pinyin": "Shangshui",
    "level": 3,
    "area_code": 394,
    "post_code": 466100,
    "pid": 411600,
    "id": 1689,
    "area_id": 411623
  },
  {
    "area_name": "沈丘县",
    "pinyin": "Shenqiu",
    "level": 3,
    "area_code": 394,
    "post_code": 466300,
    "pid": 411600,
    "id": 1690,
    "area_id": 411624
  },
  {
    "area_name": "郸城县",
    "pinyin": "Dancheng",
    "level": 3,
    "area_code": 394,
    "post_code": 477150,
    "pid": 411600,
    "id": 1691,
    "area_id": 411625
  },
  {
    "area_name": "淮阳县",
    "pinyin": "Huaiyang",
    "level": 3,
    "area_code": 394,
    "post_code": 466700,
    "pid": 411600,
    "id": 1692,
    "area_id": 411626
  },
  {
    "area_name": "太康县",
    "pinyin": "Taikang",
    "level": 3,
    "area_code": 394,
    "post_code": 461400,
    "pid": 411600,
    "id": 1693,
    "area_id": 411627
  },
  {
    "area_name": "鹿邑县",
    "pinyin": "Luyi",
    "level": 3,
    "area_code": 394,
    "post_code": 477200,
    "pid": 411600,
    "id": 1694,
    "area_id": 411628
  },
  {
    "area_name": "项城市",
    "pinyin": "Xiangcheng",
    "level": 3,
    "area_code": 394,
    "post_code": 466200,
    "pid": 411600,
    "id": 1695,
    "area_id": 411681
  },
  {
    "area_name": "驻马店市",
    "pinyin": "Zhumadian",
    "level": 2,
    "area_code": 396,
    "post_code": 463000,
    "pid": 410000,
    "id": 1696,
    "area_id": 411700
  },
  {
    "area_name": "驿城区",
    "pinyin": "Yicheng",
    "level": 3,
    "area_code": 396,
    "post_code": 463000,
    "pid": 411700,
    "id": 1697,
    "area_id": 411702
  },
  {
    "area_name": "西平县",
    "pinyin": "Xiping",
    "level": 3,
    "area_code": 396,
    "post_code": 463900,
    "pid": 411700,
    "id": 1698,
    "area_id": 411721
  },
  {
    "area_name": "上蔡县",
    "pinyin": "Shangcai",
    "level": 3,
    "area_code": 396,
    "post_code": 463800,
    "pid": 411700,
    "id": 1699,
    "area_id": 411722
  },
  {
    "area_name": "平舆县",
    "pinyin": "Pingyu",
    "level": 3,
    "area_code": 396,
    "post_code": 463400,
    "pid": 411700,
    "id": 1700,
    "area_id": 411723
  },
  {
    "area_name": "正阳县",
    "pinyin": "Zhengyang",
    "level": 3,
    "area_code": 396,
    "post_code": 463600,
    "pid": 411700,
    "id": 1701,
    "area_id": 411724
  },
  {
    "area_name": "确山县",
    "pinyin": "Queshan",
    "level": 3,
    "area_code": 396,
    "post_code": 463200,
    "pid": 411700,
    "id": 1702,
    "area_id": 411725
  },
  {
    "area_name": "泌阳县",
    "pinyin": "Biyang",
    "level": 3,
    "area_code": 396,
    "post_code": 463700,
    "pid": 411700,
    "id": 1703,
    "area_id": 411726
  },
  {
    "area_name": "汝南县",
    "pinyin": "Runan",
    "level": 3,
    "area_code": 396,
    "post_code": 463300,
    "pid": 411700,
    "id": 1704,
    "area_id": 411727
  },
  {
    "area_name": "遂平县",
    "pinyin": "Suiping",
    "level": 3,
    "area_code": 396,
    "post_code": 463100,
    "pid": 411700,
    "id": 1705,
    "area_id": 411728
  },
  {
    "area_name": "新蔡县",
    "pinyin": "Xincai",
    "level": 3,
    "area_code": 396,
    "post_code": 463500,
    "pid": 411700,
    "id": 1706,
    "area_id": 411729
  },
  {
    "area_name": "直辖县级",
    "pinyin": "",
    "level": 2,
    "area_code": null,
    "post_code": null,
    "pid": 410000,
    "id": 1707,
    "area_id": 419000
  },
  {
    "area_name": "济源市",
    "pinyin": "Jiyuan",
    "level": 3,
    "area_code": 391,
    "post_code": 454650,
    "pid": 419000,
    "id": 1708,
    "area_id": 419001
  },
  {
    "area_name": "湖北省",
    "pinyin": "Hubei",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1709,
    "area_id": 420000
  },
  {
    "area_name": "武汉市",
    "pinyin": "Wuhan",
    "level": 2,
    "area_code": null,
    "post_code": 430014,
    "pid": 420000,
    "id": 1710,
    "area_id": 420100
  },
  {
    "area_name": "江岸区",
    "pinyin": "Jiang'an",
    "level": 3,
    "area_code": 27,
    "post_code": 430014,
    "pid": 420100,
    "id": 1711,
    "area_id": 420102
  },
  {
    "area_name": "江汉区",
    "pinyin": "Jianghan",
    "level": 3,
    "area_code": 27,
    "post_code": 430021,
    "pid": 420100,
    "id": 1712,
    "area_id": 420103
  },
  {
    "area_name": "硚口区",
    "pinyin": "Qiaokou",
    "level": 3,
    "area_code": 27,
    "post_code": 430033,
    "pid": 420100,
    "id": 1713,
    "area_id": 420104
  },
  {
    "area_name": "汉阳区",
    "pinyin": "Hanyang",
    "level": 3,
    "area_code": 27,
    "post_code": 430050,
    "pid": 420100,
    "id": 1714,
    "area_id": 420105
  },
  {
    "area_name": "武昌区",
    "pinyin": "Wuchang",
    "level": 3,
    "area_code": 27,
    "post_code": 430061,
    "pid": 420100,
    "id": 1715,
    "area_id": 420106
  },
  {
    "area_name": "青山区",
    "pinyin": "Qingshan",
    "level": 3,
    "area_code": 27,
    "post_code": 430080,
    "pid": 420100,
    "id": 1716,
    "area_id": 420107
  },
  {
    "area_name": "洪山区",
    "pinyin": "Hongshan",
    "level": 3,
    "area_code": 27,
    "post_code": 430070,
    "pid": 420100,
    "id": 1717,
    "area_id": 420111
  },
  {
    "area_name": "东西湖区",
    "pinyin": "Dongxihu",
    "level": 3,
    "area_code": 27,
    "post_code": 430040,
    "pid": 420100,
    "id": 1718,
    "area_id": 420112
  },
  {
    "area_name": "汉南区",
    "pinyin": "Hannan",
    "level": 3,
    "area_code": 27,
    "post_code": 430090,
    "pid": 420100,
    "id": 1719,
    "area_id": 420113
  },
  {
    "area_name": "蔡甸区",
    "pinyin": "Caidian",
    "level": 3,
    "area_code": 27,
    "post_code": 430100,
    "pid": 420100,
    "id": 1720,
    "area_id": 420114
  },
  {
    "area_name": "江夏区",
    "pinyin": "Jiangxia",
    "level": 3,
    "area_code": 27,
    "post_code": 430200,
    "pid": 420100,
    "id": 1721,
    "area_id": 420115
  },
  {
    "area_name": "黄陂区",
    "pinyin": "Huangpi",
    "level": 3,
    "area_code": 27,
    "post_code": 432200,
    "pid": 420100,
    "id": 1722,
    "area_id": 420116
  },
  {
    "area_name": "新洲区",
    "pinyin": "Xinzhou",
    "level": 3,
    "area_code": 27,
    "post_code": 431400,
    "pid": 420100,
    "id": 1723,
    "area_id": 420117
  },
  {
    "area_name": "黄石市",
    "pinyin": "Huangshi",
    "level": 2,
    "area_code": 714,
    "post_code": 435003,
    "pid": 420000,
    "id": 1724,
    "area_id": 420200
  },
  {
    "area_name": "黄石港区",
    "pinyin": "Huangshigang",
    "level": 3,
    "area_code": 714,
    "post_code": 435000,
    "pid": 420200,
    "id": 1725,
    "area_id": 420202
  },
  {
    "area_name": "西塞山区",
    "pinyin": "Xisaishan",
    "level": 3,
    "area_code": 714,
    "post_code": 435001,
    "pid": 420200,
    "id": 1726,
    "area_id": 420203
  },
  {
    "area_name": "下陆区",
    "pinyin": "Xialu",
    "level": 3,
    "area_code": 714,
    "post_code": 435005,
    "pid": 420200,
    "id": 1727,
    "area_id": 420204
  },
  {
    "area_name": "铁山区",
    "pinyin": "Tieshan",
    "level": 3,
    "area_code": 714,
    "post_code": 435006,
    "pid": 420200,
    "id": 1728,
    "area_id": 420205
  },
  {
    "area_name": "阳新县",
    "pinyin": "Yangxin",
    "level": 3,
    "area_code": 714,
    "post_code": 435200,
    "pid": 420200,
    "id": 1729,
    "area_id": 420222
  },
  {
    "area_name": "大冶市",
    "pinyin": "Daye",
    "level": 3,
    "area_code": 714,
    "post_code": 435100,
    "pid": 420200,
    "id": 1730,
    "area_id": 420281
  },
  {
    "area_name": "十堰市",
    "pinyin": "Shiyan",
    "level": 2,
    "area_code": 719,
    "post_code": 442000,
    "pid": 420000,
    "id": 1731,
    "area_id": 420300
  },
  {
    "area_name": "茅箭区",
    "pinyin": "Maojian",
    "level": 3,
    "area_code": 719,
    "post_code": 442012,
    "pid": 420300,
    "id": 1732,
    "area_id": 420302
  },
  {
    "area_name": "张湾区",
    "pinyin": "Zhangwan",
    "level": 3,
    "area_code": 719,
    "post_code": 442001,
    "pid": 420300,
    "id": 1733,
    "area_id": 420303
  },
  {
    "area_name": "郧阳区",
    "pinyin": "Yunyang",
    "level": 3,
    "area_code": 719,
    "post_code": 442500,
    "pid": 420300,
    "id": 1734,
    "area_id": 420304
  },
  {
    "area_name": "郧西县",
    "pinyin": "Yunxi",
    "level": 3,
    "area_code": 719,
    "post_code": 442600,
    "pid": 420300,
    "id": 1735,
    "area_id": 420322
  },
  {
    "area_name": "竹山县",
    "pinyin": "Zhushan",
    "level": 3,
    "area_code": 719,
    "post_code": 442200,
    "pid": 420300,
    "id": 1736,
    "area_id": 420323
  },
  {
    "area_name": "竹溪县",
    "pinyin": "Zhuxi",
    "level": 3,
    "area_code": 719,
    "post_code": 442300,
    "pid": 420300,
    "id": 1737,
    "area_id": 420324
  },
  {
    "area_name": "房县",
    "pinyin": "Fangxian",
    "level": 3,
    "area_code": 719,
    "post_code": 442100,
    "pid": 420300,
    "id": 1738,
    "area_id": 420325
  },
  {
    "area_name": "丹江口市",
    "pinyin": "Danjiangkou",
    "level": 3,
    "area_code": 719,
    "post_code": 442700,
    "pid": 420300,
    "id": 1739,
    "area_id": 420381
  },
  {
    "area_name": "宜昌市",
    "pinyin": "Yichang",
    "level": 2,
    "area_code": 717,
    "post_code": 443000,
    "pid": 420000,
    "id": 1740,
    "area_id": 420500
  },
  {
    "area_name": "西陵区",
    "pinyin": "Xiling",
    "level": 3,
    "area_code": 717,
    "post_code": 443000,
    "pid": 420500,
    "id": 1741,
    "area_id": 420502
  },
  {
    "area_name": "伍家岗区",
    "pinyin": "Wujiagang",
    "level": 3,
    "area_code": 717,
    "post_code": 443001,
    "pid": 420500,
    "id": 1742,
    "area_id": 420503
  },
  {
    "area_name": "点军区",
    "pinyin": "Dianjun",
    "level": 3,
    "area_code": 717,
    "post_code": 443006,
    "pid": 420500,
    "id": 1743,
    "area_id": 420504
  },
  {
    "area_name": "猇亭区",
    "pinyin": "Xiaoting",
    "level": 3,
    "area_code": 717,
    "post_code": 443007,
    "pid": 420500,
    "id": 1744,
    "area_id": 420505
  },
  {
    "area_name": "夷陵区",
    "pinyin": "Yiling",
    "level": 3,
    "area_code": 717,
    "post_code": 443100,
    "pid": 420500,
    "id": 1745,
    "area_id": 420506
  },
  {
    "area_name": "远安县",
    "pinyin": "Yuan'an",
    "level": 3,
    "area_code": 717,
    "post_code": 444200,
    "pid": 420500,
    "id": 1746,
    "area_id": 420525
  },
  {
    "area_name": "兴山县",
    "pinyin": "Xingshan",
    "level": 3,
    "area_code": 717,
    "post_code": 443711,
    "pid": 420500,
    "id": 1747,
    "area_id": 420526
  },
  {
    "area_name": "秭归县",
    "pinyin": "Zigui",
    "level": 3,
    "area_code": 717,
    "post_code": 443600,
    "pid": 420500,
    "id": 1748,
    "area_id": 420527
  },
  {
    "area_name": "长阳土家族自治县",
    "pinyin": "Changyang",
    "level": 3,
    "area_code": 717,
    "post_code": 443500,
    "pid": 420500,
    "id": 1749,
    "area_id": 420528
  },
  {
    "area_name": "五峰土家族自治县",
    "pinyin": "Wufeng",
    "level": 3,
    "area_code": 717,
    "post_code": 443413,
    "pid": 420500,
    "id": 1750,
    "area_id": 420529
  },
  {
    "area_name": "宜都市",
    "pinyin": "Yidu",
    "level": 3,
    "area_code": 717,
    "post_code": 443300,
    "pid": 420500,
    "id": 1751,
    "area_id": 420581
  },
  {
    "area_name": "当阳市",
    "pinyin": "Dangyang",
    "level": 3,
    "area_code": 717,
    "post_code": 444100,
    "pid": 420500,
    "id": 1752,
    "area_id": 420582
  },
  {
    "area_name": "枝江市",
    "pinyin": "Zhijiang",
    "level": 3,
    "area_code": 717,
    "post_code": 443200,
    "pid": 420500,
    "id": 1753,
    "area_id": 420583
  },
  {
    "area_name": "襄阳市",
    "pinyin": "Xiangyang",
    "level": 2,
    "area_code": 710,
    "post_code": 441021,
    "pid": 420000,
    "id": 1754,
    "area_id": 420600
  },
  {
    "area_name": "襄城区",
    "pinyin": "Xiangcheng",
    "level": 3,
    "area_code": 710,
    "post_code": 441021,
    "pid": 420600,
    "id": 1755,
    "area_id": 420602
  },
  {
    "area_name": "樊城区",
    "pinyin": "Fancheng",
    "level": 3,
    "area_code": 710,
    "post_code": 441001,
    "pid": 420600,
    "id": 1756,
    "area_id": 420606
  },
  {
    "area_name": "襄州区",
    "pinyin": "Xiangzhou",
    "level": 3,
    "area_code": 710,
    "post_code": 441100,
    "pid": 420600,
    "id": 1757,
    "area_id": 420607
  },
  {
    "area_name": "南漳县",
    "pinyin": "Nanzhang",
    "level": 3,
    "area_code": 710,
    "post_code": 441500,
    "pid": 420600,
    "id": 1758,
    "area_id": 420624
  },
  {
    "area_name": "谷城县",
    "pinyin": "Gucheng",
    "level": 3,
    "area_code": 710,
    "post_code": 441700,
    "pid": 420600,
    "id": 1759,
    "area_id": 420625
  },
  {
    "area_name": "保康县",
    "pinyin": "Baokang",
    "level": 3,
    "area_code": 710,
    "post_code": 441600,
    "pid": 420600,
    "id": 1760,
    "area_id": 420626
  },
  {
    "area_name": "老河口市",
    "pinyin": "Laohekou",
    "level": 3,
    "area_code": 710,
    "post_code": 441800,
    "pid": 420600,
    "id": 1761,
    "area_id": 420682
  },
  {
    "area_name": "枣阳市",
    "pinyin": "Zaoyang",
    "level": 3,
    "area_code": 710,
    "post_code": 441200,
    "pid": 420600,
    "id": 1762,
    "area_id": 420683
  },
  {
    "area_name": "宜城市",
    "pinyin": "Yicheng",
    "level": 3,
    "area_code": 710,
    "post_code": 441400,
    "pid": 420600,
    "id": 1763,
    "area_id": 420684
  },
  {
    "area_name": "鄂州市",
    "pinyin": "Ezhou",
    "level": 2,
    "area_code": 711,
    "post_code": 436000,
    "pid": 420000,
    "id": 1764,
    "area_id": 420700
  },
  {
    "area_name": "梁子湖区",
    "pinyin": "Liangzihu",
    "level": 3,
    "area_code": 711,
    "post_code": 436064,
    "pid": 420700,
    "id": 1765,
    "area_id": 420702
  },
  {
    "area_name": "华容区",
    "pinyin": "Huarong",
    "level": 3,
    "area_code": 711,
    "post_code": 436030,
    "pid": 420700,
    "id": 1766,
    "area_id": 420703
  },
  {
    "area_name": "鄂城区",
    "pinyin": "Echeng",
    "level": 3,
    "area_code": 711,
    "post_code": 436000,
    "pid": 420700,
    "id": 1767,
    "area_id": 420704
  },
  {
    "area_name": "荆门市",
    "pinyin": "Jingmen",
    "level": 2,
    "area_code": 724,
    "post_code": 448000,
    "pid": 420000,
    "id": 1768,
    "area_id": 420800
  },
  {
    "area_name": "东宝区",
    "pinyin": "Dongbao",
    "level": 3,
    "area_code": 724,
    "post_code": 448004,
    "pid": 420800,
    "id": 1769,
    "area_id": 420802
  },
  {
    "area_name": "掇刀区",
    "pinyin": "Duodao",
    "level": 3,
    "area_code": 724,
    "post_code": 448124,
    "pid": 420800,
    "id": 1770,
    "area_id": 420804
  },
  {
    "area_name": "京山县",
    "pinyin": "Jingshan",
    "level": 3,
    "area_code": 724,
    "post_code": 431800,
    "pid": 420800,
    "id": 1771,
    "area_id": 420821
  },
  {
    "area_name": "沙洋县",
    "pinyin": "Shayang",
    "level": 3,
    "area_code": 724,
    "post_code": 448200,
    "pid": 420800,
    "id": 1772,
    "area_id": 420822
  },
  {
    "area_name": "钟祥市",
    "pinyin": "Zhongxiang",
    "level": 3,
    "area_code": 724,
    "post_code": 431900,
    "pid": 420800,
    "id": 1773,
    "area_id": 420881
  },
  {
    "area_name": "孝感市",
    "pinyin": "Xiaogan",
    "level": 2,
    "area_code": 712,
    "post_code": 432100,
    "pid": 420000,
    "id": 1774,
    "area_id": 420900
  },
  {
    "area_name": "孝南区",
    "pinyin": "Xiaonan",
    "level": 3,
    "area_code": 712,
    "post_code": 432100,
    "pid": 420900,
    "id": 1775,
    "area_id": 420902
  },
  {
    "area_name": "孝昌县",
    "pinyin": "Xiaochang",
    "level": 3,
    "area_code": 712,
    "post_code": 432900,
    "pid": 420900,
    "id": 1776,
    "area_id": 420921
  },
  {
    "area_name": "大悟县",
    "pinyin": "Dawu",
    "level": 3,
    "area_code": 712,
    "post_code": 432800,
    "pid": 420900,
    "id": 1777,
    "area_id": 420922
  },
  {
    "area_name": "云梦县",
    "pinyin": "Yunmeng",
    "level": 3,
    "area_code": 712,
    "post_code": 432500,
    "pid": 420900,
    "id": 1778,
    "area_id": 420923
  },
  {
    "area_name": "应城市",
    "pinyin": "Yingcheng",
    "level": 3,
    "area_code": 712,
    "post_code": 432400,
    "pid": 420900,
    "id": 1779,
    "area_id": 420981
  },
  {
    "area_name": "安陆市",
    "pinyin": "Anlu",
    "level": 3,
    "area_code": 712,
    "post_code": 432600,
    "pid": 420900,
    "id": 1780,
    "area_id": 420982
  },
  {
    "area_name": "汉川市",
    "pinyin": "Hanchuan",
    "level": 3,
    "area_code": 712,
    "post_code": 432300,
    "pid": 420900,
    "id": 1781,
    "area_id": 420984
  },
  {
    "area_name": "荆州市",
    "pinyin": "Jingzhou",
    "level": 2,
    "area_code": 716,
    "post_code": 434000,
    "pid": 420000,
    "id": 1782,
    "area_id": 421000
  },
  {
    "area_name": "沙市区",
    "pinyin": "Shashi",
    "level": 3,
    "area_code": 716,
    "post_code": 434000,
    "pid": 421000,
    "id": 1783,
    "area_id": 421002
  },
  {
    "area_name": "荆州区",
    "pinyin": "Jingzhou",
    "level": 3,
    "area_code": 716,
    "post_code": 434020,
    "pid": 421000,
    "id": 1784,
    "area_id": 421003
  },
  {
    "area_name": "公安县",
    "pinyin": "Gong'an",
    "level": 3,
    "area_code": 716,
    "post_code": 434300,
    "pid": 421000,
    "id": 1785,
    "area_id": 421022
  },
  {
    "area_name": "监利县",
    "pinyin": "Jianli",
    "level": 3,
    "area_code": 716,
    "post_code": 433300,
    "pid": 421000,
    "id": 1786,
    "area_id": 421023
  },
  {
    "area_name": "江陵县",
    "pinyin": "Jiangling",
    "level": 3,
    "area_code": 716,
    "post_code": 434101,
    "pid": 421000,
    "id": 1787,
    "area_id": 421024
  },
  {
    "area_name": "石首市",
    "pinyin": "Shishou",
    "level": 3,
    "area_code": 716,
    "post_code": 434400,
    "pid": 421000,
    "id": 1788,
    "area_id": 421081
  },
  {
    "area_name": "洪湖市",
    "pinyin": "Honghu",
    "level": 3,
    "area_code": 716,
    "post_code": 433200,
    "pid": 421000,
    "id": 1789,
    "area_id": 421083
  },
  {
    "area_name": "松滋市",
    "pinyin": "Songzi",
    "level": 3,
    "area_code": 716,
    "post_code": 434200,
    "pid": 421000,
    "id": 1790,
    "area_id": 421087
  },
  {
    "area_name": "黄冈市",
    "pinyin": "Huanggang",
    "level": 2,
    "area_code": 713,
    "post_code": 438000,
    "pid": 420000,
    "id": 1791,
    "area_id": 421100
  },
  {
    "area_name": "黄州区",
    "pinyin": "Huangzhou",
    "level": 3,
    "area_code": 713,
    "post_code": 438000,
    "pid": 421100,
    "id": 1792,
    "area_id": 421102
  },
  {
    "area_name": "团风县",
    "pinyin": "Tuanfeng",
    "level": 3,
    "area_code": 713,
    "post_code": 438800,
    "pid": 421100,
    "id": 1793,
    "area_id": 421121
  },
  {
    "area_name": "红安县",
    "pinyin": "Hong'an",
    "level": 3,
    "area_code": 713,
    "post_code": 438401,
    "pid": 421100,
    "id": 1794,
    "area_id": 421122
  },
  {
    "area_name": "罗田县",
    "pinyin": "Luotian",
    "level": 3,
    "area_code": 713,
    "post_code": 438600,
    "pid": 421100,
    "id": 1795,
    "area_id": 421123
  },
  {
    "area_name": "英山县",
    "pinyin": "Yingshan",
    "level": 3,
    "area_code": 713,
    "post_code": 438700,
    "pid": 421100,
    "id": 1796,
    "area_id": 421124
  },
  {
    "area_name": "浠水县",
    "pinyin": "Xishui",
    "level": 3,
    "area_code": 713,
    "post_code": 438200,
    "pid": 421100,
    "id": 1797,
    "area_id": 421125
  },
  {
    "area_name": "蕲春县",
    "pinyin": "Qichun",
    "level": 3,
    "area_code": 713,
    "post_code": 435300,
    "pid": 421100,
    "id": 1798,
    "area_id": 421126
  },
  {
    "area_name": "黄梅县",
    "pinyin": "Huangmei",
    "level": 3,
    "area_code": 713,
    "post_code": 435500,
    "pid": 421100,
    "id": 1799,
    "area_id": 421127
  },
  {
    "area_name": "麻城市",
    "pinyin": "Macheng",
    "level": 3,
    "area_code": 713,
    "post_code": 438300,
    "pid": 421100,
    "id": 1800,
    "area_id": 421181
  },
  {
    "area_name": "武穴市",
    "pinyin": "Wuxue",
    "level": 3,
    "area_code": 713,
    "post_code": 435400,
    "pid": 421100,
    "id": 1801,
    "area_id": 421182
  },
  {
    "area_name": "咸宁市",
    "pinyin": "Xianning",
    "level": 2,
    "area_code": 715,
    "post_code": 437000,
    "pid": 420000,
    "id": 1802,
    "area_id": 421200
  },
  {
    "area_name": "咸安区",
    "pinyin": "Xian'an",
    "level": 3,
    "area_code": 715,
    "post_code": 437000,
    "pid": 421200,
    "id": 1803,
    "area_id": 421202
  },
  {
    "area_name": "嘉鱼县",
    "pinyin": "Jiayu",
    "level": 3,
    "area_code": 715,
    "post_code": 437200,
    "pid": 421200,
    "id": 1804,
    "area_id": 421221
  },
  {
    "area_name": "通城县",
    "pinyin": "Tongcheng",
    "level": 3,
    "area_code": 715,
    "post_code": 437400,
    "pid": 421200,
    "id": 1805,
    "area_id": 421222
  },
  {
    "area_name": "崇阳县",
    "pinyin": "Chongyang",
    "level": 3,
    "area_code": 715,
    "post_code": 437500,
    "pid": 421200,
    "id": 1806,
    "area_id": 421223
  },
  {
    "area_name": "通山县",
    "pinyin": "Tongshan",
    "level": 3,
    "area_code": 715,
    "post_code": 437600,
    "pid": 421200,
    "id": 1807,
    "area_id": 421224
  },
  {
    "area_name": "赤壁市",
    "pinyin": "Chibi",
    "level": 3,
    "area_code": 715,
    "post_code": 437300,
    "pid": 421200,
    "id": 1808,
    "area_id": 421281
  },
  {
    "area_name": "随州市",
    "pinyin": "Suizhou",
    "level": 2,
    "area_code": 722,
    "post_code": 441300,
    "pid": 420000,
    "id": 1809,
    "area_id": 421300
  },
  {
    "area_name": "曾都区",
    "pinyin": "Zengdu",
    "level": 3,
    "area_code": 722,
    "post_code": 441300,
    "pid": 421300,
    "id": 1810,
    "area_id": 421303
  },
  {
    "area_name": "随县",
    "pinyin": "Suixian",
    "level": 3,
    "area_code": 722,
    "post_code": 441309,
    "pid": 421300,
    "id": 1811,
    "area_id": 421321
  },
  {
    "area_name": "广水市",
    "pinyin": "Guangshui",
    "level": 3,
    "area_code": 722,
    "post_code": 432700,
    "pid": 421300,
    "id": 1812,
    "area_id": 421381
  },
  {
    "area_name": "恩施土家族苗族自治州",
    "pinyin": "Enshi",
    "level": 2,
    "area_code": 718,
    "post_code": 445000,
    "pid": 420000,
    "id": 1813,
    "area_id": 422800
  },
  {
    "area_name": "恩施市",
    "pinyin": "Enshi",
    "level": 3,
    "area_code": 718,
    "post_code": 445000,
    "pid": 422800,
    "id": 1814,
    "area_id": 422801
  },
  {
    "area_name": "利川市",
    "pinyin": "Lichuan",
    "level": 3,
    "area_code": 718,
    "post_code": 445400,
    "pid": 422800,
    "id": 1815,
    "area_id": 422802
  },
  {
    "area_name": "建始县",
    "pinyin": "Jianshi",
    "level": 3,
    "area_code": 718,
    "post_code": 445300,
    "pid": 422800,
    "id": 1816,
    "area_id": 422822
  },
  {
    "area_name": "巴东县",
    "pinyin": "Badong",
    "level": 3,
    "area_code": 718,
    "post_code": 444300,
    "pid": 422800,
    "id": 1817,
    "area_id": 422823
  },
  {
    "area_name": "宣恩县",
    "pinyin": "Xuanen",
    "level": 3,
    "area_code": 718,
    "post_code": 445500,
    "pid": 422800,
    "id": 1818,
    "area_id": 422825
  },
  {
    "area_name": "咸丰县",
    "pinyin": "Xianfeng",
    "level": 3,
    "area_code": 718,
    "post_code": 445600,
    "pid": 422800,
    "id": 1819,
    "area_id": 422826
  },
  {
    "area_name": "来凤县",
    "pinyin": "Laifeng",
    "level": 3,
    "area_code": 718,
    "post_code": 445700,
    "pid": 422800,
    "id": 1820,
    "area_id": 422827
  },
  {
    "area_name": "鹤峰县",
    "pinyin": "Hefeng",
    "level": 3,
    "area_code": 718,
    "post_code": 445800,
    "pid": 422800,
    "id": 1821,
    "area_id": 422828
  },
  {
    "area_name": "直辖县级",
    "pinyin": "",
    "level": 2,
    "area_code": null,
    "post_code": null,
    "pid": 420000,
    "id": 1822,
    "area_id": 429000
  },
  {
    "area_name": "仙桃市",
    "pinyin": "Xiantao",
    "level": 3,
    "area_code": 728,
    "post_code": 433000,
    "pid": 429000,
    "id": 1823,
    "area_id": 429004
  },
  {
    "area_name": "潜江市",
    "pinyin": "Qianjiang",
    "level": 3,
    "area_code": 728,
    "post_code": 433100,
    "pid": 429000,
    "id": 1824,
    "area_id": 429005
  },
  {
    "area_name": "天门市",
    "pinyin": "Tianmen",
    "level": 3,
    "area_code": 728,
    "post_code": 431700,
    "pid": 429000,
    "id": 1825,
    "area_id": 429006
  },
  {
    "area_name": "神农架林区",
    "pinyin": "Shennongjia",
    "level": 3,
    "area_code": 719,
    "post_code": 442400,
    "pid": 429000,
    "id": 1826,
    "area_id": 429021
  },
  {
    "area_name": "湖南省",
    "pinyin": "Hunan",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1827,
    "area_id": 430000
  },
  {
    "area_name": "长沙市",
    "pinyin": "Changsha",
    "level": 2,
    "area_code": 731,
    "post_code": 410005,
    "pid": 430000,
    "id": 1828,
    "area_id": 430100
  },
  {
    "area_name": "芙蓉区",
    "pinyin": "Furong",
    "level": 3,
    "area_code": 731,
    "post_code": 410011,
    "pid": 430100,
    "id": 1829,
    "area_id": 430102
  },
  {
    "area_name": "天心区",
    "pinyin": "Tianxin",
    "level": 3,
    "area_code": 731,
    "post_code": 410004,
    "pid": 430100,
    "id": 1830,
    "area_id": 430103
  },
  {
    "area_name": "岳麓区",
    "pinyin": "Yuelu",
    "level": 3,
    "area_code": 731,
    "post_code": 410013,
    "pid": 430100,
    "id": 1831,
    "area_id": 430104
  },
  {
    "area_name": "开福区",
    "pinyin": "Kaifu",
    "level": 3,
    "area_code": 731,
    "post_code": 410008,
    "pid": 430100,
    "id": 1832,
    "area_id": 430105
  },
  {
    "area_name": "雨花区",
    "pinyin": "Yuhua",
    "level": 3,
    "area_code": 731,
    "post_code": 410011,
    "pid": 430100,
    "id": 1833,
    "area_id": 430111
  },
  {
    "area_name": "望城区",
    "pinyin": "Wangcheng",
    "level": 3,
    "area_code": 731,
    "post_code": 410200,
    "pid": 430100,
    "id": 1834,
    "area_id": 430112
  },
  {
    "area_name": "长沙县",
    "pinyin": "Changsha",
    "level": 3,
    "area_code": 731,
    "post_code": 410100,
    "pid": 430100,
    "id": 1835,
    "area_id": 430121
  },
  {
    "area_name": "宁乡县",
    "pinyin": "Ningxiang",
    "level": 3,
    "area_code": 731,
    "post_code": 410600,
    "pid": 430100,
    "id": 1836,
    "area_id": 430124
  },
  {
    "area_name": "浏阳市",
    "pinyin": "Liuyang",
    "level": 3,
    "area_code": 731,
    "post_code": 410300,
    "pid": 430100,
    "id": 1837,
    "area_id": 430181
  },
  {
    "area_name": "株洲市",
    "pinyin": "Zhuzhou",
    "level": 2,
    "area_code": 731,
    "post_code": 412000,
    "pid": 430000,
    "id": 1838,
    "area_id": 430200
  },
  {
    "area_name": "荷塘区",
    "pinyin": "Hetang",
    "level": 3,
    "area_code": 731,
    "post_code": 412000,
    "pid": 430200,
    "id": 1839,
    "area_id": 430202
  },
  {
    "area_name": "芦淞区",
    "pinyin": "Lusong",
    "level": 3,
    "area_code": 731,
    "post_code": 412000,
    "pid": 430200,
    "id": 1840,
    "area_id": 430203
  },
  {
    "area_name": "石峰区",
    "pinyin": "Shifeng",
    "level": 3,
    "area_code": 731,
    "post_code": 412005,
    "pid": 430200,
    "id": 1841,
    "area_id": 430204
  },
  {
    "area_name": "天元区",
    "pinyin": "Tianyuan",
    "level": 3,
    "area_code": 731,
    "post_code": 412007,
    "pid": 430200,
    "id": 1842,
    "area_id": 430211
  },
  {
    "area_name": "株洲县",
    "pinyin": "Zhuzhou",
    "level": 3,
    "area_code": 731,
    "post_code": 412100,
    "pid": 430200,
    "id": 1843,
    "area_id": 430221
  },
  {
    "area_name": "攸县",
    "pinyin": "Youxian",
    "level": 3,
    "area_code": 731,
    "post_code": 412300,
    "pid": 430200,
    "id": 1844,
    "area_id": 430223
  },
  {
    "area_name": "茶陵县",
    "pinyin": "Chaling",
    "level": 3,
    "area_code": 731,
    "post_code": 412400,
    "pid": 430200,
    "id": 1845,
    "area_id": 430224
  },
  {
    "area_name": "炎陵县",
    "pinyin": "Yanling",
    "level": 3,
    "area_code": 731,
    "post_code": 412500,
    "pid": 430200,
    "id": 1846,
    "area_id": 430225
  },
  {
    "area_name": "醴陵市",
    "pinyin": "Liling",
    "level": 3,
    "area_code": 731,
    "post_code": 412200,
    "pid": 430200,
    "id": 1847,
    "area_id": 430281
  },
  {
    "area_name": "湘潭市",
    "pinyin": "Xiangtan",
    "level": 2,
    "area_code": 731,
    "post_code": 411100,
    "pid": 430000,
    "id": 1848,
    "area_id": 430300
  },
  {
    "area_name": "雨湖区",
    "pinyin": "Yuhu",
    "level": 3,
    "area_code": 731,
    "post_code": 411100,
    "pid": 430300,
    "id": 1849,
    "area_id": 430302
  },
  {
    "area_name": "岳塘区",
    "pinyin": "Yuetang",
    "level": 3,
    "area_code": 731,
    "post_code": 411101,
    "pid": 430300,
    "id": 1850,
    "area_id": 430304
  },
  {
    "area_name": "湘潭县",
    "pinyin": "Xiangtan",
    "level": 3,
    "area_code": 731,
    "post_code": 411228,
    "pid": 430300,
    "id": 1851,
    "area_id": 430321
  },
  {
    "area_name": "湘乡市",
    "pinyin": "Xiangxiang",
    "level": 3,
    "area_code": 731,
    "post_code": 411400,
    "pid": 430300,
    "id": 1852,
    "area_id": 430381
  },
  {
    "area_name": "韶山市",
    "pinyin": "Shaoshan",
    "level": 3,
    "area_code": 731,
    "post_code": 411300,
    "pid": 430300,
    "id": 1853,
    "area_id": 430382
  },
  {
    "area_name": "衡阳市",
    "pinyin": "Hengyang",
    "level": 2,
    "area_code": 734,
    "post_code": 421001,
    "pid": 430000,
    "id": 1854,
    "area_id": 430400
  },
  {
    "area_name": "珠晖区",
    "pinyin": "Zhuhui",
    "level": 3,
    "area_code": 734,
    "post_code": 421002,
    "pid": 430400,
    "id": 1855,
    "area_id": 430405
  },
  {
    "area_name": "雁峰区",
    "pinyin": "Yanfeng",
    "level": 3,
    "area_code": 734,
    "post_code": 421001,
    "pid": 430400,
    "id": 1856,
    "area_id": 430406
  },
  {
    "area_name": "石鼓区",
    "pinyin": "Shigu",
    "level": 3,
    "area_code": 734,
    "post_code": 421005,
    "pid": 430400,
    "id": 1857,
    "area_id": 430407
  },
  {
    "area_name": "蒸湘区",
    "pinyin": "Zhengxiang",
    "level": 3,
    "area_code": 734,
    "post_code": 421001,
    "pid": 430400,
    "id": 1858,
    "area_id": 430408
  },
  {
    "area_name": "南岳区",
    "pinyin": "Nanyue",
    "level": 3,
    "area_code": 734,
    "post_code": 421900,
    "pid": 430400,
    "id": 1859,
    "area_id": 430412
  },
  {
    "area_name": "衡阳县",
    "pinyin": "Hengyang",
    "level": 3,
    "area_code": 734,
    "post_code": 421200,
    "pid": 430400,
    "id": 1860,
    "area_id": 430421
  },
  {
    "area_name": "衡南县",
    "pinyin": "Hengnan",
    "level": 3,
    "area_code": 734,
    "post_code": 421131,
    "pid": 430400,
    "id": 1861,
    "area_id": 430422
  },
  {
    "area_name": "衡山县",
    "pinyin": "Hengshan",
    "level": 3,
    "area_code": 734,
    "post_code": 421300,
    "pid": 430400,
    "id": 1862,
    "area_id": 430423
  },
  {
    "area_name": "衡东县",
    "pinyin": "Hengdong",
    "level": 3,
    "area_code": 734,
    "post_code": 421400,
    "pid": 430400,
    "id": 1863,
    "area_id": 430424
  },
  {
    "area_name": "祁东县",
    "pinyin": "Qidong",
    "level": 3,
    "area_code": 734,
    "post_code": 421600,
    "pid": 430400,
    "id": 1864,
    "area_id": 430426
  },
  {
    "area_name": "耒阳市",
    "pinyin": "Leiyang",
    "level": 3,
    "area_code": 734,
    "post_code": 421800,
    "pid": 430400,
    "id": 1865,
    "area_id": 430481
  },
  {
    "area_name": "常宁市",
    "pinyin": "Changning",
    "level": 3,
    "area_code": 734,
    "post_code": 421500,
    "pid": 430400,
    "id": 1866,
    "area_id": 430482
  },
  {
    "area_name": "邵阳市",
    "pinyin": "Shaoyang",
    "level": 2,
    "area_code": 739,
    "post_code": 422000,
    "pid": 430000,
    "id": 1867,
    "area_id": 430500
  },
  {
    "area_name": "双清区",
    "pinyin": "Shuangqing",
    "level": 3,
    "area_code": 739,
    "post_code": 422001,
    "pid": 430500,
    "id": 1868,
    "area_id": 430502
  },
  {
    "area_name": "大祥区",
    "pinyin": "Daxiang",
    "level": 3,
    "area_code": 739,
    "post_code": 422000,
    "pid": 430500,
    "id": 1869,
    "area_id": 430503
  },
  {
    "area_name": "北塔区",
    "pinyin": "Beita",
    "level": 3,
    "area_code": 739,
    "post_code": 422007,
    "pid": 430500,
    "id": 1870,
    "area_id": 430511
  },
  {
    "area_name": "邵东县",
    "pinyin": "Shaodong",
    "level": 3,
    "area_code": 739,
    "post_code": 422800,
    "pid": 430500,
    "id": 1871,
    "area_id": 430521
  },
  {
    "area_name": "新邵县",
    "pinyin": "Xinshao",
    "level": 3,
    "area_code": 739,
    "post_code": 422900,
    "pid": 430500,
    "id": 1872,
    "area_id": 430522
  },
  {
    "area_name": "邵阳县",
    "pinyin": "Shaoyang",
    "level": 3,
    "area_code": 739,
    "post_code": 422100,
    "pid": 430500,
    "id": 1873,
    "area_id": 430523
  },
  {
    "area_name": "隆回县",
    "pinyin": "Longhui",
    "level": 3,
    "area_code": 739,
    "post_code": 422200,
    "pid": 430500,
    "id": 1874,
    "area_id": 430524
  },
  {
    "area_name": "洞口县",
    "pinyin": "Dongkou",
    "level": 3,
    "area_code": 739,
    "post_code": 422300,
    "pid": 430500,
    "id": 1875,
    "area_id": 430525
  },
  {
    "area_name": "绥宁县",
    "pinyin": "Suining",
    "level": 3,
    "area_code": 739,
    "post_code": 422600,
    "pid": 430500,
    "id": 1876,
    "area_id": 430527
  },
  {
    "area_name": "新宁县",
    "pinyin": "Xinning",
    "level": 3,
    "area_code": 739,
    "post_code": 422700,
    "pid": 430500,
    "id": 1877,
    "area_id": 430528
  },
  {
    "area_name": "城步苗族自治县",
    "pinyin": "Chengbu",
    "level": 3,
    "area_code": 739,
    "post_code": 422500,
    "pid": 430500,
    "id": 1878,
    "area_id": 430529
  },
  {
    "area_name": "武冈市",
    "pinyin": "Wugang",
    "level": 3,
    "area_code": 739,
    "post_code": 422400,
    "pid": 430500,
    "id": 1879,
    "area_id": 430581
  },
  {
    "area_name": "岳阳市",
    "pinyin": "Yueyang",
    "level": 2,
    "area_code": 730,
    "post_code": 414000,
    "pid": 430000,
    "id": 1880,
    "area_id": 430600
  },
  {
    "area_name": "岳阳楼区",
    "pinyin": "Yueyanglou",
    "level": 3,
    "area_code": 730,
    "post_code": 414000,
    "pid": 430600,
    "id": 1881,
    "area_id": 430602
  },
  {
    "area_name": "云溪区",
    "pinyin": "Yunxi",
    "level": 3,
    "area_code": 730,
    "post_code": 414009,
    "pid": 430600,
    "id": 1882,
    "area_id": 430603
  },
  {
    "area_name": "君山区",
    "pinyin": "Junshan",
    "level": 3,
    "area_code": 730,
    "post_code": 414005,
    "pid": 430600,
    "id": 1883,
    "area_id": 430611
  },
  {
    "area_name": "岳阳县",
    "pinyin": "Yueyang",
    "level": 3,
    "area_code": 730,
    "post_code": 414100,
    "pid": 430600,
    "id": 1884,
    "area_id": 430621
  },
  {
    "area_name": "华容县",
    "pinyin": "Huarong",
    "level": 3,
    "area_code": 730,
    "post_code": 414200,
    "pid": 430600,
    "id": 1885,
    "area_id": 430623
  },
  {
    "area_name": "湘阴县",
    "pinyin": "Xiangyin",
    "level": 3,
    "area_code": 730,
    "post_code": 414600,
    "pid": 430600,
    "id": 1886,
    "area_id": 430624
  },
  {
    "area_name": "平江县",
    "pinyin": "Pingjiang",
    "level": 3,
    "area_code": 730,
    "post_code": 414500,
    "pid": 430600,
    "id": 1887,
    "area_id": 430626
  },
  {
    "area_name": "汨罗市",
    "pinyin": "Miluo",
    "level": 3,
    "area_code": 730,
    "post_code": 414400,
    "pid": 430600,
    "id": 1888,
    "area_id": 430681
  },
  {
    "area_name": "临湘市",
    "pinyin": "Linxiang",
    "level": 3,
    "area_code": 730,
    "post_code": 414300,
    "pid": 430600,
    "id": 1889,
    "area_id": 430682
  },
  {
    "area_name": "常德市",
    "pinyin": "Changde",
    "level": 2,
    "area_code": 736,
    "post_code": 415000,
    "pid": 430000,
    "id": 1890,
    "area_id": 430700
  },
  {
    "area_name": "武陵区",
    "pinyin": "Wuling",
    "level": 3,
    "area_code": 736,
    "post_code": 415000,
    "pid": 430700,
    "id": 1891,
    "area_id": 430702
  },
  {
    "area_name": "鼎城区",
    "pinyin": "Dingcheng",
    "level": 3,
    "area_code": 736,
    "post_code": 415101,
    "pid": 430700,
    "id": 1892,
    "area_id": 430703
  },
  {
    "area_name": "安乡县",
    "pinyin": "Anxiang",
    "level": 3,
    "area_code": 736,
    "post_code": 415600,
    "pid": 430700,
    "id": 1893,
    "area_id": 430721
  },
  {
    "area_name": "汉寿县",
    "pinyin": "Hanshou",
    "level": 3,
    "area_code": 736,
    "post_code": 415900,
    "pid": 430700,
    "id": 1894,
    "area_id": 430722
  },
  {
    "area_name": "澧县",
    "pinyin": "Lixian",
    "level": 3,
    "area_code": 736,
    "post_code": 415500,
    "pid": 430700,
    "id": 1895,
    "area_id": 430723
  },
  {
    "area_name": "临澧县",
    "pinyin": "Linli",
    "level": 3,
    "area_code": 736,
    "post_code": 415200,
    "pid": 430700,
    "id": 1896,
    "area_id": 430724
  },
  {
    "area_name": "桃源县",
    "pinyin": "Taoyuan",
    "level": 3,
    "area_code": 736,
    "post_code": 415700,
    "pid": 430700,
    "id": 1897,
    "area_id": 430725
  },
  {
    "area_name": "石门县",
    "pinyin": "Shimen",
    "level": 3,
    "area_code": 736,
    "post_code": 415300,
    "pid": 430700,
    "id": 1898,
    "area_id": 430726
  },
  {
    "area_name": "津市市",
    "pinyin": "Jinshi",
    "level": 3,
    "area_code": 736,
    "post_code": 415400,
    "pid": 430700,
    "id": 1899,
    "area_id": 430781
  },
  {
    "area_name": "张家界市",
    "pinyin": "Zhangjiajie",
    "level": 2,
    "area_code": 744,
    "post_code": 427000,
    "pid": 430000,
    "id": 1900,
    "area_id": 430800
  },
  {
    "area_name": "永定区",
    "pinyin": "Yongding",
    "level": 3,
    "area_code": 744,
    "post_code": 427000,
    "pid": 430800,
    "id": 1901,
    "area_id": 430802
  },
  {
    "area_name": "武陵源区",
    "pinyin": "Wulingyuan",
    "level": 3,
    "area_code": 744,
    "post_code": 427400,
    "pid": 430800,
    "id": 1902,
    "area_id": 430811
  },
  {
    "area_name": "慈利县",
    "pinyin": "Cili",
    "level": 3,
    "area_code": 744,
    "post_code": 427200,
    "pid": 430800,
    "id": 1903,
    "area_id": 430821
  },
  {
    "area_name": "桑植县",
    "pinyin": "Sangzhi",
    "level": 3,
    "area_code": 744,
    "post_code": 427100,
    "pid": 430800,
    "id": 1904,
    "area_id": 430822
  },
  {
    "area_name": "益阳市",
    "pinyin": "Yiyang",
    "level": 2,
    "area_code": 737,
    "post_code": 413000,
    "pid": 430000,
    "id": 1905,
    "area_id": 430900
  },
  {
    "area_name": "资阳区",
    "pinyin": "Ziyang",
    "level": 3,
    "area_code": 737,
    "post_code": 413001,
    "pid": 430900,
    "id": 1906,
    "area_id": 430902
  },
  {
    "area_name": "赫山区",
    "pinyin": "Heshan",
    "level": 3,
    "area_code": 737,
    "post_code": 413002,
    "pid": 430900,
    "id": 1907,
    "area_id": 430903
  },
  {
    "area_name": "南县",
    "pinyin": "Nanxian",
    "level": 3,
    "area_code": 737,
    "post_code": 413200,
    "pid": 430900,
    "id": 1908,
    "area_id": 430921
  },
  {
    "area_name": "桃江县",
    "pinyin": "Taojiang",
    "level": 3,
    "area_code": 737,
    "post_code": 413400,
    "pid": 430900,
    "id": 1909,
    "area_id": 430922
  },
  {
    "area_name": "安化县",
    "pinyin": "Anhua",
    "level": 3,
    "area_code": 737,
    "post_code": 413500,
    "pid": 430900,
    "id": 1910,
    "area_id": 430923
  },
  {
    "area_name": "沅江市",
    "pinyin": "Yuanjiang",
    "level": 3,
    "area_code": 737,
    "post_code": 413100,
    "pid": 430900,
    "id": 1911,
    "area_id": 430981
  },
  {
    "area_name": "郴州市",
    "pinyin": "Chenzhou",
    "level": 2,
    "area_code": 735,
    "post_code": 423000,
    "pid": 430000,
    "id": 1912,
    "area_id": 431000
  },
  {
    "area_name": "北湖区",
    "pinyin": "Beihu",
    "level": 3,
    "area_code": 735,
    "post_code": 423000,
    "pid": 431000,
    "id": 1913,
    "area_id": 431002
  },
  {
    "area_name": "苏仙区",
    "pinyin": "Suxian",
    "level": 3,
    "area_code": 735,
    "post_code": 423000,
    "pid": 431000,
    "id": 1914,
    "area_id": 431003
  },
  {
    "area_name": "桂阳县",
    "pinyin": "Guiyang",
    "level": 3,
    "area_code": 735,
    "post_code": 424400,
    "pid": 431000,
    "id": 1915,
    "area_id": 431021
  },
  {
    "area_name": "宜章县",
    "pinyin": "Yizhang",
    "level": 3,
    "area_code": 735,
    "post_code": 424200,
    "pid": 431000,
    "id": 1916,
    "area_id": 431022
  },
  {
    "area_name": "永兴县",
    "pinyin": "Yongxing",
    "level": 3,
    "area_code": 735,
    "post_code": 423300,
    "pid": 431000,
    "id": 1917,
    "area_id": 431023
  },
  {
    "area_name": "嘉禾县",
    "pinyin": "Jiahe",
    "level": 3,
    "area_code": 735,
    "post_code": 424500,
    "pid": 431000,
    "id": 1918,
    "area_id": 431024
  },
  {
    "area_name": "临武县",
    "pinyin": "Linwu",
    "level": 3,
    "area_code": 735,
    "post_code": 424300,
    "pid": 431000,
    "id": 1919,
    "area_id": 431025
  },
  {
    "area_name": "汝城县",
    "pinyin": "Rucheng",
    "level": 3,
    "area_code": 735,
    "post_code": 424100,
    "pid": 431000,
    "id": 1920,
    "area_id": 431026
  },
  {
    "area_name": "桂东县",
    "pinyin": "Guidong",
    "level": 3,
    "area_code": 735,
    "post_code": 423500,
    "pid": 431000,
    "id": 1921,
    "area_id": 431027
  },
  {
    "area_name": "安仁县",
    "pinyin": "Anren",
    "level": 3,
    "area_code": 735,
    "post_code": 423600,
    "pid": 431000,
    "id": 1922,
    "area_id": 431028
  },
  {
    "area_name": "资兴市",
    "pinyin": "Zixing",
    "level": 3,
    "area_code": 735,
    "post_code": 423400,
    "pid": 431000,
    "id": 1923,
    "area_id": 431081
  },
  {
    "area_name": "永州市",
    "pinyin": "Yongzhou",
    "level": 2,
    "area_code": 746,
    "post_code": 425000,
    "pid": 430000,
    "id": 1924,
    "area_id": 431100
  },
  {
    "area_name": "零陵区",
    "pinyin": "Lingling",
    "level": 3,
    "area_code": 746,
    "post_code": 425100,
    "pid": 431100,
    "id": 1925,
    "area_id": 431102
  },
  {
    "area_name": "冷水滩区",
    "pinyin": "Lengshuitan",
    "level": 3,
    "area_code": 746,
    "post_code": 425100,
    "pid": 431100,
    "id": 1926,
    "area_id": 431103
  },
  {
    "area_name": "祁阳县",
    "pinyin": "Qiyang",
    "level": 3,
    "area_code": 746,
    "post_code": 426100,
    "pid": 431100,
    "id": 1927,
    "area_id": 431121
  },
  {
    "area_name": "东安县",
    "pinyin": "Dong'an",
    "level": 3,
    "area_code": 746,
    "post_code": 425900,
    "pid": 431100,
    "id": 1928,
    "area_id": 431122
  },
  {
    "area_name": "双牌县",
    "pinyin": "Shuangpai",
    "level": 3,
    "area_code": 746,
    "post_code": 425200,
    "pid": 431100,
    "id": 1929,
    "area_id": 431123
  },
  {
    "area_name": "道县",
    "pinyin": "Daoxian",
    "level": 3,
    "area_code": 746,
    "post_code": 425300,
    "pid": 431100,
    "id": 1930,
    "area_id": 431124
  },
  {
    "area_name": "江永县",
    "pinyin": "Jiangyong",
    "level": 3,
    "area_code": 746,
    "post_code": 425400,
    "pid": 431100,
    "id": 1931,
    "area_id": 431125
  },
  {
    "area_name": "宁远县",
    "pinyin": "Ningyuan",
    "level": 3,
    "area_code": 746,
    "post_code": 425600,
    "pid": 431100,
    "id": 1932,
    "area_id": 431126
  },
  {
    "area_name": "蓝山县",
    "pinyin": "Lanshan",
    "level": 3,
    "area_code": 746,
    "post_code": 425800,
    "pid": 431100,
    "id": 1933,
    "area_id": 431127
  },
  {
    "area_name": "新田县",
    "pinyin": "Xintian",
    "level": 3,
    "area_code": 746,
    "post_code": 425700,
    "pid": 431100,
    "id": 1934,
    "area_id": 431128
  },
  {
    "area_name": "江华瑶族自治县",
    "pinyin": "Jianghua",
    "level": 3,
    "area_code": 746,
    "post_code": 425500,
    "pid": 431100,
    "id": 1935,
    "area_id": 431129
  },
  {
    "area_name": "怀化市",
    "pinyin": "Huaihua",
    "level": 2,
    "area_code": 745,
    "post_code": 418000,
    "pid": 430000,
    "id": 1936,
    "area_id": 431200
  },
  {
    "area_name": "鹤城区",
    "pinyin": "Hecheng",
    "level": 3,
    "area_code": 745,
    "post_code": 418000,
    "pid": 431200,
    "id": 1937,
    "area_id": 431202
  },
  {
    "area_name": "中方县",
    "pinyin": "Zhongfang",
    "level": 3,
    "area_code": 745,
    "post_code": 418005,
    "pid": 431200,
    "id": 1938,
    "area_id": 431221
  },
  {
    "area_name": "沅陵县",
    "pinyin": "Yuanling",
    "level": 3,
    "area_code": 745,
    "post_code": 419600,
    "pid": 431200,
    "id": 1939,
    "area_id": 431222
  },
  {
    "area_name": "辰溪县",
    "pinyin": "Chenxi",
    "level": 3,
    "area_code": 745,
    "post_code": 419500,
    "pid": 431200,
    "id": 1940,
    "area_id": 431223
  },
  {
    "area_name": "溆浦县",
    "pinyin": "Xupu",
    "level": 3,
    "area_code": 745,
    "post_code": 419300,
    "pid": 431200,
    "id": 1941,
    "area_id": 431224
  },
  {
    "area_name": "会同县",
    "pinyin": "Huitong",
    "level": 3,
    "area_code": 745,
    "post_code": 418300,
    "pid": 431200,
    "id": 1942,
    "area_id": 431225
  },
  {
    "area_name": "麻阳苗族自治县",
    "pinyin": "Mayang",
    "level": 3,
    "area_code": 745,
    "post_code": 419400,
    "pid": 431200,
    "id": 1943,
    "area_id": 431226
  },
  {
    "area_name": "新晃侗族自治县",
    "pinyin": "Xinhuang",
    "level": 3,
    "area_code": 745,
    "post_code": 419200,
    "pid": 431200,
    "id": 1944,
    "area_id": 431227
  },
  {
    "area_name": "芷江侗族自治县",
    "pinyin": "Zhijiang",
    "level": 3,
    "area_code": 745,
    "post_code": 419100,
    "pid": 431200,
    "id": 1945,
    "area_id": 431228
  },
  {
    "area_name": "靖州苗族侗族自治县",
    "pinyin": "Jingzhou",
    "level": 3,
    "area_code": 745,
    "post_code": 418400,
    "pid": 431200,
    "id": 1946,
    "area_id": 431229
  },
  {
    "area_name": "通道侗族自治县",
    "pinyin": "Tongdao",
    "level": 3,
    "area_code": 745,
    "post_code": 418500,
    "pid": 431200,
    "id": 1947,
    "area_id": 431230
  },
  {
    "area_name": "洪江市",
    "pinyin": "Hongjiang",
    "level": 3,
    "area_code": 745,
    "post_code": 418100,
    "pid": 431200,
    "id": 1948,
    "area_id": 431281
  },
  {
    "area_name": "娄底市",
    "pinyin": "Loudi",
    "level": 2,
    "area_code": 738,
    "post_code": 417000,
    "pid": 430000,
    "id": 1949,
    "area_id": 431300
  },
  {
    "area_name": "娄星区",
    "pinyin": "Louxing",
    "level": 3,
    "area_code": 738,
    "post_code": 417000,
    "pid": 431300,
    "id": 1950,
    "area_id": 431302
  },
  {
    "area_name": "双峰县",
    "pinyin": "Shuangfeng",
    "level": 3,
    "area_code": 738,
    "post_code": 417700,
    "pid": 431300,
    "id": 1951,
    "area_id": 431321
  },
  {
    "area_name": "新化县",
    "pinyin": "Xinhua",
    "level": 3,
    "area_code": 738,
    "post_code": 417600,
    "pid": 431300,
    "id": 1952,
    "area_id": 431322
  },
  {
    "area_name": "冷水江市",
    "pinyin": "Lengshuijiang",
    "level": 3,
    "area_code": 738,
    "post_code": 417500,
    "pid": 431300,
    "id": 1953,
    "area_id": 431381
  },
  {
    "area_name": "涟源市",
    "pinyin": "Lianyuan",
    "level": 3,
    "area_code": 738,
    "post_code": 417100,
    "pid": 431300,
    "id": 1954,
    "area_id": 431382
  },
  {
    "area_name": "湘西土家族苗族自治州",
    "pinyin": "Xiangxi",
    "level": 2,
    "area_code": 743,
    "post_code": 416000,
    "pid": 430000,
    "id": 1955,
    "area_id": 433100
  },
  {
    "area_name": "吉首市",
    "pinyin": "Jishou",
    "level": 3,
    "area_code": 743,
    "post_code": 416000,
    "pid": 433100,
    "id": 1956,
    "area_id": 433101
  },
  {
    "area_name": "泸溪县",
    "pinyin": "Luxi",
    "level": 3,
    "area_code": 743,
    "post_code": 416100,
    "pid": 433100,
    "id": 1957,
    "area_id": 433122
  },
  {
    "area_name": "凤凰县",
    "pinyin": "Fenghuang",
    "level": 3,
    "area_code": 743,
    "post_code": 416200,
    "pid": 433100,
    "id": 1958,
    "area_id": 433123
  },
  {
    "area_name": "花垣县",
    "pinyin": "Huayuan",
    "level": 3,
    "area_code": 743,
    "post_code": 416400,
    "pid": 433100,
    "id": 1959,
    "area_id": 433124
  },
  {
    "area_name": "保靖县",
    "pinyin": "Baojing",
    "level": 3,
    "area_code": 743,
    "post_code": 416500,
    "pid": 433100,
    "id": 1960,
    "area_id": 433125
  },
  {
    "area_name": "古丈县",
    "pinyin": "Guzhang",
    "level": 3,
    "area_code": 743,
    "post_code": 416300,
    "pid": 433100,
    "id": 1961,
    "area_id": 433126
  },
  {
    "area_name": "永顺县",
    "pinyin": "Yongshun",
    "level": 3,
    "area_code": 743,
    "post_code": 416700,
    "pid": 433100,
    "id": 1962,
    "area_id": 433127
  },
  {
    "area_name": "龙山县",
    "pinyin": "Longshan",
    "level": 3,
    "area_code": 743,
    "post_code": 416800,
    "pid": 433100,
    "id": 1963,
    "area_id": 433130
  },
  {
    "area_name": "广东省",
    "pinyin": "Guangdong",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 1964,
    "area_id": 440000
  },
  {
    "area_name": "广州市",
    "pinyin": "Guangzhou",
    "level": 2,
    "area_code": 20,
    "post_code": 510032,
    "pid": 440000,
    "id": 1965,
    "area_id": 440100
  },
  {
    "area_name": "荔湾区",
    "pinyin": "Liwan",
    "level": 3,
    "area_code": 20,
    "post_code": 510170,
    "pid": 440100,
    "id": 1966,
    "area_id": 440103
  },
  {
    "area_name": "越秀区",
    "pinyin": "Yuexiu",
    "level": 3,
    "area_code": 20,
    "post_code": 510030,
    "pid": 440100,
    "id": 1967,
    "area_id": 440104
  },
  {
    "area_name": "海珠区",
    "pinyin": "Haizhu",
    "level": 3,
    "area_code": 20,
    "post_code": 510300,
    "pid": 440100,
    "id": 1968,
    "area_id": 440105
  },
  {
    "area_name": "天河区",
    "pinyin": "Tianhe",
    "level": 3,
    "area_code": 20,
    "post_code": 510665,
    "pid": 440100,
    "id": 1969,
    "area_id": 440106
  },
  {
    "area_name": "白云区",
    "pinyin": "Baiyun",
    "level": 3,
    "area_code": 20,
    "post_code": 510405,
    "pid": 440100,
    "id": 1970,
    "area_id": 440111
  },
  {
    "area_name": "黄埔区",
    "pinyin": "Huangpu",
    "level": 3,
    "area_code": 20,
    "post_code": 510700,
    "pid": 440100,
    "id": 1971,
    "area_id": 440112
  },
  {
    "area_name": "番禺区",
    "pinyin": "Panyu",
    "level": 3,
    "area_code": 20,
    "post_code": 511400,
    "pid": 440100,
    "id": 1972,
    "area_id": 440113
  },
  {
    "area_name": "花都区",
    "pinyin": "Huadu",
    "level": 3,
    "area_code": 20,
    "post_code": 510800,
    "pid": 440100,
    "id": 1973,
    "area_id": 440114
  },
  {
    "area_name": "南沙区",
    "pinyin": "Nansha",
    "level": 3,
    "area_code": 20,
    "post_code": 511458,
    "pid": 440100,
    "id": 1974,
    "area_id": 440115
  },
  {
    "area_name": "从化区",
    "pinyin": "Conghua",
    "level": 3,
    "area_code": 20,
    "post_code": 510900,
    "pid": 440100,
    "id": 1975,
    "area_id": 440117
  },
  {
    "area_name": "增城区",
    "pinyin": "Zengcheng",
    "level": 3,
    "area_code": 20,
    "post_code": 511300,
    "pid": 440100,
    "id": 1976,
    "area_id": 440118
  },
  {
    "area_name": "韶关市",
    "pinyin": "Shaoguan",
    "level": 2,
    "area_code": 751,
    "post_code": 512002,
    "pid": 440000,
    "id": 1977,
    "area_id": 440200
  },
  {
    "area_name": "武江区",
    "pinyin": "Wujiang",
    "level": 3,
    "area_code": 751,
    "post_code": 512026,
    "pid": 440200,
    "id": 1978,
    "area_id": 440203
  },
  {
    "area_name": "浈江区",
    "pinyin": "Zhenjiang",
    "level": 3,
    "area_code": 751,
    "post_code": 512023,
    "pid": 440200,
    "id": 1979,
    "area_id": 440204
  },
  {
    "area_name": "曲江区",
    "pinyin": "Qujiang",
    "level": 3,
    "area_code": 751,
    "post_code": 512101,
    "pid": 440200,
    "id": 1980,
    "area_id": 440205
  },
  {
    "area_name": "始兴县",
    "pinyin": "Shixing",
    "level": 3,
    "area_code": 751,
    "post_code": 512500,
    "pid": 440200,
    "id": 1981,
    "area_id": 440222
  },
  {
    "area_name": "仁化县",
    "pinyin": "Renhua",
    "level": 3,
    "area_code": 751,
    "post_code": 512300,
    "pid": 440200,
    "id": 1982,
    "area_id": 440224
  },
  {
    "area_name": "翁源县",
    "pinyin": "Wengyuan",
    "level": 3,
    "area_code": 751,
    "post_code": 512600,
    "pid": 440200,
    "id": 1983,
    "area_id": 440229
  },
  {
    "area_name": "乳源瑶族自治县",
    "pinyin": "Ruyuan",
    "level": 3,
    "area_code": 751,
    "post_code": 512700,
    "pid": 440200,
    "id": 1984,
    "area_id": 440232
  },
  {
    "area_name": "新丰县",
    "pinyin": "Xinfeng",
    "level": 3,
    "area_code": 751,
    "post_code": 511100,
    "pid": 440200,
    "id": 1985,
    "area_id": 440233
  },
  {
    "area_name": "乐昌市",
    "pinyin": "Lechang",
    "level": 3,
    "area_code": 751,
    "post_code": 512200,
    "pid": 440200,
    "id": 1986,
    "area_id": 440281
  },
  {
    "area_name": "南雄市",
    "pinyin": "Nanxiong",
    "level": 3,
    "area_code": 751,
    "post_code": 512400,
    "pid": 440200,
    "id": 1987,
    "area_id": 440282
  },
  {
    "area_name": "深圳市",
    "pinyin": "Shenzhen",
    "level": 2,
    "area_code": 755,
    "post_code": 518035,
    "pid": 440000,
    "id": 1988,
    "area_id": 440300
  },
  {
    "area_name": "罗湖区",
    "pinyin": "Luohu",
    "level": 3,
    "area_code": 755,
    "post_code": 518021,
    "pid": 440300,
    "id": 1989,
    "area_id": 440303
  },
  {
    "area_name": "福田区",
    "pinyin": "Futian",
    "level": 3,
    "area_code": 755,
    "post_code": 518048,
    "pid": 440300,
    "id": 1990,
    "area_id": 440304
  },
  {
    "area_name": "南山区",
    "pinyin": "Nanshan",
    "level": 3,
    "area_code": 755,
    "post_code": 518051,
    "pid": 440300,
    "id": 1991,
    "area_id": 440305
  },
  {
    "area_name": "宝安区",
    "pinyin": "Bao'an",
    "level": 3,
    "area_code": 755,
    "post_code": 518101,
    "pid": 440300,
    "id": 1992,
    "area_id": 440306
  },
  {
    "area_name": "龙岗区",
    "pinyin": "Longgang",
    "level": 3,
    "area_code": 755,
    "post_code": 518172,
    "pid": 440300,
    "id": 1993,
    "area_id": 440307
  },
  {
    "area_name": "盐田区",
    "pinyin": "Yantian",
    "level": 3,
    "area_code": 755,
    "post_code": 518081,
    "pid": 440300,
    "id": 1994,
    "area_id": 440308
  },
  {
    "area_name": "光明新区",
    "pinyin": "Guangmingxinqu",
    "level": 3,
    "area_code": 755,
    "post_code": 518100,
    "pid": 440300,
    "id": 1995,
    "area_id": 440309
  },
  {
    "area_name": "坪山新区",
    "pinyin": "Pingshanxinqu",
    "level": 3,
    "area_code": 755,
    "post_code": 518000,
    "pid": 440300,
    "id": 1996,
    "area_id": 440310
  },
  {
    "area_name": "大鹏新区",
    "pinyin": "Dapengxinqu",
    "level": 3,
    "area_code": 755,
    "post_code": 518000,
    "pid": 440300,
    "id": 1997,
    "area_id": 440311
  },
  {
    "area_name": "龙华新区",
    "pinyin": "Longhuaxinqu",
    "level": 3,
    "area_code": 755,
    "post_code": 518100,
    "pid": 440300,
    "id": 1998,
    "area_id": 440312
  },
  {
    "area_name": "珠海市",
    "pinyin": "Zhuhai",
    "level": 2,
    "area_code": 756,
    "post_code": 519000,
    "pid": 440000,
    "id": 1999,
    "area_id": 440400
  },
  {
    "area_name": "香洲区",
    "pinyin": "Xiangzhou",
    "level": 3,
    "area_code": 756,
    "post_code": 519000,
    "pid": 440400,
    "id": 2000,
    "area_id": 440402
  },
  {
    "area_name": "斗门区",
    "pinyin": "Doumen",
    "level": 3,
    "area_code": 756,
    "post_code": 519110,
    "pid": 440400,
    "id": 2001,
    "area_id": 440403
  },
  {
    "area_name": "金湾区",
    "pinyin": "Jinwan",
    "level": 3,
    "area_code": 756,
    "post_code": 519040,
    "pid": 440400,
    "id": 2002,
    "area_id": 440404
  },
  {
    "area_name": "汕头市",
    "pinyin": "Shantou",
    "level": 2,
    "area_code": 754,
    "post_code": 515041,
    "pid": 440000,
    "id": 2003,
    "area_id": 440500
  },
  {
    "area_name": "龙湖区",
    "pinyin": "Longhu",
    "level": 3,
    "area_code": 754,
    "post_code": 515041,
    "pid": 440500,
    "id": 2004,
    "area_id": 440507
  },
  {
    "area_name": "金平区",
    "pinyin": "Jinping",
    "level": 3,
    "area_code": 754,
    "post_code": 515041,
    "pid": 440500,
    "id": 2005,
    "area_id": 440511
  },
  {
    "area_name": "濠江区",
    "pinyin": "Haojiang",
    "level": 3,
    "area_code": 754,
    "post_code": 515071,
    "pid": 440500,
    "id": 2006,
    "area_id": 440512
  },
  {
    "area_name": "潮阳区",
    "pinyin": "Chaoyang",
    "level": 3,
    "area_code": 754,
    "post_code": 515100,
    "pid": 440500,
    "id": 2007,
    "area_id": 440513
  },
  {
    "area_name": "潮南区",
    "pinyin": "Chaonan",
    "level": 3,
    "area_code": 754,
    "post_code": 515144,
    "pid": 440500,
    "id": 2008,
    "area_id": 440514
  },
  {
    "area_name": "澄海区",
    "pinyin": "Chenghai",
    "level": 3,
    "area_code": 754,
    "post_code": 515800,
    "pid": 440500,
    "id": 2009,
    "area_id": 440515
  },
  {
    "area_name": "南澳县",
    "pinyin": "Nanao",
    "level": 3,
    "area_code": 754,
    "post_code": 515900,
    "pid": 440500,
    "id": 2010,
    "area_id": 440523
  },
  {
    "area_name": "佛山市",
    "pinyin": "Foshan",
    "level": 2,
    "area_code": 757,
    "post_code": 528000,
    "pid": 440000,
    "id": 2011,
    "area_id": 440600
  },
  {
    "area_name": "禅城区",
    "pinyin": "Chancheng",
    "level": 3,
    "area_code": 757,
    "post_code": 528000,
    "pid": 440600,
    "id": 2012,
    "area_id": 440604
  },
  {
    "area_name": "南海区",
    "pinyin": "Nanhai",
    "level": 3,
    "area_code": 757,
    "post_code": 528251,
    "pid": 440600,
    "id": 2013,
    "area_id": 440605
  },
  {
    "area_name": "顺德区",
    "pinyin": "Shunde",
    "level": 3,
    "area_code": 757,
    "post_code": 528300,
    "pid": 440600,
    "id": 2014,
    "area_id": 440606
  },
  {
    "area_name": "三水区",
    "pinyin": "Sanshui",
    "level": 3,
    "area_code": 757,
    "post_code": 528133,
    "pid": 440600,
    "id": 2015,
    "area_id": 440607
  },
  {
    "area_name": "高明区",
    "pinyin": "Gaoming",
    "level": 3,
    "area_code": 757,
    "post_code": 528500,
    "pid": 440600,
    "id": 2016,
    "area_id": 440608
  },
  {
    "area_name": "江门市",
    "pinyin": "Jiangmen",
    "level": 2,
    "area_code": 750,
    "post_code": 529000,
    "pid": 440000,
    "id": 2017,
    "area_id": 440700
  },
  {
    "area_name": "蓬江区",
    "pinyin": "Pengjiang",
    "level": 3,
    "area_code": 750,
    "post_code": 529000,
    "pid": 440700,
    "id": 2018,
    "area_id": 440703
  },
  {
    "area_name": "江海区",
    "pinyin": "Jianghai",
    "level": 3,
    "area_code": 750,
    "post_code": 529040,
    "pid": 440700,
    "id": 2019,
    "area_id": 440704
  },
  {
    "area_name": "新会区",
    "pinyin": "Xinhui",
    "level": 3,
    "area_code": 750,
    "post_code": 529100,
    "pid": 440700,
    "id": 2020,
    "area_id": 440705
  },
  {
    "area_name": "台山市",
    "pinyin": "Taishan",
    "level": 3,
    "area_code": 750,
    "post_code": 529200,
    "pid": 440700,
    "id": 2021,
    "area_id": 440781
  },
  {
    "area_name": "开平市",
    "pinyin": "Kaiping",
    "level": 3,
    "area_code": 750,
    "post_code": 529337,
    "pid": 440700,
    "id": 2022,
    "area_id": 440783
  },
  {
    "area_name": "鹤山市",
    "pinyin": "Heshan",
    "level": 3,
    "area_code": 750,
    "post_code": 529700,
    "pid": 440700,
    "id": 2023,
    "area_id": 440784
  },
  {
    "area_name": "恩平市",
    "pinyin": "Enping",
    "level": 3,
    "area_code": 750,
    "post_code": 529400,
    "pid": 440700,
    "id": 2024,
    "area_id": 440785
  },
  {
    "area_name": "湛江市",
    "pinyin": "Zhanjiang",
    "level": 2,
    "area_code": 759,
    "post_code": 524047,
    "pid": 440000,
    "id": 2025,
    "area_id": 440800
  },
  {
    "area_name": "赤坎区",
    "pinyin": "Chikan",
    "level": 3,
    "area_code": 759,
    "post_code": 524033,
    "pid": 440800,
    "id": 2026,
    "area_id": 440802
  },
  {
    "area_name": "霞山区",
    "pinyin": "Xiashan",
    "level": 3,
    "area_code": 759,
    "post_code": 524011,
    "pid": 440800,
    "id": 2027,
    "area_id": 440803
  },
  {
    "area_name": "坡头区",
    "pinyin": "Potou",
    "level": 3,
    "area_code": 759,
    "post_code": 524057,
    "pid": 440800,
    "id": 2028,
    "area_id": 440804
  },
  {
    "area_name": "麻章区",
    "pinyin": "Mazhang",
    "level": 3,
    "area_code": 759,
    "post_code": 524094,
    "pid": 440800,
    "id": 2029,
    "area_id": 440811
  },
  {
    "area_name": "遂溪县",
    "pinyin": "Suixi",
    "level": 3,
    "area_code": 759,
    "post_code": 524300,
    "pid": 440800,
    "id": 2030,
    "area_id": 440823
  },
  {
    "area_name": "徐闻县",
    "pinyin": "Xuwen",
    "level": 3,
    "area_code": 759,
    "post_code": 524100,
    "pid": 440800,
    "id": 2031,
    "area_id": 440825
  },
  {
    "area_name": "廉江市",
    "pinyin": "Lianjiang",
    "level": 3,
    "area_code": 759,
    "post_code": 524400,
    "pid": 440800,
    "id": 2032,
    "area_id": 440881
  },
  {
    "area_name": "雷州市",
    "pinyin": "Leizhou",
    "level": 3,
    "area_code": 759,
    "post_code": 524200,
    "pid": 440800,
    "id": 2033,
    "area_id": 440882
  },
  {
    "area_name": "吴川市",
    "pinyin": "Wuchuan",
    "level": 3,
    "area_code": 759,
    "post_code": 524500,
    "pid": 440800,
    "id": 2034,
    "area_id": 440883
  },
  {
    "area_name": "茂名市",
    "pinyin": "Maoming",
    "level": 2,
    "area_code": 668,
    "post_code": 525000,
    "pid": 440000,
    "id": 2035,
    "area_id": 440900
  },
  {
    "area_name": "茂南区",
    "pinyin": "Maonan",
    "level": 3,
    "area_code": 668,
    "post_code": 525000,
    "pid": 440900,
    "id": 2036,
    "area_id": 440902
  },
  {
    "area_name": "电白区",
    "pinyin": "Dianbai",
    "level": 3,
    "area_code": 668,
    "post_code": 525400,
    "pid": 440900,
    "id": 2037,
    "area_id": 440904
  },
  {
    "area_name": "高州市",
    "pinyin": "Gaozhou",
    "level": 3,
    "area_code": 668,
    "post_code": 525200,
    "pid": 440900,
    "id": 2038,
    "area_id": 440981
  },
  {
    "area_name": "化州市",
    "pinyin": "Huazhou",
    "level": 3,
    "area_code": 668,
    "post_code": 525100,
    "pid": 440900,
    "id": 2039,
    "area_id": 440982
  },
  {
    "area_name": "信宜市",
    "pinyin": "Xinyi",
    "level": 3,
    "area_code": 668,
    "post_code": 525300,
    "pid": 440900,
    "id": 2040,
    "area_id": 440983
  },
  {
    "area_name": "肇庆市",
    "pinyin": "Zhaoqing",
    "level": 2,
    "area_code": 758,
    "post_code": 526040,
    "pid": 440000,
    "id": 2041,
    "area_id": 441200
  },
  {
    "area_name": "端州区",
    "pinyin": "Duanzhou",
    "level": 3,
    "area_code": 758,
    "post_code": 526060,
    "pid": 441200,
    "id": 2042,
    "area_id": 441202
  },
  {
    "area_name": "鼎湖区",
    "pinyin": "Dinghu",
    "level": 3,
    "area_code": 758,
    "post_code": 526070,
    "pid": 441200,
    "id": 2043,
    "area_id": 441203
  },
  {
    "area_name": "广宁县",
    "pinyin": "Guangning",
    "level": 3,
    "area_code": 758,
    "post_code": 526300,
    "pid": 441200,
    "id": 2044,
    "area_id": 441223
  },
  {
    "area_name": "怀集县",
    "pinyin": "Huaiji",
    "level": 3,
    "area_code": 758,
    "post_code": 526400,
    "pid": 441200,
    "id": 2045,
    "area_id": 441224
  },
  {
    "area_name": "封开县",
    "pinyin": "Fengkai",
    "level": 3,
    "area_code": 758,
    "post_code": 526500,
    "pid": 441200,
    "id": 2046,
    "area_id": 441225
  },
  {
    "area_name": "德庆县",
    "pinyin": "Deqing",
    "level": 3,
    "area_code": 758,
    "post_code": 526600,
    "pid": 441200,
    "id": 2047,
    "area_id": 441226
  },
  {
    "area_name": "高要市",
    "pinyin": "Gaoyao",
    "level": 3,
    "area_code": 758,
    "post_code": 526100,
    "pid": 441200,
    "id": 2048,
    "area_id": 441283
  },
  {
    "area_name": "四会市",
    "pinyin": "Sihui",
    "level": 3,
    "area_code": 758,
    "post_code": 526200,
    "pid": 441200,
    "id": 2049,
    "area_id": 441284
  },
  {
    "area_name": "惠州市",
    "pinyin": "Huizhou",
    "level": 2,
    "area_code": 752,
    "post_code": 516000,
    "pid": 440000,
    "id": 2050,
    "area_id": 441300
  },
  {
    "area_name": "惠城区",
    "pinyin": "Huicheng",
    "level": 3,
    "area_code": 752,
    "post_code": 516008,
    "pid": 441300,
    "id": 2051,
    "area_id": 441302
  },
  {
    "area_name": "惠阳区",
    "pinyin": "Huiyang",
    "level": 3,
    "area_code": 752,
    "post_code": 516211,
    "pid": 441300,
    "id": 2052,
    "area_id": 441303
  },
  {
    "area_name": "博罗县",
    "pinyin": "Boluo",
    "level": 3,
    "area_code": 752,
    "post_code": 516100,
    "pid": 441300,
    "id": 2053,
    "area_id": 441322
  },
  {
    "area_name": "惠东县",
    "pinyin": "Huidong",
    "level": 3,
    "area_code": 752,
    "post_code": 516300,
    "pid": 441300,
    "id": 2054,
    "area_id": 441323
  },
  {
    "area_name": "龙门县",
    "pinyin": "Longmen",
    "level": 3,
    "area_code": 752,
    "post_code": 516800,
    "pid": 441300,
    "id": 2055,
    "area_id": 441324
  },
  {
    "area_name": "梅州市",
    "pinyin": "Meizhou",
    "level": 2,
    "area_code": 753,
    "post_code": 514021,
    "pid": 440000,
    "id": 2056,
    "area_id": 441400
  },
  {
    "area_name": "梅江区",
    "pinyin": "Meijiang",
    "level": 3,
    "area_code": 753,
    "post_code": 514000,
    "pid": 441400,
    "id": 2057,
    "area_id": 441402
  },
  {
    "area_name": "梅县区",
    "pinyin": "Meixian",
    "level": 3,
    "area_code": 753,
    "post_code": 514787,
    "pid": 441400,
    "id": 2058,
    "area_id": 441403
  },
  {
    "area_name": "大埔县",
    "pinyin": "Dabu",
    "level": 3,
    "area_code": 753,
    "post_code": 514200,
    "pid": 441400,
    "id": 2059,
    "area_id": 441422
  },
  {
    "area_name": "丰顺县",
    "pinyin": "Fengshun",
    "level": 3,
    "area_code": 753,
    "post_code": 514300,
    "pid": 441400,
    "id": 2060,
    "area_id": 441423
  },
  {
    "area_name": "五华县",
    "pinyin": "Wuhua",
    "level": 3,
    "area_code": 753,
    "post_code": 514400,
    "pid": 441400,
    "id": 2061,
    "area_id": 441424
  },
  {
    "area_name": "平远县",
    "pinyin": "Pingyuan",
    "level": 3,
    "area_code": 753,
    "post_code": 514600,
    "pid": 441400,
    "id": 2062,
    "area_id": 441426
  },
  {
    "area_name": "蕉岭县",
    "pinyin": "Jiaoling",
    "level": 3,
    "area_code": 753,
    "post_code": 514100,
    "pid": 441400,
    "id": 2063,
    "area_id": 441427
  },
  {
    "area_name": "兴宁市",
    "pinyin": "Xingning",
    "level": 3,
    "area_code": 753,
    "post_code": 514500,
    "pid": 441400,
    "id": 2064,
    "area_id": 441481
  },
  {
    "area_name": "汕尾市",
    "pinyin": "Shanwei",
    "level": 2,
    "area_code": 660,
    "post_code": 516600,
    "pid": 440000,
    "id": 2065,
    "area_id": 441500
  },
  {
    "area_name": "城区",
    "pinyin": "Chengqu",
    "level": 3,
    "area_code": 660,
    "post_code": 516600,
    "pid": 441500,
    "id": 2066,
    "area_id": 441502
  },
  {
    "area_name": "海丰县",
    "pinyin": "Haifeng",
    "level": 3,
    "area_code": 660,
    "post_code": 516400,
    "pid": 441500,
    "id": 2067,
    "area_id": 441521
  },
  {
    "area_name": "陆河县",
    "pinyin": "Luhe",
    "level": 3,
    "area_code": 660,
    "post_code": 516700,
    "pid": 441500,
    "id": 2068,
    "area_id": 441523
  },
  {
    "area_name": "陆丰市",
    "pinyin": "Lufeng",
    "level": 3,
    "area_code": 660,
    "post_code": 516500,
    "pid": 441500,
    "id": 2069,
    "area_id": 441581
  },
  {
    "area_name": "河源市",
    "pinyin": "Heyuan",
    "level": 2,
    "area_code": 762,
    "post_code": 517000,
    "pid": 440000,
    "id": 2070,
    "area_id": 441600
  },
  {
    "area_name": "源城区",
    "pinyin": "Yuancheng",
    "level": 3,
    "area_code": 762,
    "post_code": 517000,
    "pid": 441600,
    "id": 2071,
    "area_id": 441602
  },
  {
    "area_name": "紫金县",
    "pinyin": "Zijin",
    "level": 3,
    "area_code": 762,
    "post_code": 517400,
    "pid": 441600,
    "id": 2072,
    "area_id": 441621
  },
  {
    "area_name": "龙川县",
    "pinyin": "Longchuan",
    "level": 3,
    "area_code": 762,
    "post_code": 517300,
    "pid": 441600,
    "id": 2073,
    "area_id": 441622
  },
  {
    "area_name": "连平县",
    "pinyin": "Lianping",
    "level": 3,
    "area_code": 762,
    "post_code": 517100,
    "pid": 441600,
    "id": 2074,
    "area_id": 441623
  },
  {
    "area_name": "和平县",
    "pinyin": "Heping",
    "level": 3,
    "area_code": 762,
    "post_code": 517200,
    "pid": 441600,
    "id": 2075,
    "area_id": 441624
  },
  {
    "area_name": "东源县",
    "pinyin": "Dongyuan",
    "level": 3,
    "area_code": 762,
    "post_code": 517583,
    "pid": 441600,
    "id": 2076,
    "area_id": 441625
  },
  {
    "area_name": "阳江市",
    "pinyin": "Yangjiang",
    "level": 2,
    "area_code": 662,
    "post_code": 529500,
    "pid": 440000,
    "id": 2077,
    "area_id": 441700
  },
  {
    "area_name": "江城区",
    "pinyin": "Jiangcheng",
    "level": 3,
    "area_code": 662,
    "post_code": 529500,
    "pid": 441700,
    "id": 2078,
    "area_id": 441702
  },
  {
    "area_name": "阳东区",
    "pinyin": "Yangdong",
    "level": 3,
    "area_code": 662,
    "post_code": 529900,
    "pid": 441700,
    "id": 2079,
    "area_id": 441704
  },
  {
    "area_name": "阳西县",
    "pinyin": "Yangxi",
    "level": 3,
    "area_code": 662,
    "post_code": 529800,
    "pid": 441700,
    "id": 2080,
    "area_id": 441721
  },
  {
    "area_name": "阳春市",
    "pinyin": "Yangchun",
    "level": 3,
    "area_code": 662,
    "post_code": 529600,
    "pid": 441700,
    "id": 2081,
    "area_id": 441781
  },
  {
    "area_name": "清远市",
    "pinyin": "Qingyuan",
    "level": 2,
    "area_code": 763,
    "post_code": 511500,
    "pid": 440000,
    "id": 2082,
    "area_id": 441800
  },
  {
    "area_name": "清城区",
    "pinyin": "Qingcheng",
    "level": 3,
    "area_code": 763,
    "post_code": 511515,
    "pid": 441800,
    "id": 2083,
    "area_id": 441802
  },
  {
    "area_name": "清新区",
    "pinyin": "Qingxin",
    "level": 3,
    "area_code": 763,
    "post_code": 511810,
    "pid": 441800,
    "id": 2084,
    "area_id": 441803
  },
  {
    "area_name": "佛冈县",
    "pinyin": "Fogang",
    "level": 3,
    "area_code": 763,
    "post_code": 511600,
    "pid": 441800,
    "id": 2085,
    "area_id": 441821
  },
  {
    "area_name": "阳山县",
    "pinyin": "Yangshan",
    "level": 3,
    "area_code": 763,
    "post_code": 513100,
    "pid": 441800,
    "id": 2086,
    "area_id": 441823
  },
  {
    "area_name": "连山壮族瑶族自治县",
    "pinyin": "Lianshan",
    "level": 3,
    "area_code": 763,
    "post_code": 513200,
    "pid": 441800,
    "id": 2087,
    "area_id": 441825
  },
  {
    "area_name": "连南瑶族自治县",
    "pinyin": "Liannan",
    "level": 3,
    "area_code": 763,
    "post_code": 513300,
    "pid": 441800,
    "id": 2088,
    "area_id": 441826
  },
  {
    "area_name": "英德市",
    "pinyin": "Yingde",
    "level": 3,
    "area_code": 763,
    "post_code": 513000,
    "pid": 441800,
    "id": 2089,
    "area_id": 441881
  },
  {
    "area_name": "连州市",
    "pinyin": "Lianzhou",
    "level": 3,
    "area_code": 763,
    "post_code": 513400,
    "pid": 441800,
    "id": 2090,
    "area_id": 441882
  },
  {
    "area_name": "东莞市",
    "pinyin": "Dongguan",
    "level": 2,
    "area_code": 769,
    "post_code": 523888,
    "pid": 440000,
    "id": 2091,
    "area_id": 441900
  },
  {
    "area_name": "莞城区",
    "pinyin": "Guancheng",
    "level": 3,
    "area_code": 769,
    "post_code": 523128,
    "pid": 441900,
    "id": 2092,
    "area_id": 441901
  },
  {
    "area_name": "南城区",
    "pinyin": "Nancheng",
    "level": 3,
    "area_code": 769,
    "post_code": 523617,
    "pid": 441900,
    "id": 2093,
    "area_id": 441902
  },
  {
    "area_name": "万江区",
    "pinyin": "Wanjiang",
    "level": 3,
    "area_code": 769,
    "post_code": 523039,
    "pid": 441900,
    "id": 2094,
    "area_id": 441904
  },
  {
    "area_name": "石碣镇",
    "pinyin": "Shijie",
    "level": 3,
    "area_code": 769,
    "post_code": 523290,
    "pid": 441900,
    "id": 2095,
    "area_id": 441905
  },
  {
    "area_name": "石龙镇",
    "pinyin": "Shilong",
    "level": 3,
    "area_code": 769,
    "post_code": 523326,
    "pid": 441900,
    "id": 2096,
    "area_id": 441906
  },
  {
    "area_name": "茶山镇",
    "pinyin": "Chashan",
    "level": 3,
    "area_code": 769,
    "post_code": 523380,
    "pid": 441900,
    "id": 2097,
    "area_id": 441907
  },
  {
    "area_name": "石排镇",
    "pinyin": "Shipai",
    "level": 3,
    "area_code": 769,
    "post_code": 523346,
    "pid": 441900,
    "id": 2098,
    "area_id": 441908
  },
  {
    "area_name": "企石镇",
    "pinyin": "Qishi",
    "level": 3,
    "area_code": 769,
    "post_code": 523507,
    "pid": 441900,
    "id": 2099,
    "area_id": 441909
  },
  {
    "area_name": "横沥镇",
    "pinyin": "Hengli",
    "level": 3,
    "area_code": 769,
    "post_code": 523471,
    "pid": 441900,
    "id": 2100,
    "area_id": 441910
  },
  {
    "area_name": "桥头镇",
    "pinyin": "Qiaotou",
    "level": 3,
    "area_code": 769,
    "post_code": 523520,
    "pid": 441900,
    "id": 2101,
    "area_id": 441911
  },
  {
    "area_name": "谢岗镇",
    "pinyin": "Xiegang",
    "level": 3,
    "area_code": 769,
    "post_code": 523592,
    "pid": 441900,
    "id": 2102,
    "area_id": 441912
  },
  {
    "area_name": "东坑镇",
    "pinyin": "Dongkeng",
    "level": 3,
    "area_code": 769,
    "post_code": 523451,
    "pid": 441900,
    "id": 2103,
    "area_id": 441913
  },
  {
    "area_name": "常平镇",
    "pinyin": "Changping",
    "level": 3,
    "area_code": 769,
    "post_code": 523560,
    "pid": 441900,
    "id": 2104,
    "area_id": 441914
  },
  {
    "area_name": "寮步镇",
    "pinyin": "Liaobu",
    "level": 3,
    "area_code": 769,
    "post_code": 523411,
    "pid": 441900,
    "id": 2105,
    "area_id": 441915
  },
  {
    "area_name": "大朗镇",
    "pinyin": "Dalang",
    "level": 3,
    "area_code": 769,
    "post_code": 523770,
    "pid": 441900,
    "id": 2106,
    "area_id": 441916
  },
  {
    "area_name": "麻涌镇",
    "pinyin": "Machong",
    "level": 3,
    "area_code": 769,
    "post_code": 523143,
    "pid": 441900,
    "id": 2107,
    "area_id": 441917
  },
  {
    "area_name": "中堂镇",
    "pinyin": "Zhongtang",
    "level": 3,
    "area_code": 769,
    "post_code": 523233,
    "pid": 441900,
    "id": 2108,
    "area_id": 441918
  },
  {
    "area_name": "高埗镇",
    "pinyin": "Gaobu",
    "level": 3,
    "area_code": 769,
    "post_code": 523282,
    "pid": 441900,
    "id": 2109,
    "area_id": 441919
  },
  {
    "area_name": "樟木头镇",
    "pinyin": "Zhangmutou",
    "level": 3,
    "area_code": 769,
    "post_code": 523619,
    "pid": 441900,
    "id": 2110,
    "area_id": 441920
  },
  {
    "area_name": "大岭山镇",
    "pinyin": "Dalingshan",
    "level": 3,
    "area_code": 769,
    "post_code": 523835,
    "pid": 441900,
    "id": 2111,
    "area_id": 441921
  },
  {
    "area_name": "望牛墩镇",
    "pinyin": "Wangniudun",
    "level": 3,
    "area_code": 769,
    "post_code": 523203,
    "pid": 441900,
    "id": 2112,
    "area_id": 441922
  },
  {
    "area_name": "黄江镇",
    "pinyin": "Huangjiang",
    "level": 3,
    "area_code": 769,
    "post_code": 523755,
    "pid": 441900,
    "id": 2113,
    "area_id": 441923
  },
  {
    "area_name": "洪梅镇",
    "pinyin": "Hongmei",
    "level": 3,
    "area_code": 769,
    "post_code": 523163,
    "pid": 441900,
    "id": 2114,
    "area_id": 441924
  },
  {
    "area_name": "清溪镇",
    "pinyin": "Qingxi",
    "level": 3,
    "area_code": 769,
    "post_code": 523660,
    "pid": 441900,
    "id": 2115,
    "area_id": 441925
  },
  {
    "area_name": "沙田镇",
    "pinyin": "Shatian",
    "level": 3,
    "area_code": 769,
    "post_code": 523988,
    "pid": 441900,
    "id": 2116,
    "area_id": 441926
  },
  {
    "area_name": "道滘镇",
    "pinyin": "Daojiao",
    "level": 3,
    "area_code": 769,
    "post_code": 523171,
    "pid": 441900,
    "id": 2117,
    "area_id": 441927
  },
  {
    "area_name": "塘厦镇",
    "pinyin": "Tangxia",
    "level": 3,
    "area_code": 769,
    "post_code": 523713,
    "pid": 441900,
    "id": 2118,
    "area_id": 441928
  },
  {
    "area_name": "虎门镇",
    "pinyin": "Humen",
    "level": 3,
    "area_code": 769,
    "post_code": 523932,
    "pid": 441900,
    "id": 2119,
    "area_id": 441929
  },
  {
    "area_name": "厚街镇",
    "pinyin": "Houjie",
    "level": 3,
    "area_code": 769,
    "post_code": 523960,
    "pid": 441900,
    "id": 2120,
    "area_id": 441930
  },
  {
    "area_name": "凤岗镇",
    "pinyin": "Fenggang",
    "level": 3,
    "area_code": 769,
    "post_code": 523690,
    "pid": 441900,
    "id": 2121,
    "area_id": 441931
  },
  {
    "area_name": "长安镇",
    "pinyin": "Chang'an",
    "level": 3,
    "area_code": 769,
    "post_code": 523850,
    "pid": 441900,
    "id": 2122,
    "area_id": 441932
  },
  {
    "area_name": "中山市",
    "pinyin": "Zhongshan",
    "level": 2,
    "area_code": 760,
    "post_code": 528403,
    "pid": 440000,
    "id": 2123,
    "area_id": 442000
  },
  {
    "area_name": "石岐区",
    "pinyin": "Shiqi",
    "level": 3,
    "area_code": 760,
    "post_code": 528400,
    "pid": 442000,
    "id": 2124,
    "area_id": 442001
  },
  {
    "area_name": "南区",
    "pinyin": "Nanqu",
    "level": 3,
    "area_code": 760,
    "post_code": 528400,
    "pid": 442000,
    "id": 2125,
    "area_id": 442004
  },
  {
    "area_name": "五桂山区",
    "pinyin": "Wuguishan",
    "level": 3,
    "area_code": 760,
    "post_code": 528458,
    "pid": 442000,
    "id": 2126,
    "area_id": 442005
  },
  {
    "area_name": "火炬开发区",
    "pinyin": "Huoju",
    "level": 3,
    "area_code": 760,
    "post_code": 528437,
    "pid": 442000,
    "id": 2127,
    "area_id": 442006
  },
  {
    "area_name": "黄圃镇",
    "pinyin": "Huangpu",
    "level": 3,
    "area_code": 760,
    "post_code": 528429,
    "pid": 442000,
    "id": 2128,
    "area_id": 442007
  },
  {
    "area_name": "南头镇",
    "pinyin": "Nantou",
    "level": 3,
    "area_code": 760,
    "post_code": 528421,
    "pid": 442000,
    "id": 2129,
    "area_id": 442008
  },
  {
    "area_name": "东凤镇",
    "pinyin": "Dongfeng",
    "level": 3,
    "area_code": 760,
    "post_code": 528425,
    "pid": 442000,
    "id": 2130,
    "area_id": 442009
  },
  {
    "area_name": "阜沙镇",
    "pinyin": "Fusha",
    "level": 3,
    "area_code": 760,
    "post_code": 528434,
    "pid": 442000,
    "id": 2131,
    "area_id": 442010
  },
  {
    "area_name": "小榄镇",
    "pinyin": "Xiaolan",
    "level": 3,
    "area_code": 760,
    "post_code": 528415,
    "pid": 442000,
    "id": 2132,
    "area_id": 442011
  },
  {
    "area_name": "东升镇",
    "pinyin": "Dongsheng",
    "level": 3,
    "area_code": 760,
    "post_code": 528400,
    "pid": 442000,
    "id": 2133,
    "area_id": 442012
  },
  {
    "area_name": "古镇镇",
    "pinyin": "Guzhen",
    "level": 3,
    "area_code": 760,
    "post_code": 528422,
    "pid": 442000,
    "id": 2134,
    "area_id": 442013
  },
  {
    "area_name": "横栏镇",
    "pinyin": "Henglan",
    "level": 3,
    "area_code": 760,
    "post_code": 528478,
    "pid": 442000,
    "id": 2135,
    "area_id": 442014
  },
  {
    "area_name": "三角镇",
    "pinyin": "Sanjiao",
    "level": 3,
    "area_code": 760,
    "post_code": 528422,
    "pid": 442000,
    "id": 2136,
    "area_id": 442015
  },
  {
    "area_name": "民众镇",
    "pinyin": "Minzhong",
    "level": 3,
    "area_code": 760,
    "post_code": 528441,
    "pid": 442000,
    "id": 2137,
    "area_id": 442016
  },
  {
    "area_name": "南朗镇",
    "pinyin": "Nanlang",
    "level": 3,
    "area_code": 760,
    "post_code": 528454,
    "pid": 442000,
    "id": 2138,
    "area_id": 442017
  },
  {
    "area_name": "港口镇",
    "pinyin": "Gangkou",
    "level": 3,
    "area_code": 760,
    "post_code": 528447,
    "pid": 442000,
    "id": 2139,
    "area_id": 442018
  },
  {
    "area_name": "大涌镇",
    "pinyin": "Dayong",
    "level": 3,
    "area_code": 760,
    "post_code": 528476,
    "pid": 442000,
    "id": 2140,
    "area_id": 442019
  },
  {
    "area_name": "沙溪镇",
    "pinyin": "Shaxi",
    "level": 3,
    "area_code": 760,
    "post_code": 528471,
    "pid": 442000,
    "id": 2141,
    "area_id": 442020
  },
  {
    "area_name": "三乡镇",
    "pinyin": "Sanxiang",
    "level": 3,
    "area_code": 760,
    "post_code": 528463,
    "pid": 442000,
    "id": 2142,
    "area_id": 442021
  },
  {
    "area_name": "板芙镇",
    "pinyin": "Banfu",
    "level": 3,
    "area_code": 760,
    "post_code": 528459,
    "pid": 442000,
    "id": 2143,
    "area_id": 442022
  },
  {
    "area_name": "神湾镇",
    "pinyin": "Shenwan",
    "level": 3,
    "area_code": 760,
    "post_code": 528462,
    "pid": 442000,
    "id": 2144,
    "area_id": 442023
  },
  {
    "area_name": "坦洲镇",
    "pinyin": "Tanzhou",
    "level": 3,
    "area_code": 760,
    "post_code": 528467,
    "pid": 442000,
    "id": 2145,
    "area_id": 442024
  },
  {
    "area_name": "潮州市",
    "pinyin": "Chaozhou",
    "level": 2,
    "area_code": 768,
    "post_code": 521000,
    "pid": 440000,
    "id": 2146,
    "area_id": 445100
  },
  {
    "area_name": "湘桥区",
    "pinyin": "Xiangqiao",
    "level": 3,
    "area_code": 768,
    "post_code": 521000,
    "pid": 445100,
    "id": 2147,
    "area_id": 445102
  },
  {
    "area_name": "潮安区",
    "pinyin": "Chao'an",
    "level": 3,
    "area_code": 768,
    "post_code": 515638,
    "pid": 445100,
    "id": 2148,
    "area_id": 445103
  },
  {
    "area_name": "饶平县",
    "pinyin": "Raoping",
    "level": 3,
    "area_code": 768,
    "post_code": 515700,
    "pid": 445100,
    "id": 2149,
    "area_id": 445122
  },
  {
    "area_name": "揭阳市",
    "pinyin": "Jieyang",
    "level": 2,
    "area_code": 633,
    "post_code": 522000,
    "pid": 440000,
    "id": 2150,
    "area_id": 445200
  },
  {
    "area_name": "榕城区",
    "pinyin": "Rongcheng",
    "level": 3,
    "area_code": 633,
    "post_code": 522000,
    "pid": 445200,
    "id": 2151,
    "area_id": 445202
  },
  {
    "area_name": "揭东区",
    "pinyin": "Jiedong",
    "level": 3,
    "area_code": 633,
    "post_code": 515500,
    "pid": 445200,
    "id": 2152,
    "area_id": 445203
  },
  {
    "area_name": "揭西县",
    "pinyin": "Jiexi",
    "level": 3,
    "area_code": 633,
    "post_code": 515400,
    "pid": 445200,
    "id": 2153,
    "area_id": 445222
  },
  {
    "area_name": "惠来县",
    "pinyin": "Huilai",
    "level": 3,
    "area_code": 633,
    "post_code": 515200,
    "pid": 445200,
    "id": 2154,
    "area_id": 445224
  },
  {
    "area_name": "普宁市",
    "pinyin": "Puning",
    "level": 3,
    "area_code": 633,
    "post_code": 515300,
    "pid": 445200,
    "id": 2155,
    "area_id": 445281
  },
  {
    "area_name": "云浮市",
    "pinyin": "Yunfu",
    "level": 2,
    "area_code": 766,
    "post_code": 527300,
    "pid": 440000,
    "id": 2156,
    "area_id": 445300
  },
  {
    "area_name": "云城区",
    "pinyin": "Yuncheng",
    "level": 3,
    "area_code": 766,
    "post_code": 527300,
    "pid": 445300,
    "id": 2157,
    "area_id": 445302
  },
  {
    "area_name": "云安区",
    "pinyin": "Yun'an",
    "level": 3,
    "area_code": 766,
    "post_code": 527500,
    "pid": 445300,
    "id": 2158,
    "area_id": 445303
  },
  {
    "area_name": "新兴县",
    "pinyin": "Xinxing",
    "level": 3,
    "area_code": 766,
    "post_code": 527400,
    "pid": 445300,
    "id": 2159,
    "area_id": 445321
  },
  {
    "area_name": "郁南县",
    "pinyin": "Yunan",
    "level": 3,
    "area_code": 766,
    "post_code": 527100,
    "pid": 445300,
    "id": 2160,
    "area_id": 445322
  },
  {
    "area_name": "罗定市",
    "pinyin": "Luoding",
    "level": 3,
    "area_code": 766,
    "post_code": 527200,
    "pid": 445300,
    "id": 2161,
    "area_id": 445381
  },
  {
    "area_name": "广西壮族自治区",
    "pinyin": "Guangxi",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2162,
    "area_id": 450000
  },
  {
    "area_name": "南宁市",
    "pinyin": "Nanning",
    "level": 2,
    "area_code": 771,
    "post_code": 530028,
    "pid": 450000,
    "id": 2163,
    "area_id": 450100
  },
  {
    "area_name": "兴宁区",
    "pinyin": "Xingning",
    "level": 3,
    "area_code": 771,
    "post_code": 530023,
    "pid": 450100,
    "id": 2164,
    "area_id": 450102
  },
  {
    "area_name": "青秀区",
    "pinyin": "Qingxiu",
    "level": 3,
    "area_code": 771,
    "post_code": 530213,
    "pid": 450100,
    "id": 2165,
    "area_id": 450103
  },
  {
    "area_name": "江南区",
    "pinyin": "Jiangnan",
    "level": 3,
    "area_code": 771,
    "post_code": 530031,
    "pid": 450100,
    "id": 2166,
    "area_id": 450105
  },
  {
    "area_name": "西乡塘区",
    "pinyin": "Xixiangtang",
    "level": 3,
    "area_code": 771,
    "post_code": 530001,
    "pid": 450100,
    "id": 2167,
    "area_id": 450107
  },
  {
    "area_name": "良庆区",
    "pinyin": "Liangqing",
    "level": 3,
    "area_code": 771,
    "post_code": 530219,
    "pid": 450100,
    "id": 2168,
    "area_id": 450108
  },
  {
    "area_name": "邕宁区",
    "pinyin": "Yongning",
    "level": 3,
    "area_code": 771,
    "post_code": 530200,
    "pid": 450100,
    "id": 2169,
    "area_id": 450109
  },
  {
    "area_name": "武鸣县",
    "pinyin": "Wuming",
    "level": 3,
    "area_code": 771,
    "post_code": 530100,
    "pid": 450100,
    "id": 2170,
    "area_id": 450122
  },
  {
    "area_name": "隆安县",
    "pinyin": "Long'an",
    "level": 3,
    "area_code": 771,
    "post_code": 532700,
    "pid": 450100,
    "id": 2171,
    "area_id": 450123
  },
  {
    "area_name": "马山县",
    "pinyin": "Mashan",
    "level": 3,
    "area_code": 771,
    "post_code": 530600,
    "pid": 450100,
    "id": 2172,
    "area_id": 450124
  },
  {
    "area_name": "上林县",
    "pinyin": "Shanglin",
    "level": 3,
    "area_code": 771,
    "post_code": 530500,
    "pid": 450100,
    "id": 2173,
    "area_id": 450125
  },
  {
    "area_name": "宾阳县",
    "pinyin": "Binyang",
    "level": 3,
    "area_code": 771,
    "post_code": 530400,
    "pid": 450100,
    "id": 2174,
    "area_id": 450126
  },
  {
    "area_name": "横县",
    "pinyin": "Hengxian",
    "level": 3,
    "area_code": 771,
    "post_code": 530300,
    "pid": 450100,
    "id": 2175,
    "area_id": 450127
  },
  {
    "area_name": "埌东新区",
    "pinyin": "Langdong",
    "level": 3,
    "area_code": 771,
    "post_code": 530000,
    "pid": 450100,
    "id": 2176,
    "area_id": 450128
  },
  {
    "area_name": "柳州市",
    "pinyin": "Liuzhou",
    "level": 2,
    "area_code": 772,
    "post_code": 545001,
    "pid": 450000,
    "id": 2177,
    "area_id": 450200
  },
  {
    "area_name": "城中区",
    "pinyin": "Chengzhong",
    "level": 3,
    "area_code": 772,
    "post_code": 545001,
    "pid": 450200,
    "id": 2178,
    "area_id": 450202
  },
  {
    "area_name": "鱼峰区",
    "pinyin": "Yufeng",
    "level": 3,
    "area_code": 772,
    "post_code": 545005,
    "pid": 450200,
    "id": 2179,
    "area_id": 450203
  },
  {
    "area_name": "柳南区",
    "pinyin": "Liunan",
    "level": 3,
    "area_code": 772,
    "post_code": 545007,
    "pid": 450200,
    "id": 2180,
    "area_id": 450204
  },
  {
    "area_name": "柳北区",
    "pinyin": "Liubei",
    "level": 3,
    "area_code": 772,
    "post_code": 545002,
    "pid": 450200,
    "id": 2181,
    "area_id": 450205
  },
  {
    "area_name": "柳江县",
    "pinyin": "Liujiang",
    "level": 3,
    "area_code": 772,
    "post_code": 545100,
    "pid": 450200,
    "id": 2182,
    "area_id": 450221
  },
  {
    "area_name": "柳城县",
    "pinyin": "Liucheng",
    "level": 3,
    "area_code": 772,
    "post_code": 545200,
    "pid": 450200,
    "id": 2183,
    "area_id": 450222
  },
  {
    "area_name": "鹿寨县",
    "pinyin": "Luzhai",
    "level": 3,
    "area_code": 772,
    "post_code": 545600,
    "pid": 450200,
    "id": 2184,
    "area_id": 450223
  },
  {
    "area_name": "融安县",
    "pinyin": "Rong'an",
    "level": 3,
    "area_code": 772,
    "post_code": 545400,
    "pid": 450200,
    "id": 2185,
    "area_id": 450224
  },
  {
    "area_name": "融水苗族自治县",
    "pinyin": "Rongshui",
    "level": 3,
    "area_code": 772,
    "post_code": 545300,
    "pid": 450200,
    "id": 2186,
    "area_id": 450225
  },
  {
    "area_name": "三江侗族自治县",
    "pinyin": "Sanjiang",
    "level": 3,
    "area_code": 772,
    "post_code": 545500,
    "pid": 450200,
    "id": 2187,
    "area_id": 450226
  },
  {
    "area_name": "柳东新区",
    "pinyin": "Liudong",
    "level": 3,
    "area_code": 772,
    "post_code": 545000,
    "pid": 450200,
    "id": 2188,
    "area_id": 450227
  },
  {
    "area_name": "桂林市",
    "pinyin": "Guilin",
    "level": 2,
    "area_code": 773,
    "post_code": 541100,
    "pid": 450000,
    "id": 2189,
    "area_id": 450300
  },
  {
    "area_name": "秀峰区",
    "pinyin": "Xiufeng",
    "level": 3,
    "area_code": 773,
    "post_code": 541001,
    "pid": 450300,
    "id": 2190,
    "area_id": 450302
  },
  {
    "area_name": "叠彩区",
    "pinyin": "Diecai",
    "level": 3,
    "area_code": 773,
    "post_code": 541001,
    "pid": 450300,
    "id": 2191,
    "area_id": 450303
  },
  {
    "area_name": "象山区",
    "pinyin": "Xiangshan",
    "level": 3,
    "area_code": 773,
    "post_code": 541002,
    "pid": 450300,
    "id": 2192,
    "area_id": 450304
  },
  {
    "area_name": "七星区",
    "pinyin": "Qixing",
    "level": 3,
    "area_code": 773,
    "post_code": 541004,
    "pid": 450300,
    "id": 2193,
    "area_id": 450305
  },
  {
    "area_name": "雁山区",
    "pinyin": "Yanshan",
    "level": 3,
    "area_code": 773,
    "post_code": 541006,
    "pid": 450300,
    "id": 2194,
    "area_id": 450311
  },
  {
    "area_name": "临桂区",
    "pinyin": "Lingui",
    "level": 3,
    "area_code": 773,
    "post_code": 541100,
    "pid": 450300,
    "id": 2195,
    "area_id": 450312
  },
  {
    "area_name": "阳朔县",
    "pinyin": "Yangshuo",
    "level": 3,
    "area_code": 773,
    "post_code": 541900,
    "pid": 450300,
    "id": 2196,
    "area_id": 450321
  },
  {
    "area_name": "灵川县",
    "pinyin": "Lingchuan",
    "level": 3,
    "area_code": 773,
    "post_code": 541200,
    "pid": 450300,
    "id": 2197,
    "area_id": 450323
  },
  {
    "area_name": "全州县",
    "pinyin": "Quanzhou",
    "level": 3,
    "area_code": 773,
    "post_code": 541503,
    "pid": 450300,
    "id": 2198,
    "area_id": 450324
  },
  {
    "area_name": "兴安县",
    "pinyin": "Xing'an",
    "level": 3,
    "area_code": 773,
    "post_code": 541300,
    "pid": 450300,
    "id": 2199,
    "area_id": 450325
  },
  {
    "area_name": "永福县",
    "pinyin": "Yongfu",
    "level": 3,
    "area_code": 773,
    "post_code": 541800,
    "pid": 450300,
    "id": 2200,
    "area_id": 450326
  },
  {
    "area_name": "灌阳县",
    "pinyin": "Guanyang",
    "level": 3,
    "area_code": 773,
    "post_code": 541600,
    "pid": 450300,
    "id": 2201,
    "area_id": 450327
  },
  {
    "area_name": "龙胜各族自治县",
    "pinyin": "Longsheng",
    "level": 3,
    "area_code": 773,
    "post_code": 541700,
    "pid": 450300,
    "id": 2202,
    "area_id": 450328
  },
  {
    "area_name": "资源县",
    "pinyin": "Ziyuan",
    "level": 3,
    "area_code": 773,
    "post_code": 541400,
    "pid": 450300,
    "id": 2203,
    "area_id": 450329
  },
  {
    "area_name": "平乐县",
    "pinyin": "Pingle",
    "level": 3,
    "area_code": 773,
    "post_code": 542400,
    "pid": 450300,
    "id": 2204,
    "area_id": 450330
  },
  {
    "area_name": "荔浦县",
    "pinyin": "Lipu",
    "level": 3,
    "area_code": 773,
    "post_code": 546600,
    "pid": 450300,
    "id": 2205,
    "area_id": 450331
  },
  {
    "area_name": "恭城瑶族自治县",
    "pinyin": "Gongcheng",
    "level": 3,
    "area_code": 773,
    "post_code": 542500,
    "pid": 450300,
    "id": 2206,
    "area_id": 450332
  },
  {
    "area_name": "梧州市",
    "pinyin": "Wuzhou",
    "level": 2,
    "area_code": 774,
    "post_code": 543002,
    "pid": 450000,
    "id": 2207,
    "area_id": 450400
  },
  {
    "area_name": "万秀区",
    "pinyin": "Wanxiu",
    "level": 3,
    "area_code": 774,
    "post_code": 543000,
    "pid": 450400,
    "id": 2208,
    "area_id": 450403
  },
  {
    "area_name": "长洲区",
    "pinyin": "Changzhou",
    "level": 3,
    "area_code": 774,
    "post_code": 543003,
    "pid": 450400,
    "id": 2209,
    "area_id": 450405
  },
  {
    "area_name": "龙圩区",
    "pinyin": "Longxu",
    "level": 3,
    "area_code": 774,
    "post_code": 543002,
    "pid": 450400,
    "id": 2210,
    "area_id": 450406
  },
  {
    "area_name": "苍梧县",
    "pinyin": "Cangwu",
    "level": 3,
    "area_code": 774,
    "post_code": 543100,
    "pid": 450400,
    "id": 2211,
    "area_id": 450421
  },
  {
    "area_name": "藤县",
    "pinyin": "Tengxian",
    "level": 3,
    "area_code": 774,
    "post_code": 543300,
    "pid": 450400,
    "id": 2212,
    "area_id": 450422
  },
  {
    "area_name": "蒙山县",
    "pinyin": "Mengshan",
    "level": 3,
    "area_code": 774,
    "post_code": 546700,
    "pid": 450400,
    "id": 2213,
    "area_id": 450423
  },
  {
    "area_name": "岑溪市",
    "pinyin": "Cenxi",
    "level": 3,
    "area_code": 774,
    "post_code": 543200,
    "pid": 450400,
    "id": 2214,
    "area_id": 450481
  },
  {
    "area_name": "北海市",
    "pinyin": "Beihai",
    "level": 2,
    "area_code": 779,
    "post_code": 536000,
    "pid": 450000,
    "id": 2215,
    "area_id": 450500
  },
  {
    "area_name": "海城区",
    "pinyin": "Haicheng",
    "level": 3,
    "area_code": 779,
    "post_code": 536000,
    "pid": 450500,
    "id": 2216,
    "area_id": 450502
  },
  {
    "area_name": "银海区",
    "pinyin": "Yinhai",
    "level": 3,
    "area_code": 779,
    "post_code": 536000,
    "pid": 450500,
    "id": 2217,
    "area_id": 450503
  },
  {
    "area_name": "铁山港区",
    "pinyin": "Tieshangang",
    "level": 3,
    "area_code": 779,
    "post_code": 536017,
    "pid": 450500,
    "id": 2218,
    "area_id": 450512
  },
  {
    "area_name": "合浦县",
    "pinyin": "Hepu",
    "level": 3,
    "area_code": 779,
    "post_code": 536100,
    "pid": 450500,
    "id": 2219,
    "area_id": 450521
  },
  {
    "area_name": "防城港市",
    "pinyin": "Fangchenggang",
    "level": 2,
    "area_code": 770,
    "post_code": 538001,
    "pid": 450000,
    "id": 2220,
    "area_id": 450600
  },
  {
    "area_name": "港口区",
    "pinyin": "Gangkou",
    "level": 3,
    "area_code": 770,
    "post_code": 538001,
    "pid": 450600,
    "id": 2221,
    "area_id": 450602
  },
  {
    "area_name": "防城区",
    "pinyin": "Fangcheng",
    "level": 3,
    "area_code": 770,
    "post_code": 538021,
    "pid": 450600,
    "id": 2222,
    "area_id": 450603
  },
  {
    "area_name": "上思县",
    "pinyin": "Shangsi",
    "level": 3,
    "area_code": 770,
    "post_code": 535500,
    "pid": 450600,
    "id": 2223,
    "area_id": 450621
  },
  {
    "area_name": "东兴市",
    "pinyin": "Dongxing",
    "level": 3,
    "area_code": 770,
    "post_code": 538100,
    "pid": 450600,
    "id": 2224,
    "area_id": 450681
  },
  {
    "area_name": "钦州市",
    "pinyin": "Qinzhou",
    "level": 2,
    "area_code": 777,
    "post_code": 535099,
    "pid": 450000,
    "id": 2225,
    "area_id": 450700
  },
  {
    "area_name": "钦南区",
    "pinyin": "Qinnan",
    "level": 3,
    "area_code": 777,
    "post_code": 535099,
    "pid": 450700,
    "id": 2226,
    "area_id": 450702
  },
  {
    "area_name": "钦北区",
    "pinyin": "Qinbei",
    "level": 3,
    "area_code": 777,
    "post_code": 535099,
    "pid": 450700,
    "id": 2227,
    "area_id": 450703
  },
  {
    "area_name": "灵山县",
    "pinyin": "Lingshan",
    "level": 3,
    "area_code": 777,
    "post_code": 535099,
    "pid": 450700,
    "id": 2228,
    "area_id": 450721
  },
  {
    "area_name": "浦北县",
    "pinyin": "Pubei",
    "level": 3,
    "area_code": 777,
    "post_code": 535099,
    "pid": 450700,
    "id": 2229,
    "area_id": 450722
  },
  {
    "area_name": "贵港市",
    "pinyin": "Guigang",
    "level": 2,
    "area_code": 775,
    "post_code": 537100,
    "pid": 450000,
    "id": 2230,
    "area_id": 450800
  },
  {
    "area_name": "港北区",
    "pinyin": "Gangbei",
    "level": 3,
    "area_code": 775,
    "post_code": 537100,
    "pid": 450800,
    "id": 2231,
    "area_id": 450802
  },
  {
    "area_name": "港南区",
    "pinyin": "Gangnan",
    "level": 3,
    "area_code": 775,
    "post_code": 537100,
    "pid": 450800,
    "id": 2232,
    "area_id": 450803
  },
  {
    "area_name": "覃塘区",
    "pinyin": "Qintang",
    "level": 3,
    "area_code": 775,
    "post_code": 537121,
    "pid": 450800,
    "id": 2233,
    "area_id": 450804
  },
  {
    "area_name": "平南县",
    "pinyin": "Pingnan",
    "level": 3,
    "area_code": 775,
    "post_code": 537300,
    "pid": 450800,
    "id": 2234,
    "area_id": 450821
  },
  {
    "area_name": "桂平市",
    "pinyin": "Guiping",
    "level": 3,
    "area_code": 775,
    "post_code": 537200,
    "pid": 450800,
    "id": 2235,
    "area_id": 450881
  },
  {
    "area_name": "玉林市",
    "pinyin": "Yulin",
    "level": 2,
    "area_code": 775,
    "post_code": 537000,
    "pid": 450000,
    "id": 2236,
    "area_id": 450900
  },
  {
    "area_name": "玉州区",
    "pinyin": "Yuzhou",
    "level": 3,
    "area_code": 775,
    "post_code": 537000,
    "pid": 450900,
    "id": 2237,
    "area_id": 450902
  },
  {
    "area_name": "福绵区",
    "pinyin": "Fumian",
    "level": 3,
    "area_code": 775,
    "post_code": 537023,
    "pid": 450900,
    "id": 2238,
    "area_id": 450903
  },
  {
    "area_name": "玉东新区",
    "pinyin": "Yudong",
    "level": 3,
    "area_code": 775,
    "post_code": 537000,
    "pid": 450900,
    "id": 2239,
    "area_id": 450904
  },
  {
    "area_name": "容县",
    "pinyin": "Rongxian",
    "level": 3,
    "area_code": 775,
    "post_code": 537500,
    "pid": 450900,
    "id": 2240,
    "area_id": 450921
  },
  {
    "area_name": "陆川县",
    "pinyin": "Luchuan",
    "level": 3,
    "area_code": 775,
    "post_code": 537700,
    "pid": 450900,
    "id": 2241,
    "area_id": 450922
  },
  {
    "area_name": "博白县",
    "pinyin": "Bobai",
    "level": 3,
    "area_code": 775,
    "post_code": 537600,
    "pid": 450900,
    "id": 2242,
    "area_id": 450923
  },
  {
    "area_name": "兴业县",
    "pinyin": "Xingye",
    "level": 3,
    "area_code": 775,
    "post_code": 537800,
    "pid": 450900,
    "id": 2243,
    "area_id": 450924
  },
  {
    "area_name": "北流市",
    "pinyin": "Beiliu",
    "level": 3,
    "area_code": 775,
    "post_code": 537400,
    "pid": 450900,
    "id": 2244,
    "area_id": 450981
  },
  {
    "area_name": "百色市",
    "pinyin": "Baise",
    "level": 2,
    "area_code": 776,
    "post_code": 533000,
    "pid": 450000,
    "id": 2245,
    "area_id": 451000
  },
  {
    "area_name": "右江区",
    "pinyin": "Youjiang",
    "level": 3,
    "area_code": 776,
    "post_code": 533000,
    "pid": 451000,
    "id": 2246,
    "area_id": 451002
  },
  {
    "area_name": "田阳县",
    "pinyin": "Tianyang",
    "level": 3,
    "area_code": 776,
    "post_code": 533600,
    "pid": 451000,
    "id": 2247,
    "area_id": 451021
  },
  {
    "area_name": "田东县",
    "pinyin": "Tiandong",
    "level": 3,
    "area_code": 776,
    "post_code": 531500,
    "pid": 451000,
    "id": 2248,
    "area_id": 451022
  },
  {
    "area_name": "平果县",
    "pinyin": "Pingguo",
    "level": 3,
    "area_code": 776,
    "post_code": 531400,
    "pid": 451000,
    "id": 2249,
    "area_id": 451023
  },
  {
    "area_name": "德保县",
    "pinyin": "Debao",
    "level": 3,
    "area_code": 776,
    "post_code": 533700,
    "pid": 451000,
    "id": 2250,
    "area_id": 451024
  },
  {
    "area_name": "靖西县",
    "pinyin": "Jingxi",
    "level": 3,
    "area_code": 776,
    "post_code": 533800,
    "pid": 451000,
    "id": 2251,
    "area_id": 451025
  },
  {
    "area_name": "那坡县",
    "pinyin": "Napo",
    "level": 3,
    "area_code": 776,
    "post_code": 533900,
    "pid": 451000,
    "id": 2252,
    "area_id": 451026
  },
  {
    "area_name": "凌云县",
    "pinyin": "Lingyun",
    "level": 3,
    "area_code": 776,
    "post_code": 533100,
    "pid": 451000,
    "id": 2253,
    "area_id": 451027
  },
  {
    "area_name": "乐业县",
    "pinyin": "Leye",
    "level": 3,
    "area_code": 776,
    "post_code": 533200,
    "pid": 451000,
    "id": 2254,
    "area_id": 451028
  },
  {
    "area_name": "田林县",
    "pinyin": "Tianlin",
    "level": 3,
    "area_code": 776,
    "post_code": 533300,
    "pid": 451000,
    "id": 2255,
    "area_id": 451029
  },
  {
    "area_name": "西林县",
    "pinyin": "Xilin",
    "level": 3,
    "area_code": 776,
    "post_code": 533500,
    "pid": 451000,
    "id": 2256,
    "area_id": 451030
  },
  {
    "area_name": "隆林各族自治县",
    "pinyin": "Longlin",
    "level": 3,
    "area_code": 776,
    "post_code": 533400,
    "pid": 451000,
    "id": 2257,
    "area_id": 451031
  },
  {
    "area_name": "贺州市",
    "pinyin": "Hezhou",
    "level": 2,
    "area_code": 774,
    "post_code": 542800,
    "pid": 450000,
    "id": 2258,
    "area_id": 451100
  },
  {
    "area_name": "八步区",
    "pinyin": "Babu",
    "level": 3,
    "area_code": 774,
    "post_code": 542800,
    "pid": 451100,
    "id": 2259,
    "area_id": 451102
  },
  {
    "area_name": "昭平县",
    "pinyin": "Zhaoping",
    "level": 3,
    "area_code": 774,
    "post_code": 546800,
    "pid": 451100,
    "id": 2260,
    "area_id": 451121
  },
  {
    "area_name": "钟山县",
    "pinyin": "Zhongshan",
    "level": 3,
    "area_code": 774,
    "post_code": 542600,
    "pid": 451100,
    "id": 2261,
    "area_id": 451122
  },
  {
    "area_name": "富川瑶族自治县",
    "pinyin": "Fuchuan",
    "level": 3,
    "area_code": 774,
    "post_code": 542700,
    "pid": 451100,
    "id": 2262,
    "area_id": 451123
  },
  {
    "area_name": "平桂管理区",
    "pinyin": "Pingui",
    "level": 3,
    "area_code": 774,
    "post_code": 542800,
    "pid": 451100,
    "id": 2263,
    "area_id": 451124
  },
  {
    "area_name": "河池市",
    "pinyin": "Hechi",
    "level": 2,
    "area_code": 778,
    "post_code": 547000,
    "pid": 450000,
    "id": 2264,
    "area_id": 451200
  },
  {
    "area_name": "金城江区",
    "pinyin": "Jinchengjiang",
    "level": 3,
    "area_code": 779,
    "post_code": 547000,
    "pid": 451200,
    "id": 2265,
    "area_id": 451202
  },
  {
    "area_name": "南丹县",
    "pinyin": "Nandan",
    "level": 3,
    "area_code": 781,
    "post_code": 547200,
    "pid": 451200,
    "id": 2266,
    "area_id": 451221
  },
  {
    "area_name": "天峨县",
    "pinyin": "Tiane",
    "level": 3,
    "area_code": 782,
    "post_code": 547300,
    "pid": 451200,
    "id": 2267,
    "area_id": 451222
  },
  {
    "area_name": "凤山县",
    "pinyin": "Fengshan",
    "level": 3,
    "area_code": 783,
    "post_code": 547600,
    "pid": 451200,
    "id": 2268,
    "area_id": 451223
  },
  {
    "area_name": "东兰县",
    "pinyin": "Donglan",
    "level": 3,
    "area_code": 784,
    "post_code": 547400,
    "pid": 451200,
    "id": 2269,
    "area_id": 451224
  },
  {
    "area_name": "罗城仫佬族自治县",
    "pinyin": "Luocheng",
    "level": 3,
    "area_code": 785,
    "post_code": 546400,
    "pid": 451200,
    "id": 2270,
    "area_id": 451225
  },
  {
    "area_name": "环江毛南族自治县",
    "pinyin": "Huanjiang",
    "level": 3,
    "area_code": 786,
    "post_code": 547100,
    "pid": 451200,
    "id": 2271,
    "area_id": 451226
  },
  {
    "area_name": "巴马瑶族自治县",
    "pinyin": "Bama",
    "level": 3,
    "area_code": 787,
    "post_code": 547500,
    "pid": 451200,
    "id": 2272,
    "area_id": 451227
  },
  {
    "area_name": "都安瑶族自治县",
    "pinyin": "Du'an",
    "level": 3,
    "area_code": 788,
    "post_code": 530700,
    "pid": 451200,
    "id": 2273,
    "area_id": 451228
  },
  {
    "area_name": "大化瑶族自治县",
    "pinyin": "Dahua",
    "level": 3,
    "area_code": 789,
    "post_code": 530800,
    "pid": 451200,
    "id": 2274,
    "area_id": 451229
  },
  {
    "area_name": "宜州市",
    "pinyin": "Yizhou",
    "level": 3,
    "area_code": 780,
    "post_code": 546300,
    "pid": 451200,
    "id": 2275,
    "area_id": 451281
  },
  {
    "area_name": "来宾市",
    "pinyin": "Laibin",
    "level": 2,
    "area_code": 772,
    "post_code": 546100,
    "pid": 450000,
    "id": 2276,
    "area_id": 451300
  },
  {
    "area_name": "兴宾区",
    "pinyin": "Xingbin",
    "level": 3,
    "area_code": 772,
    "post_code": 546100,
    "pid": 451300,
    "id": 2277,
    "area_id": 451302
  },
  {
    "area_name": "忻城县",
    "pinyin": "Xincheng",
    "level": 3,
    "area_code": 772,
    "post_code": 546200,
    "pid": 451300,
    "id": 2278,
    "area_id": 451321
  },
  {
    "area_name": "象州县",
    "pinyin": "Xiangzhou",
    "level": 3,
    "area_code": 772,
    "post_code": 545800,
    "pid": 451300,
    "id": 2279,
    "area_id": 451322
  },
  {
    "area_name": "武宣县",
    "pinyin": "Wuxuan",
    "level": 3,
    "area_code": 772,
    "post_code": 545900,
    "pid": 451300,
    "id": 2280,
    "area_id": 451323
  },
  {
    "area_name": "金秀瑶族自治县",
    "pinyin": "Jinxiu",
    "level": 3,
    "area_code": 772,
    "post_code": 545799,
    "pid": 451300,
    "id": 2281,
    "area_id": 451324
  },
  {
    "area_name": "合山市",
    "pinyin": "Heshan",
    "level": 3,
    "area_code": 772,
    "post_code": 546500,
    "pid": 451300,
    "id": 2282,
    "area_id": 451381
  },
  {
    "area_name": "崇左市",
    "pinyin": "Chongzuo",
    "level": 2,
    "area_code": 771,
    "post_code": 532299,
    "pid": 450000,
    "id": 2283,
    "area_id": 451400
  },
  {
    "area_name": "江州区",
    "pinyin": "Jiangzhou",
    "level": 3,
    "area_code": 771,
    "post_code": 532299,
    "pid": 451400,
    "id": 2284,
    "area_id": 451402
  },
  {
    "area_name": "扶绥县",
    "pinyin": "Fusui",
    "level": 3,
    "area_code": 771,
    "post_code": 532199,
    "pid": 451400,
    "id": 2285,
    "area_id": 451421
  },
  {
    "area_name": "宁明县",
    "pinyin": "Ningming",
    "level": 3,
    "area_code": 771,
    "post_code": 532599,
    "pid": 451400,
    "id": 2286,
    "area_id": 451422
  },
  {
    "area_name": "龙州县",
    "pinyin": "Longzhou",
    "level": 3,
    "area_code": 771,
    "post_code": 532499,
    "pid": 451400,
    "id": 2287,
    "area_id": 451423
  },
  {
    "area_name": "大新县",
    "pinyin": "Daxin",
    "level": 3,
    "area_code": 771,
    "post_code": 532399,
    "pid": 451400,
    "id": 2288,
    "area_id": 451424
  },
  {
    "area_name": "天等县",
    "pinyin": "Tiandeng",
    "level": 3,
    "area_code": 771,
    "post_code": 532899,
    "pid": 451400,
    "id": 2289,
    "area_id": 451425
  },
  {
    "area_name": "凭祥市",
    "pinyin": "Pingxiang",
    "level": 3,
    "area_code": 771,
    "post_code": 532699,
    "pid": 451400,
    "id": 2290,
    "area_id": 451481
  },
  {
    "area_name": "海南省",
    "pinyin": "Hainan",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2291,
    "area_id": 460000
  },
  {
    "area_name": "海口市",
    "pinyin": "Haikou",
    "level": 2,
    "area_code": 898,
    "post_code": 570000,
    "pid": 460000,
    "id": 2292,
    "area_id": 460100
  },
  {
    "area_name": "秀英区",
    "pinyin": "Xiuying",
    "level": 3,
    "area_code": 898,
    "post_code": 570311,
    "pid": 460100,
    "id": 2293,
    "area_id": 460105
  },
  {
    "area_name": "龙华区",
    "pinyin": "Longhua",
    "level": 3,
    "area_code": 898,
    "post_code": 570145,
    "pid": 460100,
    "id": 2294,
    "area_id": 460106
  },
  {
    "area_name": "琼山区",
    "pinyin": "Qiongshan",
    "level": 3,
    "area_code": 898,
    "post_code": 571100,
    "pid": 460100,
    "id": 2295,
    "area_id": 460107
  },
  {
    "area_name": "美兰区",
    "pinyin": "Meilan",
    "level": 3,
    "area_code": 898,
    "post_code": 570203,
    "pid": 460100,
    "id": 2296,
    "area_id": 460108
  },
  {
    "area_name": "三亚市",
    "pinyin": "Sanya",
    "level": 2,
    "area_code": 898,
    "post_code": 572000,
    "pid": 460000,
    "id": 2297,
    "area_id": 460200
  },
  {
    "area_name": "海棠区",
    "pinyin": "Haitang",
    "level": 3,
    "area_code": 898,
    "post_code": 572000,
    "pid": 460200,
    "id": 2298,
    "area_id": 460202
  },
  {
    "area_name": "吉阳区",
    "pinyin": "Jiyang",
    "level": 3,
    "area_code": 898,
    "post_code": 572000,
    "pid": 460200,
    "id": 2299,
    "area_id": 460203
  },
  {
    "area_name": "天涯区",
    "pinyin": "Tianya",
    "level": 3,
    "area_code": 898,
    "post_code": 572000,
    "pid": 460200,
    "id": 2300,
    "area_id": 460204
  },
  {
    "area_name": "崖州区",
    "pinyin": "Yazhou",
    "level": 3,
    "area_code": 898,
    "post_code": 572000,
    "pid": 460200,
    "id": 2301,
    "area_id": 460205
  },
  {
    "area_name": "三沙市",
    "pinyin": "Sansha",
    "level": 2,
    "area_code": 898,
    "post_code": 573199,
    "pid": 460000,
    "id": 2302,
    "area_id": 460300
  },
  {
    "area_name": "西沙群岛",
    "pinyin": "Xisha Islands",
    "level": 3,
    "area_code": 898,
    "post_code": 572000,
    "pid": 460300,
    "id": 2303,
    "area_id": 460321
  },
  {
    "area_name": "南沙群岛",
    "pinyin": "Nansha Islands",
    "level": 3,
    "area_code": 898,
    "post_code": 573100,
    "pid": 460300,
    "id": 2304,
    "area_id": 460322
  },
  {
    "area_name": "中沙群岛",
    "pinyin": "Zhongsha Islands",
    "level": 3,
    "area_code": 898,
    "post_code": 573100,
    "pid": 460300,
    "id": 2305,
    "area_id": 460323
  },
  {
    "area_name": "直辖县级",
    "pinyin": "",
    "level": 2,
    "area_code": null,
    "post_code": null,
    "pid": 460000,
    "id": 2306,
    "area_id": 469000
  },
  {
    "area_name": "五指山市",
    "pinyin": "Wuzhishan",
    "level": 3,
    "area_code": 898,
    "post_code": 572200,
    "pid": 469000,
    "id": 2307,
    "area_id": 469001
  },
  {
    "area_name": "琼海市",
    "pinyin": "Qionghai",
    "level": 3,
    "area_code": 898,
    "post_code": 571400,
    "pid": 469000,
    "id": 2308,
    "area_id": 469002
  },
  {
    "area_name": "儋州市",
    "pinyin": "Danzhou",
    "level": 3,
    "area_code": 898,
    "post_code": 571700,
    "pid": 469000,
    "id": 2309,
    "area_id": 469003
  },
  {
    "area_name": "文昌市",
    "pinyin": "Wenchang",
    "level": 3,
    "area_code": 898,
    "post_code": 571339,
    "pid": 469000,
    "id": 2310,
    "area_id": 469005
  },
  {
    "area_name": "万宁市",
    "pinyin": "Wanning",
    "level": 3,
    "area_code": 898,
    "post_code": 571500,
    "pid": 469000,
    "id": 2311,
    "area_id": 469006
  },
  {
    "area_name": "东方市",
    "pinyin": "Dongfang",
    "level": 3,
    "area_code": 898,
    "post_code": 572600,
    "pid": 469000,
    "id": 2312,
    "area_id": 469007
  },
  {
    "area_name": "定安县",
    "pinyin": "Ding'an",
    "level": 3,
    "area_code": 898,
    "post_code": 571200,
    "pid": 469000,
    "id": 2313,
    "area_id": 469021
  },
  {
    "area_name": "屯昌县",
    "pinyin": "Tunchang",
    "level": 3,
    "area_code": 898,
    "post_code": 571600,
    "pid": 469000,
    "id": 2314,
    "area_id": 469022
  },
  {
    "area_name": "澄迈县",
    "pinyin": "Chengmai",
    "level": 3,
    "area_code": 898,
    "post_code": 571900,
    "pid": 469000,
    "id": 2315,
    "area_id": 469023
  },
  {
    "area_name": "临高县",
    "pinyin": "Lingao",
    "level": 3,
    "area_code": 898,
    "post_code": 571800,
    "pid": 469000,
    "id": 2316,
    "area_id": 469024
  },
  {
    "area_name": "白沙黎族自治县",
    "pinyin": "Baisha",
    "level": 3,
    "area_code": 898,
    "post_code": 572800,
    "pid": 469000,
    "id": 2317,
    "area_id": 469025
  },
  {
    "area_name": "昌江黎族自治县",
    "pinyin": "Changjiang",
    "level": 3,
    "area_code": 898,
    "post_code": 572700,
    "pid": 469000,
    "id": 2318,
    "area_id": 469026
  },
  {
    "area_name": "乐东黎族自治县",
    "pinyin": "Ledong",
    "level": 3,
    "area_code": 898,
    "post_code": 572500,
    "pid": 469000,
    "id": 2319,
    "area_id": 469027
  },
  {
    "area_name": "陵水黎族自治县",
    "pinyin": "Lingshui",
    "level": 3,
    "area_code": 898,
    "post_code": 572400,
    "pid": 469000,
    "id": 2320,
    "area_id": 469028
  },
  {
    "area_name": "保亭黎族苗族自治县",
    "pinyin": "Baoting",
    "level": 3,
    "area_code": 898,
    "post_code": 572300,
    "pid": 469000,
    "id": 2321,
    "area_id": 469029
  },
  {
    "area_name": "琼中黎族苗族自治县",
    "pinyin": "Qiongzhong",
    "level": 3,
    "area_code": 898,
    "post_code": 572900,
    "pid": 469000,
    "id": 2322,
    "area_id": 469030
  },
  {
    "area_name": "重庆",
    "pinyin": "Chongqing",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2323,
    "area_id": 500000
  },
  {
    "area_name": "重庆市",
    "pinyin": "Chongqing",
    "level": 2,
    "area_code": 23,
    "post_code": 400000,
    "pid": 500000,
    "id": 2324,
    "area_id": 500100
  },
  {
    "area_name": "万州区",
    "pinyin": "Wanzhou",
    "level": 3,
    "area_code": 23,
    "post_code": 404000,
    "pid": 500100,
    "id": 2325,
    "area_id": 500101
  },
  {
    "area_name": "涪陵区",
    "pinyin": "Fuling",
    "level": 3,
    "area_code": 23,
    "post_code": 408000,
    "pid": 500100,
    "id": 2326,
    "area_id": 500102
  },
  {
    "area_name": "渝中区",
    "pinyin": "Yuzhong",
    "level": 3,
    "area_code": 23,
    "post_code": 400010,
    "pid": 500100,
    "id": 2327,
    "area_id": 500103
  },
  {
    "area_name": "大渡口区",
    "pinyin": "Dadukou",
    "level": 3,
    "area_code": 23,
    "post_code": 400080,
    "pid": 500100,
    "id": 2328,
    "area_id": 500104
  },
  {
    "area_name": "江北区",
    "pinyin": "Jiangbei",
    "level": 3,
    "area_code": 23,
    "post_code": 400020,
    "pid": 500100,
    "id": 2329,
    "area_id": 500105
  },
  {
    "area_name": "沙坪坝区",
    "pinyin": "Shapingba",
    "level": 3,
    "area_code": 23,
    "post_code": 400030,
    "pid": 500100,
    "id": 2330,
    "area_id": 500106
  },
  {
    "area_name": "九龙坡区",
    "pinyin": "Jiulongpo",
    "level": 3,
    "area_code": 23,
    "post_code": 400050,
    "pid": 500100,
    "id": 2331,
    "area_id": 500107
  },
  {
    "area_name": "南岸区",
    "pinyin": "Nan'an",
    "level": 3,
    "area_code": 23,
    "post_code": 400064,
    "pid": 500100,
    "id": 2332,
    "area_id": 500108
  },
  {
    "area_name": "北碚区",
    "pinyin": "Beibei",
    "level": 3,
    "area_code": 23,
    "post_code": 400700,
    "pid": 500100,
    "id": 2333,
    "area_id": 500109
  },
  {
    "area_name": "綦江区",
    "pinyin": "Qijiang",
    "level": 3,
    "area_code": 23,
    "post_code": 400800,
    "pid": 500100,
    "id": 2334,
    "area_id": 500110
  },
  {
    "area_name": "大足区",
    "pinyin": "Dazu",
    "level": 3,
    "area_code": 23,
    "post_code": 400900,
    "pid": 500100,
    "id": 2335,
    "area_id": 500111
  },
  {
    "area_name": "渝北区",
    "pinyin": "Yubei",
    "level": 3,
    "area_code": 23,
    "post_code": 401120,
    "pid": 500100,
    "id": 2336,
    "area_id": 500112
  },
  {
    "area_name": "巴南区",
    "pinyin": "Banan",
    "level": 3,
    "area_code": 23,
    "post_code": 401320,
    "pid": 500100,
    "id": 2337,
    "area_id": 500113
  },
  {
    "area_name": "黔江区",
    "pinyin": "Qianjiang",
    "level": 3,
    "area_code": 23,
    "post_code": 409700,
    "pid": 500100,
    "id": 2338,
    "area_id": 500114
  },
  {
    "area_name": "长寿区",
    "pinyin": "Changshou",
    "level": 3,
    "area_code": 23,
    "post_code": 401220,
    "pid": 500100,
    "id": 2339,
    "area_id": 500115
  },
  {
    "area_name": "江津区",
    "pinyin": "Jiangjin",
    "level": 3,
    "area_code": 23,
    "post_code": 402260,
    "pid": 500100,
    "id": 2340,
    "area_id": 500116
  },
  {
    "area_name": "合川区",
    "pinyin": "Hechuan",
    "level": 3,
    "area_code": 23,
    "post_code": 401520,
    "pid": 500100,
    "id": 2341,
    "area_id": 500117
  },
  {
    "area_name": "永川区",
    "pinyin": "Yongchuan",
    "level": 3,
    "area_code": 23,
    "post_code": 402160,
    "pid": 500100,
    "id": 2342,
    "area_id": 500118
  },
  {
    "area_name": "南川区",
    "pinyin": "Nanchuan",
    "level": 3,
    "area_code": 23,
    "post_code": 408400,
    "pid": 500100,
    "id": 2343,
    "area_id": 500119
  },
  {
    "area_name": "璧山区",
    "pinyin": "Bishan",
    "level": 3,
    "area_code": 23,
    "post_code": 402760,
    "pid": 500100,
    "id": 2344,
    "area_id": 500120
  },
  {
    "area_name": "铜梁区",
    "pinyin": "Tongliang",
    "level": 3,
    "area_code": 23,
    "post_code": 402560,
    "pid": 500100,
    "id": 2345,
    "area_id": 500151
  },
  {
    "area_name": "潼南县",
    "pinyin": "Tongnan",
    "level": 3,
    "area_code": 23,
    "post_code": 402660,
    "pid": 500100,
    "id": 2346,
    "area_id": 500223
  },
  {
    "area_name": "荣昌县",
    "pinyin": "Rongchang",
    "level": 3,
    "area_code": 23,
    "post_code": 402460,
    "pid": 500100,
    "id": 2347,
    "area_id": 500226
  },
  {
    "area_name": "梁平县",
    "pinyin": "Liangping",
    "level": 3,
    "area_code": 23,
    "post_code": 405200,
    "pid": 500100,
    "id": 2348,
    "area_id": 500228
  },
  {
    "area_name": "城口县",
    "pinyin": "Chengkou",
    "level": 3,
    "area_code": 23,
    "post_code": 405900,
    "pid": 500100,
    "id": 2349,
    "area_id": 500229
  },
  {
    "area_name": "丰都县",
    "pinyin": "Fengdu",
    "level": 3,
    "area_code": 23,
    "post_code": 408200,
    "pid": 500100,
    "id": 2350,
    "area_id": 500230
  },
  {
    "area_name": "垫江县",
    "pinyin": "Dianjiang",
    "level": 3,
    "area_code": 23,
    "post_code": 408300,
    "pid": 500100,
    "id": 2351,
    "area_id": 500231
  },
  {
    "area_name": "武隆县",
    "pinyin": "Wulong",
    "level": 3,
    "area_code": 23,
    "post_code": 408500,
    "pid": 500100,
    "id": 2352,
    "area_id": 500232
  },
  {
    "area_name": "忠县",
    "pinyin": "Zhongxian",
    "level": 3,
    "area_code": 23,
    "post_code": 404300,
    "pid": 500100,
    "id": 2353,
    "area_id": 500233
  },
  {
    "area_name": "开县",
    "pinyin": "Kaixian",
    "level": 3,
    "area_code": 23,
    "post_code": 405400,
    "pid": 500100,
    "id": 2354,
    "area_id": 500234
  },
  {
    "area_name": "云阳县",
    "pinyin": "Yunyang",
    "level": 3,
    "area_code": 23,
    "post_code": 404500,
    "pid": 500100,
    "id": 2355,
    "area_id": 500235
  },
  {
    "area_name": "奉节县",
    "pinyin": "Fengjie",
    "level": 3,
    "area_code": 23,
    "post_code": 404600,
    "pid": 500100,
    "id": 2356,
    "area_id": 500236
  },
  {
    "area_name": "巫山县",
    "pinyin": "Wushan",
    "level": 3,
    "area_code": 23,
    "post_code": 404700,
    "pid": 500100,
    "id": 2357,
    "area_id": 500237
  },
  {
    "area_name": "巫溪县",
    "pinyin": "Wuxi",
    "level": 3,
    "area_code": 23,
    "post_code": 405800,
    "pid": 500100,
    "id": 2358,
    "area_id": 500238
  },
  {
    "area_name": "石柱土家族自治县",
    "pinyin": "Shizhu",
    "level": 3,
    "area_code": 23,
    "post_code": 409100,
    "pid": 500100,
    "id": 2359,
    "area_id": 500240
  },
  {
    "area_name": "秀山土家族苗族自治县",
    "pinyin": "Xiushan",
    "level": 3,
    "area_code": 23,
    "post_code": 409900,
    "pid": 500100,
    "id": 2360,
    "area_id": 500241
  },
  {
    "area_name": "酉阳土家族苗族自治县",
    "pinyin": "Youyang",
    "level": 3,
    "area_code": 23,
    "post_code": 409800,
    "pid": 500100,
    "id": 2361,
    "area_id": 500242
  },
  {
    "area_name": "彭水苗族土家族自治县",
    "pinyin": "Pengshui",
    "level": 3,
    "area_code": 23,
    "post_code": 409600,
    "pid": 500100,
    "id": 2362,
    "area_id": 500243
  },
  {
    "area_name": "两江新区",
    "pinyin": "Liangjiangxinqu",
    "level": 2,
    "area_code": 23,
    "post_code": 400000,
    "pid": 500000,
    "id": 2363,
    "area_id": 500300
  },
  {
    "area_name": "北部新区",
    "pinyin": "Beibuxinqu",
    "level": 3,
    "area_code": 23,
    "post_code": 400000,
    "pid": 500300,
    "id": 2364,
    "area_id": 500301
  },
  {
    "area_name": "保税港区",
    "pinyin": "Baoshuigangqu",
    "level": 3,
    "area_code": 23,
    "post_code": 400000,
    "pid": 500300,
    "id": 2365,
    "area_id": 500302
  },
  {
    "area_name": "工业园区",
    "pinyin": "Gongyeyuanqu",
    "level": 3,
    "area_code": 23,
    "post_code": 400000,
    "pid": 500300,
    "id": 2366,
    "area_id": 500303
  },
  {
    "area_name": "四川省",
    "pinyin": "Sichuan",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2367,
    "area_id": 510000
  },
  {
    "area_name": "成都市",
    "pinyin": "Chengdu",
    "level": 2,
    "area_code": 28,
    "post_code": 610015,
    "pid": 510000,
    "id": 2368,
    "area_id": 510100
  },
  {
    "area_name": "锦江区",
    "pinyin": "Jinjiang",
    "level": 3,
    "area_code": 28,
    "post_code": 610021,
    "pid": 510100,
    "id": 2369,
    "area_id": 510104
  },
  {
    "area_name": "青羊区",
    "pinyin": "Qingyang",
    "level": 3,
    "area_code": 28,
    "post_code": 610031,
    "pid": 510100,
    "id": 2370,
    "area_id": 510105
  },
  {
    "area_name": "金牛区",
    "pinyin": "Jinniu",
    "level": 3,
    "area_code": 28,
    "post_code": 610036,
    "pid": 510100,
    "id": 2371,
    "area_id": 510106
  },
  {
    "area_name": "武侯区",
    "pinyin": "Wuhou",
    "level": 3,
    "area_code": 28,
    "post_code": 610041,
    "pid": 510100,
    "id": 2372,
    "area_id": 510107
  },
  {
    "area_name": "成华区",
    "pinyin": "Chenghua",
    "level": 3,
    "area_code": 28,
    "post_code": 610066,
    "pid": 510100,
    "id": 2373,
    "area_id": 510108
  },
  {
    "area_name": "龙泉驿区",
    "pinyin": "Longquanyi",
    "level": 3,
    "area_code": 28,
    "post_code": 610100,
    "pid": 510100,
    "id": 2374,
    "area_id": 510112
  },
  {
    "area_name": "青白江区",
    "pinyin": "Qingbaijiang",
    "level": 3,
    "area_code": 28,
    "post_code": 610300,
    "pid": 510100,
    "id": 2375,
    "area_id": 510113
  },
  {
    "area_name": "新都区",
    "pinyin": "Xindu",
    "level": 3,
    "area_code": 28,
    "post_code": 610500,
    "pid": 510100,
    "id": 2376,
    "area_id": 510114
  },
  {
    "area_name": "温江区",
    "pinyin": "Wenjiang",
    "level": 3,
    "area_code": 28,
    "post_code": 611130,
    "pid": 510100,
    "id": 2377,
    "area_id": 510115
  },
  {
    "area_name": "金堂县",
    "pinyin": "Jintang",
    "level": 3,
    "area_code": 28,
    "post_code": 610400,
    "pid": 510100,
    "id": 2378,
    "area_id": 510121
  },
  {
    "area_name": "双流县",
    "pinyin": "Shuangliu",
    "level": 3,
    "area_code": 28,
    "post_code": 610200,
    "pid": 510100,
    "id": 2379,
    "area_id": 510122
  },
  {
    "area_name": "郫县",
    "pinyin": "Pixian",
    "level": 3,
    "area_code": 28,
    "post_code": 611730,
    "pid": 510100,
    "id": 2380,
    "area_id": 510124
  },
  {
    "area_name": "大邑县",
    "pinyin": "Dayi",
    "level": 3,
    "area_code": 28,
    "post_code": 611330,
    "pid": 510100,
    "id": 2381,
    "area_id": 510129
  },
  {
    "area_name": "蒲江县",
    "pinyin": "Pujiang",
    "level": 3,
    "area_code": 28,
    "post_code": 611630,
    "pid": 510100,
    "id": 2382,
    "area_id": 510131
  },
  {
    "area_name": "新津县",
    "pinyin": "Xinjin",
    "level": 3,
    "area_code": 28,
    "post_code": 611430,
    "pid": 510100,
    "id": 2383,
    "area_id": 510132
  },
  {
    "area_name": "都江堰市",
    "pinyin": "Dujiangyan",
    "level": 3,
    "area_code": 28,
    "post_code": 611830,
    "pid": 510100,
    "id": 2384,
    "area_id": 510181
  },
  {
    "area_name": "彭州市",
    "pinyin": "Pengzhou",
    "level": 3,
    "area_code": 28,
    "post_code": 611930,
    "pid": 510100,
    "id": 2385,
    "area_id": 510182
  },
  {
    "area_name": "邛崃市",
    "pinyin": "Qionglai",
    "level": 3,
    "area_code": 28,
    "post_code": 611530,
    "pid": 510100,
    "id": 2386,
    "area_id": 510183
  },
  {
    "area_name": "崇州市",
    "pinyin": "Chongzhou",
    "level": 3,
    "area_code": 28,
    "post_code": 611230,
    "pid": 510100,
    "id": 2387,
    "area_id": 510184
  },
  {
    "area_name": "自贡市",
    "pinyin": "Zigong",
    "level": 2,
    "area_code": 813,
    "post_code": 643000,
    "pid": 510000,
    "id": 2388,
    "area_id": 510300
  },
  {
    "area_name": "自流井区",
    "pinyin": "Ziliujing",
    "level": 3,
    "area_code": 813,
    "post_code": 643000,
    "pid": 510300,
    "id": 2389,
    "area_id": 510302
  },
  {
    "area_name": "贡井区",
    "pinyin": "Gongjing",
    "level": 3,
    "area_code": 813,
    "post_code": 643020,
    "pid": 510300,
    "id": 2390,
    "area_id": 510303
  },
  {
    "area_name": "大安区",
    "pinyin": "Da'an",
    "level": 3,
    "area_code": 813,
    "post_code": 643010,
    "pid": 510300,
    "id": 2391,
    "area_id": 510304
  },
  {
    "area_name": "沿滩区",
    "pinyin": "Yantan",
    "level": 3,
    "area_code": 813,
    "post_code": 643030,
    "pid": 510300,
    "id": 2392,
    "area_id": 510311
  },
  {
    "area_name": "荣县",
    "pinyin": "Rongxian",
    "level": 3,
    "area_code": 813,
    "post_code": 643100,
    "pid": 510300,
    "id": 2393,
    "area_id": 510321
  },
  {
    "area_name": "富顺县",
    "pinyin": "Fushun",
    "level": 3,
    "area_code": 813,
    "post_code": 643200,
    "pid": 510300,
    "id": 2394,
    "area_id": 510322
  },
  {
    "area_name": "攀枝花市",
    "pinyin": "Panzhihua",
    "level": 2,
    "area_code": 812,
    "post_code": 617000,
    "pid": 510000,
    "id": 2395,
    "area_id": 510400
  },
  {
    "area_name": "东区",
    "pinyin": "Dongqu",
    "level": 3,
    "area_code": 812,
    "post_code": 617067,
    "pid": 510400,
    "id": 2396,
    "area_id": 510402
  },
  {
    "area_name": "西区",
    "pinyin": "Xiqu",
    "level": 3,
    "area_code": 812,
    "post_code": 617068,
    "pid": 510400,
    "id": 2397,
    "area_id": 510403
  },
  {
    "area_name": "仁和区",
    "pinyin": "Renhe",
    "level": 3,
    "area_code": 812,
    "post_code": 617061,
    "pid": 510400,
    "id": 2398,
    "area_id": 510411
  },
  {
    "area_name": "米易县",
    "pinyin": "Miyi",
    "level": 3,
    "area_code": 812,
    "post_code": 617200,
    "pid": 510400,
    "id": 2399,
    "area_id": 510421
  },
  {
    "area_name": "盐边县",
    "pinyin": "Yanbian",
    "level": 3,
    "area_code": 812,
    "post_code": 617100,
    "pid": 510400,
    "id": 2400,
    "area_id": 510422
  },
  {
    "area_name": "泸州市",
    "pinyin": "Luzhou",
    "level": 2,
    "area_code": 830,
    "post_code": 646000,
    "pid": 510000,
    "id": 2401,
    "area_id": 510500
  },
  {
    "area_name": "江阳区",
    "pinyin": "Jiangyang",
    "level": 3,
    "area_code": 830,
    "post_code": 646000,
    "pid": 510500,
    "id": 2402,
    "area_id": 510502
  },
  {
    "area_name": "纳溪区",
    "pinyin": "Naxi",
    "level": 3,
    "area_code": 830,
    "post_code": 646300,
    "pid": 510500,
    "id": 2403,
    "area_id": 510503
  },
  {
    "area_name": "龙马潭区",
    "pinyin": "Longmatan",
    "level": 3,
    "area_code": 830,
    "post_code": 646000,
    "pid": 510500,
    "id": 2404,
    "area_id": 510504
  },
  {
    "area_name": "泸县",
    "pinyin": "Luxian",
    "level": 3,
    "area_code": 830,
    "post_code": 646106,
    "pid": 510500,
    "id": 2405,
    "area_id": 510521
  },
  {
    "area_name": "合江县",
    "pinyin": "Hejiang",
    "level": 3,
    "area_code": 830,
    "post_code": 646200,
    "pid": 510500,
    "id": 2406,
    "area_id": 510522
  },
  {
    "area_name": "叙永县",
    "pinyin": "Xuyong",
    "level": 3,
    "area_code": 830,
    "post_code": 646400,
    "pid": 510500,
    "id": 2407,
    "area_id": 510524
  },
  {
    "area_name": "古蔺县",
    "pinyin": "Gulin",
    "level": 3,
    "area_code": 830,
    "post_code": 646500,
    "pid": 510500,
    "id": 2408,
    "area_id": 510525
  },
  {
    "area_name": "德阳市",
    "pinyin": "Deyang",
    "level": 2,
    "area_code": 838,
    "post_code": 618000,
    "pid": 510000,
    "id": 2409,
    "area_id": 510600
  },
  {
    "area_name": "旌阳区",
    "pinyin": "Jingyang",
    "level": 3,
    "area_code": 838,
    "post_code": 618000,
    "pid": 510600,
    "id": 2410,
    "area_id": 510603
  },
  {
    "area_name": "中江县",
    "pinyin": "Zhongjiang",
    "level": 3,
    "area_code": 838,
    "post_code": 618100,
    "pid": 510600,
    "id": 2411,
    "area_id": 510623
  },
  {
    "area_name": "罗江县",
    "pinyin": "Luojiang",
    "level": 3,
    "area_code": 838,
    "post_code": 618500,
    "pid": 510600,
    "id": 2412,
    "area_id": 510626
  },
  {
    "area_name": "广汉市",
    "pinyin": "Guanghan",
    "level": 3,
    "area_code": 838,
    "post_code": 618300,
    "pid": 510600,
    "id": 2413,
    "area_id": 510681
  },
  {
    "area_name": "什邡市",
    "pinyin": "Shifang",
    "level": 3,
    "area_code": 838,
    "post_code": 618400,
    "pid": 510600,
    "id": 2414,
    "area_id": 510682
  },
  {
    "area_name": "绵竹市",
    "pinyin": "Mianzhu",
    "level": 3,
    "area_code": 838,
    "post_code": 618200,
    "pid": 510600,
    "id": 2415,
    "area_id": 510683
  },
  {
    "area_name": "绵阳市",
    "pinyin": "Mianyang",
    "level": 2,
    "area_code": 816,
    "post_code": 621000,
    "pid": 510000,
    "id": 2416,
    "area_id": 510700
  },
  {
    "area_name": "涪城区",
    "pinyin": "Fucheng",
    "level": 3,
    "area_code": 816,
    "post_code": 621000,
    "pid": 510700,
    "id": 2417,
    "area_id": 510703
  },
  {
    "area_name": "游仙区",
    "pinyin": "Youxian",
    "level": 3,
    "area_code": 816,
    "post_code": 621022,
    "pid": 510700,
    "id": 2418,
    "area_id": 510704
  },
  {
    "area_name": "三台县",
    "pinyin": "Santai",
    "level": 3,
    "area_code": 816,
    "post_code": 621100,
    "pid": 510700,
    "id": 2419,
    "area_id": 510722
  },
  {
    "area_name": "盐亭县",
    "pinyin": "Yanting",
    "level": 3,
    "area_code": 816,
    "post_code": 621600,
    "pid": 510700,
    "id": 2420,
    "area_id": 510723
  },
  {
    "area_name": "安县",
    "pinyin": "Anxian",
    "level": 3,
    "area_code": 816,
    "post_code": 622650,
    "pid": 510700,
    "id": 2421,
    "area_id": 510724
  },
  {
    "area_name": "梓潼县",
    "pinyin": "Zitong",
    "level": 3,
    "area_code": 816,
    "post_code": 622150,
    "pid": 510700,
    "id": 2422,
    "area_id": 510725
  },
  {
    "area_name": "北川羌族自治县",
    "pinyin": "Beichuan",
    "level": 3,
    "area_code": 816,
    "post_code": 622750,
    "pid": 510700,
    "id": 2423,
    "area_id": 510726
  },
  {
    "area_name": "平武县",
    "pinyin": "Pingwu",
    "level": 3,
    "area_code": 816,
    "post_code": 622550,
    "pid": 510700,
    "id": 2424,
    "area_id": 510727
  },
  {
    "area_name": "江油市",
    "pinyin": "Jiangyou",
    "level": 3,
    "area_code": 816,
    "post_code": 621700,
    "pid": 510700,
    "id": 2425,
    "area_id": 510781
  },
  {
    "area_name": "广元市",
    "pinyin": "Guangyuan",
    "level": 2,
    "area_code": 839,
    "post_code": 628000,
    "pid": 510000,
    "id": 2426,
    "area_id": 510800
  },
  {
    "area_name": "利州区",
    "pinyin": "Lizhou",
    "level": 3,
    "area_code": 839,
    "post_code": 628017,
    "pid": 510800,
    "id": 2427,
    "area_id": 510802
  },
  {
    "area_name": "昭化区",
    "pinyin": "Zhaohua",
    "level": 3,
    "area_code": 839,
    "post_code": 628017,
    "pid": 510800,
    "id": 2428,
    "area_id": 510811
  },
  {
    "area_name": "朝天区",
    "pinyin": "Chaotian",
    "level": 3,
    "area_code": 839,
    "post_code": 628017,
    "pid": 510800,
    "id": 2429,
    "area_id": 510812
  },
  {
    "area_name": "旺苍县",
    "pinyin": "Wangcang",
    "level": 3,
    "area_code": 839,
    "post_code": 628200,
    "pid": 510800,
    "id": 2430,
    "area_id": 510821
  },
  {
    "area_name": "青川县",
    "pinyin": "Qingchuan",
    "level": 3,
    "area_code": 839,
    "post_code": 628100,
    "pid": 510800,
    "id": 2431,
    "area_id": 510822
  },
  {
    "area_name": "剑阁县",
    "pinyin": "Jiange",
    "level": 3,
    "area_code": 839,
    "post_code": 628300,
    "pid": 510800,
    "id": 2432,
    "area_id": 510823
  },
  {
    "area_name": "苍溪县",
    "pinyin": "Cangxi",
    "level": 3,
    "area_code": 839,
    "post_code": 628400,
    "pid": 510800,
    "id": 2433,
    "area_id": 510824
  },
  {
    "area_name": "遂宁市",
    "pinyin": "Suining",
    "level": 2,
    "area_code": 825,
    "post_code": 629000,
    "pid": 510000,
    "id": 2434,
    "area_id": 510900
  },
  {
    "area_name": "船山区",
    "pinyin": "Chuanshan",
    "level": 3,
    "area_code": 825,
    "post_code": 629000,
    "pid": 510900,
    "id": 2435,
    "area_id": 510903
  },
  {
    "area_name": "安居区",
    "pinyin": "Anju",
    "level": 3,
    "area_code": 825,
    "post_code": 629000,
    "pid": 510900,
    "id": 2436,
    "area_id": 510904
  },
  {
    "area_name": "蓬溪县",
    "pinyin": "Pengxi",
    "level": 3,
    "area_code": 825,
    "post_code": 629100,
    "pid": 510900,
    "id": 2437,
    "area_id": 510921
  },
  {
    "area_name": "射洪县",
    "pinyin": "Shehong",
    "level": 3,
    "area_code": 825,
    "post_code": 629200,
    "pid": 510900,
    "id": 2438,
    "area_id": 510922
  },
  {
    "area_name": "大英县",
    "pinyin": "Daying",
    "level": 3,
    "area_code": 825,
    "post_code": 629300,
    "pid": 510900,
    "id": 2439,
    "area_id": 510923
  },
  {
    "area_name": "内江市",
    "pinyin": "Neijiang",
    "level": 2,
    "area_code": 832,
    "post_code": 641000,
    "pid": 510000,
    "id": 2440,
    "area_id": 511000
  },
  {
    "area_name": "市中区",
    "pinyin": "Shizhongqu",
    "level": 3,
    "area_code": 832,
    "post_code": 641000,
    "pid": 511000,
    "id": 2441,
    "area_id": 511002
  },
  {
    "area_name": "东兴区",
    "pinyin": "Dongxing",
    "level": 3,
    "area_code": 832,
    "post_code": 641100,
    "pid": 511000,
    "id": 2442,
    "area_id": 511011
  },
  {
    "area_name": "威远县",
    "pinyin": "Weiyuan",
    "level": 3,
    "area_code": 832,
    "post_code": 642450,
    "pid": 511000,
    "id": 2443,
    "area_id": 511024
  },
  {
    "area_name": "资中县",
    "pinyin": "Zizhong",
    "level": 3,
    "area_code": 832,
    "post_code": 641200,
    "pid": 511000,
    "id": 2444,
    "area_id": 511025
  },
  {
    "area_name": "隆昌县",
    "pinyin": "Longchang",
    "level": 3,
    "area_code": 832,
    "post_code": 642150,
    "pid": 511000,
    "id": 2445,
    "area_id": 511028
  },
  {
    "area_name": "乐山市",
    "pinyin": "Leshan",
    "level": 2,
    "area_code": 833,
    "post_code": 614000,
    "pid": 510000,
    "id": 2446,
    "area_id": 511100
  },
  {
    "area_name": "市中区",
    "pinyin": "Shizhongqu",
    "level": 3,
    "area_code": 833,
    "post_code": 614000,
    "pid": 511100,
    "id": 2447,
    "area_id": 511102
  },
  {
    "area_name": "沙湾区",
    "pinyin": "Shawan",
    "level": 3,
    "area_code": 833,
    "post_code": 614900,
    "pid": 511100,
    "id": 2448,
    "area_id": 511111
  },
  {
    "area_name": "五通桥区",
    "pinyin": "Wutongqiao",
    "level": 3,
    "area_code": 833,
    "post_code": 614800,
    "pid": 511100,
    "id": 2449,
    "area_id": 511112
  },
  {
    "area_name": "金口河区",
    "pinyin": "Jinkouhe",
    "level": 3,
    "area_code": 833,
    "post_code": 614700,
    "pid": 511100,
    "id": 2450,
    "area_id": 511113
  },
  {
    "area_name": "犍为县",
    "pinyin": "Qianwei",
    "level": 3,
    "area_code": 833,
    "post_code": 614400,
    "pid": 511100,
    "id": 2451,
    "area_id": 511123
  },
  {
    "area_name": "井研县",
    "pinyin": "Jingyan",
    "level": 3,
    "area_code": 833,
    "post_code": 613100,
    "pid": 511100,
    "id": 2452,
    "area_id": 511124
  },
  {
    "area_name": "夹江县",
    "pinyin": "Jiajiang",
    "level": 3,
    "area_code": 833,
    "post_code": 614100,
    "pid": 511100,
    "id": 2453,
    "area_id": 511126
  },
  {
    "area_name": "沐川县",
    "pinyin": "Muchuan",
    "level": 3,
    "area_code": 833,
    "post_code": 614500,
    "pid": 511100,
    "id": 2454,
    "area_id": 511129
  },
  {
    "area_name": "峨边彝族自治县",
    "pinyin": "Ebian",
    "level": 3,
    "area_code": 833,
    "post_code": 614300,
    "pid": 511100,
    "id": 2455,
    "area_id": 511132
  },
  {
    "area_name": "马边彝族自治县",
    "pinyin": "Mabian",
    "level": 3,
    "area_code": 833,
    "post_code": 614600,
    "pid": 511100,
    "id": 2456,
    "area_id": 511133
  },
  {
    "area_name": "峨眉山市",
    "pinyin": "Emeishan",
    "level": 3,
    "area_code": 833,
    "post_code": 614200,
    "pid": 511100,
    "id": 2457,
    "area_id": 511181
  },
  {
    "area_name": "南充市",
    "pinyin": "Nanchong",
    "level": 2,
    "area_code": 817,
    "post_code": 637000,
    "pid": 510000,
    "id": 2458,
    "area_id": 511300
  },
  {
    "area_name": "顺庆区",
    "pinyin": "Shunqing",
    "level": 3,
    "area_code": 817,
    "post_code": 637000,
    "pid": 511300,
    "id": 2459,
    "area_id": 511302
  },
  {
    "area_name": "高坪区",
    "pinyin": "Gaoping",
    "level": 3,
    "area_code": 817,
    "post_code": 637100,
    "pid": 511300,
    "id": 2460,
    "area_id": 511303
  },
  {
    "area_name": "嘉陵区",
    "pinyin": "Jialing",
    "level": 3,
    "area_code": 817,
    "post_code": 637100,
    "pid": 511300,
    "id": 2461,
    "area_id": 511304
  },
  {
    "area_name": "南部县",
    "pinyin": "Nanbu",
    "level": 3,
    "area_code": 817,
    "post_code": 637300,
    "pid": 511300,
    "id": 2462,
    "area_id": 511321
  },
  {
    "area_name": "营山县",
    "pinyin": "Yingshan",
    "level": 3,
    "area_code": 817,
    "post_code": 637700,
    "pid": 511300,
    "id": 2463,
    "area_id": 511322
  },
  {
    "area_name": "蓬安县",
    "pinyin": "Peng'an",
    "level": 3,
    "area_code": 817,
    "post_code": 637800,
    "pid": 511300,
    "id": 2464,
    "area_id": 511323
  },
  {
    "area_name": "仪陇县",
    "pinyin": "Yilong",
    "level": 3,
    "area_code": 817,
    "post_code": 637600,
    "pid": 511300,
    "id": 2465,
    "area_id": 511324
  },
  {
    "area_name": "西充县",
    "pinyin": "Xichong",
    "level": 3,
    "area_code": 817,
    "post_code": 637200,
    "pid": 511300,
    "id": 2466,
    "area_id": 511325
  },
  {
    "area_name": "阆中市",
    "pinyin": "Langzhong",
    "level": 3,
    "area_code": 817,
    "post_code": 637400,
    "pid": 511300,
    "id": 2467,
    "area_id": 511381
  },
  {
    "area_name": "眉山市",
    "pinyin": "Meishan",
    "level": 2,
    "area_code": 28,
    "post_code": 620020,
    "pid": 510000,
    "id": 2468,
    "area_id": 511400
  },
  {
    "area_name": "东坡区",
    "pinyin": "Dongpo",
    "level": 3,
    "area_code": 28,
    "post_code": 620010,
    "pid": 511400,
    "id": 2469,
    "area_id": 511402
  },
  {
    "area_name": "彭山区",
    "pinyin": "Pengshan",
    "level": 3,
    "area_code": 28,
    "post_code": 620860,
    "pid": 511400,
    "id": 2470,
    "area_id": 511403
  },
  {
    "area_name": "仁寿县",
    "pinyin": "Renshou",
    "level": 3,
    "area_code": 28,
    "post_code": 620500,
    "pid": 511400,
    "id": 2471,
    "area_id": 511421
  },
  {
    "area_name": "洪雅县",
    "pinyin": "Hongya",
    "level": 3,
    "area_code": 28,
    "post_code": 620360,
    "pid": 511400,
    "id": 2472,
    "area_id": 511423
  },
  {
    "area_name": "丹棱县",
    "pinyin": "Danling",
    "level": 3,
    "area_code": 28,
    "post_code": 620200,
    "pid": 511400,
    "id": 2473,
    "area_id": 511424
  },
  {
    "area_name": "青神县",
    "pinyin": "Qingshen",
    "level": 3,
    "area_code": 28,
    "post_code": 620460,
    "pid": 511400,
    "id": 2474,
    "area_id": 511425
  },
  {
    "area_name": "宜宾市",
    "pinyin": "Yibin",
    "level": 2,
    "area_code": 831,
    "post_code": 644000,
    "pid": 510000,
    "id": 2475,
    "area_id": 511500
  },
  {
    "area_name": "翠屏区",
    "pinyin": "Cuiping",
    "level": 3,
    "area_code": 831,
    "post_code": 644000,
    "pid": 511500,
    "id": 2476,
    "area_id": 511502
  },
  {
    "area_name": "南溪区",
    "pinyin": "Nanxi",
    "level": 3,
    "area_code": 831,
    "post_code": 644100,
    "pid": 511500,
    "id": 2477,
    "area_id": 511503
  },
  {
    "area_name": "宜宾县",
    "pinyin": "Yibin",
    "level": 3,
    "area_code": 831,
    "post_code": 644600,
    "pid": 511500,
    "id": 2478,
    "area_id": 511521
  },
  {
    "area_name": "江安县",
    "pinyin": "Jiang'an",
    "level": 3,
    "area_code": 831,
    "post_code": 644200,
    "pid": 511500,
    "id": 2479,
    "area_id": 511523
  },
  {
    "area_name": "长宁县",
    "pinyin": "Changning",
    "level": 3,
    "area_code": 831,
    "post_code": 644300,
    "pid": 511500,
    "id": 2480,
    "area_id": 511524
  },
  {
    "area_name": "高县",
    "pinyin": "Gaoxian",
    "level": 3,
    "area_code": 831,
    "post_code": 645150,
    "pid": 511500,
    "id": 2481,
    "area_id": 511525
  },
  {
    "area_name": "珙县",
    "pinyin": "Gongxian",
    "level": 3,
    "area_code": 831,
    "post_code": 644500,
    "pid": 511500,
    "id": 2482,
    "area_id": 511526
  },
  {
    "area_name": "筠连县",
    "pinyin": "Junlian",
    "level": 3,
    "area_code": 831,
    "post_code": 645250,
    "pid": 511500,
    "id": 2483,
    "area_id": 511527
  },
  {
    "area_name": "兴文县",
    "pinyin": "Xingwen",
    "level": 3,
    "area_code": 831,
    "post_code": 644400,
    "pid": 511500,
    "id": 2484,
    "area_id": 511528
  },
  {
    "area_name": "屏山县",
    "pinyin": "Pingshan",
    "level": 3,
    "area_code": 831,
    "post_code": 645350,
    "pid": 511500,
    "id": 2485,
    "area_id": 511529
  },
  {
    "area_name": "广安市",
    "pinyin": "Guang'an",
    "level": 2,
    "area_code": 826,
    "post_code": 638000,
    "pid": 510000,
    "id": 2486,
    "area_id": 511600
  },
  {
    "area_name": "广安区",
    "pinyin": "Guang'an",
    "level": 3,
    "area_code": 826,
    "post_code": 638000,
    "pid": 511600,
    "id": 2487,
    "area_id": 511602
  },
  {
    "area_name": "前锋区",
    "pinyin": "Qianfeng",
    "level": 3,
    "area_code": 826,
    "post_code": 638019,
    "pid": 511600,
    "id": 2488,
    "area_id": 511603
  },
  {
    "area_name": "岳池县",
    "pinyin": "Yuechi",
    "level": 3,
    "area_code": 826,
    "post_code": 638300,
    "pid": 511600,
    "id": 2489,
    "area_id": 511621
  },
  {
    "area_name": "武胜县",
    "pinyin": "Wusheng",
    "level": 3,
    "area_code": 826,
    "post_code": 638400,
    "pid": 511600,
    "id": 2490,
    "area_id": 511622
  },
  {
    "area_name": "邻水县",
    "pinyin": "Linshui",
    "level": 3,
    "area_code": 826,
    "post_code": 638500,
    "pid": 511600,
    "id": 2491,
    "area_id": 511623
  },
  {
    "area_name": "华蓥市",
    "pinyin": "Huaying",
    "level": 3,
    "area_code": 826,
    "post_code": 638600,
    "pid": 511600,
    "id": 2492,
    "area_id": 511681
  },
  {
    "area_name": "达州市",
    "pinyin": "Dazhou",
    "level": 2,
    "area_code": 818,
    "post_code": 635000,
    "pid": 510000,
    "id": 2493,
    "area_id": 511700
  },
  {
    "area_name": "通川区",
    "pinyin": "Tongchuan",
    "level": 3,
    "area_code": 818,
    "post_code": 635000,
    "pid": 511700,
    "id": 2494,
    "area_id": 511702
  },
  {
    "area_name": "达川区",
    "pinyin": "Dachuan",
    "level": 3,
    "area_code": 818,
    "post_code": 635000,
    "pid": 511700,
    "id": 2495,
    "area_id": 511703
  },
  {
    "area_name": "宣汉县",
    "pinyin": "Xuanhan",
    "level": 3,
    "area_code": 818,
    "post_code": 636150,
    "pid": 511700,
    "id": 2496,
    "area_id": 511722
  },
  {
    "area_name": "开江县",
    "pinyin": "Kaijiang",
    "level": 3,
    "area_code": 818,
    "post_code": 636250,
    "pid": 511700,
    "id": 2497,
    "area_id": 511723
  },
  {
    "area_name": "大竹县",
    "pinyin": "Dazhu",
    "level": 3,
    "area_code": 818,
    "post_code": 635100,
    "pid": 511700,
    "id": 2498,
    "area_id": 511724
  },
  {
    "area_name": "渠县",
    "pinyin": "Quxian",
    "level": 3,
    "area_code": 818,
    "post_code": 635200,
    "pid": 511700,
    "id": 2499,
    "area_id": 511725
  },
  {
    "area_name": "万源市",
    "pinyin": "Wanyuan",
    "level": 3,
    "area_code": 818,
    "post_code": 636350,
    "pid": 511700,
    "id": 2500,
    "area_id": 511781
  },
  {
    "area_name": "雅安市",
    "pinyin": "Ya'an",
    "level": 2,
    "area_code": 835,
    "post_code": 625000,
    "pid": 510000,
    "id": 2501,
    "area_id": 511800
  },
  {
    "area_name": "雨城区",
    "pinyin": "Yucheng",
    "level": 3,
    "area_code": 835,
    "post_code": 625000,
    "pid": 511800,
    "id": 2502,
    "area_id": 511802
  },
  {
    "area_name": "名山区",
    "pinyin": "Mingshan",
    "level": 3,
    "area_code": 835,
    "post_code": 625100,
    "pid": 511800,
    "id": 2503,
    "area_id": 511803
  },
  {
    "area_name": "荥经县",
    "pinyin": "Yingjing",
    "level": 3,
    "area_code": 835,
    "post_code": 625200,
    "pid": 511800,
    "id": 2504,
    "area_id": 511822
  },
  {
    "area_name": "汉源县",
    "pinyin": "Hanyuan",
    "level": 3,
    "area_code": 835,
    "post_code": 625300,
    "pid": 511800,
    "id": 2505,
    "area_id": 511823
  },
  {
    "area_name": "石棉县",
    "pinyin": "Shimian",
    "level": 3,
    "area_code": 835,
    "post_code": 625400,
    "pid": 511800,
    "id": 2506,
    "area_id": 511824
  },
  {
    "area_name": "天全县",
    "pinyin": "Tianquan",
    "level": 3,
    "area_code": 835,
    "post_code": 625500,
    "pid": 511800,
    "id": 2507,
    "area_id": 511825
  },
  {
    "area_name": "芦山县",
    "pinyin": "Lushan",
    "level": 3,
    "area_code": 835,
    "post_code": 625600,
    "pid": 511800,
    "id": 2508,
    "area_id": 511826
  },
  {
    "area_name": "宝兴县",
    "pinyin": "Baoxing",
    "level": 3,
    "area_code": 835,
    "post_code": 625700,
    "pid": 511800,
    "id": 2509,
    "area_id": 511827
  },
  {
    "area_name": "巴中市",
    "pinyin": "Bazhong",
    "level": 2,
    "area_code": 827,
    "post_code": 636000,
    "pid": 510000,
    "id": 2510,
    "area_id": 511900
  },
  {
    "area_name": "巴州区",
    "pinyin": "Bazhou",
    "level": 3,
    "area_code": 827,
    "post_code": 636001,
    "pid": 511900,
    "id": 2511,
    "area_id": 511902
  },
  {
    "area_name": "恩阳区",
    "pinyin": "Enyang",
    "level": 3,
    "area_code": 827,
    "post_code": 636064,
    "pid": 511900,
    "id": 2512,
    "area_id": 511903
  },
  {
    "area_name": "通江县",
    "pinyin": "Tongjiang",
    "level": 3,
    "area_code": 827,
    "post_code": 636700,
    "pid": 511900,
    "id": 2513,
    "area_id": 511921
  },
  {
    "area_name": "南江县",
    "pinyin": "Nanjiang",
    "level": 3,
    "area_code": 827,
    "post_code": 636600,
    "pid": 511900,
    "id": 2514,
    "area_id": 511922
  },
  {
    "area_name": "平昌县",
    "pinyin": "Pingchang",
    "level": 3,
    "area_code": 827,
    "post_code": 636400,
    "pid": 511900,
    "id": 2515,
    "area_id": 511923
  },
  {
    "area_name": "资阳市",
    "pinyin": "Ziyang",
    "level": 2,
    "area_code": 28,
    "post_code": 641300,
    "pid": 510000,
    "id": 2516,
    "area_id": 512000
  },
  {
    "area_name": "雁江区",
    "pinyin": "Yanjiang",
    "level": 3,
    "area_code": 28,
    "post_code": 641300,
    "pid": 512000,
    "id": 2517,
    "area_id": 512002
  },
  {
    "area_name": "安岳县",
    "pinyin": "Anyue",
    "level": 3,
    "area_code": 28,
    "post_code": 642350,
    "pid": 512000,
    "id": 2518,
    "area_id": 512021
  },
  {
    "area_name": "乐至县",
    "pinyin": "Lezhi",
    "level": 3,
    "area_code": 28,
    "post_code": 641500,
    "pid": 512000,
    "id": 2519,
    "area_id": 512022
  },
  {
    "area_name": "简阳市",
    "pinyin": "Jianyang",
    "level": 3,
    "area_code": 28,
    "post_code": 641400,
    "pid": 512000,
    "id": 2520,
    "area_id": 512081
  },
  {
    "area_name": "阿坝藏族羌族自治州",
    "pinyin": "Aba",
    "level": 2,
    "area_code": 837,
    "post_code": 624000,
    "pid": 510000,
    "id": 2521,
    "area_id": 513200
  },
  {
    "area_name": "汶川县",
    "pinyin": "Wenchuan",
    "level": 3,
    "area_code": 837,
    "post_code": 623000,
    "pid": 513200,
    "id": 2522,
    "area_id": 513221
  },
  {
    "area_name": "理县",
    "pinyin": "Lixian",
    "level": 3,
    "area_code": 837,
    "post_code": 623100,
    "pid": 513200,
    "id": 2523,
    "area_id": 513222
  },
  {
    "area_name": "茂县",
    "pinyin": "Maoxian",
    "level": 3,
    "area_code": 837,
    "post_code": 623200,
    "pid": 513200,
    "id": 2524,
    "area_id": 513223
  },
  {
    "area_name": "松潘县",
    "pinyin": "Songpan",
    "level": 3,
    "area_code": 837,
    "post_code": 623300,
    "pid": 513200,
    "id": 2525,
    "area_id": 513224
  },
  {
    "area_name": "九寨沟县",
    "pinyin": "Jiuzhaigou",
    "level": 3,
    "area_code": 837,
    "post_code": 623400,
    "pid": 513200,
    "id": 2526,
    "area_id": 513225
  },
  {
    "area_name": "金川县",
    "pinyin": "Jinchuan",
    "level": 3,
    "area_code": 837,
    "post_code": 624100,
    "pid": 513200,
    "id": 2527,
    "area_id": 513226
  },
  {
    "area_name": "小金县",
    "pinyin": "Xiaojin",
    "level": 3,
    "area_code": 837,
    "post_code": 624200,
    "pid": 513200,
    "id": 2528,
    "area_id": 513227
  },
  {
    "area_name": "黑水县",
    "pinyin": "Heishui",
    "level": 3,
    "area_code": 837,
    "post_code": 623500,
    "pid": 513200,
    "id": 2529,
    "area_id": 513228
  },
  {
    "area_name": "马尔康县",
    "pinyin": "Maerkang",
    "level": 3,
    "area_code": 837,
    "post_code": 624000,
    "pid": 513200,
    "id": 2530,
    "area_id": 513229
  },
  {
    "area_name": "壤塘县",
    "pinyin": "Rangtang",
    "level": 3,
    "area_code": 837,
    "post_code": 624300,
    "pid": 513200,
    "id": 2531,
    "area_id": 513230
  },
  {
    "area_name": "阿坝县",
    "pinyin": "Aba",
    "level": 3,
    "area_code": 837,
    "post_code": 624600,
    "pid": 513200,
    "id": 2532,
    "area_id": 513231
  },
  {
    "area_name": "若尔盖县",
    "pinyin": "Ruoergai",
    "level": 3,
    "area_code": 837,
    "post_code": 624500,
    "pid": 513200,
    "id": 2533,
    "area_id": 513232
  },
  {
    "area_name": "红原县",
    "pinyin": "Hongyuan",
    "level": 3,
    "area_code": 837,
    "post_code": 624400,
    "pid": 513200,
    "id": 2534,
    "area_id": 513233
  },
  {
    "area_name": "甘孜藏族自治州",
    "pinyin": "Garze",
    "level": 2,
    "area_code": 836,
    "post_code": 626000,
    "pid": 510000,
    "id": 2535,
    "area_id": 513300
  },
  {
    "area_name": "康定县",
    "pinyin": "Kangding",
    "level": 3,
    "area_code": 836,
    "post_code": 626000,
    "pid": 513300,
    "id": 2536,
    "area_id": 513321
  },
  {
    "area_name": "泸定县",
    "pinyin": "Luding",
    "level": 3,
    "area_code": 836,
    "post_code": 626100,
    "pid": 513300,
    "id": 2537,
    "area_id": 513322
  },
  {
    "area_name": "丹巴县",
    "pinyin": "Danba",
    "level": 3,
    "area_code": 836,
    "post_code": 626300,
    "pid": 513300,
    "id": 2538,
    "area_id": 513323
  },
  {
    "area_name": "九龙县",
    "pinyin": "Jiulong",
    "level": 3,
    "area_code": 836,
    "post_code": 626200,
    "pid": 513300,
    "id": 2539,
    "area_id": 513324
  },
  {
    "area_name": "雅江县",
    "pinyin": "Yajiang",
    "level": 3,
    "area_code": 836,
    "post_code": 627450,
    "pid": 513300,
    "id": 2540,
    "area_id": 513325
  },
  {
    "area_name": "道孚县",
    "pinyin": "Daofu",
    "level": 3,
    "area_code": 836,
    "post_code": 626400,
    "pid": 513300,
    "id": 2541,
    "area_id": 513326
  },
  {
    "area_name": "炉霍县",
    "pinyin": "Luhuo",
    "level": 3,
    "area_code": 836,
    "post_code": 626500,
    "pid": 513300,
    "id": 2542,
    "area_id": 513327
  },
  {
    "area_name": "甘孜县",
    "pinyin": "Ganzi",
    "level": 3,
    "area_code": 836,
    "post_code": 626700,
    "pid": 513300,
    "id": 2543,
    "area_id": 513328
  },
  {
    "area_name": "新龙县",
    "pinyin": "Xinlong",
    "level": 3,
    "area_code": 836,
    "post_code": 626800,
    "pid": 513300,
    "id": 2544,
    "area_id": 513329
  },
  {
    "area_name": "德格县",
    "pinyin": "Dege",
    "level": 3,
    "area_code": 836,
    "post_code": 627250,
    "pid": 513300,
    "id": 2545,
    "area_id": 513330
  },
  {
    "area_name": "白玉县",
    "pinyin": "Baiyu",
    "level": 3,
    "area_code": 836,
    "post_code": 627150,
    "pid": 513300,
    "id": 2546,
    "area_id": 513331
  },
  {
    "area_name": "石渠县",
    "pinyin": "Shiqu",
    "level": 3,
    "area_code": 836,
    "post_code": 627350,
    "pid": 513300,
    "id": 2547,
    "area_id": 513332
  },
  {
    "area_name": "色达县",
    "pinyin": "Seda",
    "level": 3,
    "area_code": 836,
    "post_code": 626600,
    "pid": 513300,
    "id": 2548,
    "area_id": 513333
  },
  {
    "area_name": "理塘县",
    "pinyin": "Litang",
    "level": 3,
    "area_code": 836,
    "post_code": 627550,
    "pid": 513300,
    "id": 2549,
    "area_id": 513334
  },
  {
    "area_name": "巴塘县",
    "pinyin": "Batang",
    "level": 3,
    "area_code": 836,
    "post_code": 627650,
    "pid": 513300,
    "id": 2550,
    "area_id": 513335
  },
  {
    "area_name": "乡城县",
    "pinyin": "Xiangcheng",
    "level": 3,
    "area_code": 836,
    "post_code": 627850,
    "pid": 513300,
    "id": 2551,
    "area_id": 513336
  },
  {
    "area_name": "稻城县",
    "pinyin": "Daocheng",
    "level": 3,
    "area_code": 836,
    "post_code": 627750,
    "pid": 513300,
    "id": 2552,
    "area_id": 513337
  },
  {
    "area_name": "得荣县",
    "pinyin": "Derong",
    "level": 3,
    "area_code": 836,
    "post_code": 627950,
    "pid": 513300,
    "id": 2553,
    "area_id": 513338
  },
  {
    "area_name": "凉山彝族自治州",
    "pinyin": "Liangshan",
    "level": 2,
    "area_code": 834,
    "post_code": 615000,
    "pid": 510000,
    "id": 2554,
    "area_id": 513400
  },
  {
    "area_name": "西昌市",
    "pinyin": "Xichang",
    "level": 3,
    "area_code": 835,
    "post_code": 615000,
    "pid": 513400,
    "id": 2555,
    "area_id": 513401
  },
  {
    "area_name": "木里藏族自治县",
    "pinyin": "Muli",
    "level": 3,
    "area_code": 851,
    "post_code": 615800,
    "pid": 513400,
    "id": 2556,
    "area_id": 513422
  },
  {
    "area_name": "盐源县",
    "pinyin": "Yanyuan",
    "level": 3,
    "area_code": 836,
    "post_code": 615700,
    "pid": 513400,
    "id": 2557,
    "area_id": 513423
  },
  {
    "area_name": "德昌县",
    "pinyin": "Dechang",
    "level": 3,
    "area_code": 837,
    "post_code": 615500,
    "pid": 513400,
    "id": 2558,
    "area_id": 513424
  },
  {
    "area_name": "会理县",
    "pinyin": "Huili",
    "level": 3,
    "area_code": 838,
    "post_code": 615100,
    "pid": 513400,
    "id": 2559,
    "area_id": 513425
  },
  {
    "area_name": "会东县",
    "pinyin": "Huidong",
    "level": 3,
    "area_code": 839,
    "post_code": 615200,
    "pid": 513400,
    "id": 2560,
    "area_id": 513426
  },
  {
    "area_name": "宁南县",
    "pinyin": "Ningnan",
    "level": 3,
    "area_code": 840,
    "post_code": 615400,
    "pid": 513400,
    "id": 2561,
    "area_id": 513427
  },
  {
    "area_name": "普格县",
    "pinyin": "Puge",
    "level": 3,
    "area_code": 841,
    "post_code": 615300,
    "pid": 513400,
    "id": 2562,
    "area_id": 513428
  },
  {
    "area_name": "布拖县",
    "pinyin": "Butuo",
    "level": 3,
    "area_code": 842,
    "post_code": 616350,
    "pid": 513400,
    "id": 2563,
    "area_id": 513429
  },
  {
    "area_name": "金阳县",
    "pinyin": "Jinyang",
    "level": 3,
    "area_code": 843,
    "post_code": 616250,
    "pid": 513400,
    "id": 2564,
    "area_id": 513430
  },
  {
    "area_name": "昭觉县",
    "pinyin": "Zhaojue",
    "level": 3,
    "area_code": 844,
    "post_code": 616150,
    "pid": 513400,
    "id": 2565,
    "area_id": 513431
  },
  {
    "area_name": "喜德县",
    "pinyin": "Xide",
    "level": 3,
    "area_code": 845,
    "post_code": 616750,
    "pid": 513400,
    "id": 2566,
    "area_id": 513432
  },
  {
    "area_name": "冕宁县",
    "pinyin": "Mianning",
    "level": 3,
    "area_code": 846,
    "post_code": 615600,
    "pid": 513400,
    "id": 2567,
    "area_id": 513433
  },
  {
    "area_name": "越西县",
    "pinyin": "Yuexi",
    "level": 3,
    "area_code": 847,
    "post_code": 616650,
    "pid": 513400,
    "id": 2568,
    "area_id": 513434
  },
  {
    "area_name": "甘洛县",
    "pinyin": "Ganluo",
    "level": 3,
    "area_code": 848,
    "post_code": 616850,
    "pid": 513400,
    "id": 2569,
    "area_id": 513435
  },
  {
    "area_name": "美姑县",
    "pinyin": "Meigu",
    "level": 3,
    "area_code": 849,
    "post_code": 616450,
    "pid": 513400,
    "id": 2570,
    "area_id": 513436
  },
  {
    "area_name": "雷波县",
    "pinyin": "Leibo",
    "level": 3,
    "area_code": 850,
    "post_code": 616550,
    "pid": 513400,
    "id": 2571,
    "area_id": 513437
  },
  {
    "area_name": "贵州省",
    "pinyin": "Guizhou",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2572,
    "area_id": 520000
  },
  {
    "area_name": "贵阳市",
    "pinyin": "Guiyang",
    "level": 2,
    "area_code": 851,
    "post_code": 550001,
    "pid": 520000,
    "id": 2573,
    "area_id": 520100
  },
  {
    "area_name": "南明区",
    "pinyin": "Nanming",
    "level": 3,
    "area_code": 851,
    "post_code": 550001,
    "pid": 520100,
    "id": 2574,
    "area_id": 520102
  },
  {
    "area_name": "云岩区",
    "pinyin": "Yunyan",
    "level": 3,
    "area_code": 851,
    "post_code": 550001,
    "pid": 520100,
    "id": 2575,
    "area_id": 520103
  },
  {
    "area_name": "花溪区",
    "pinyin": "Huaxi",
    "level": 3,
    "area_code": 851,
    "post_code": 550025,
    "pid": 520100,
    "id": 2576,
    "area_id": 520111
  },
  {
    "area_name": "乌当区",
    "pinyin": "Wudang",
    "level": 3,
    "area_code": 851,
    "post_code": 550018,
    "pid": 520100,
    "id": 2577,
    "area_id": 520112
  },
  {
    "area_name": "白云区",
    "pinyin": "Baiyun",
    "level": 3,
    "area_code": 851,
    "post_code": 550014,
    "pid": 520100,
    "id": 2578,
    "area_id": 520113
  },
  {
    "area_name": "观山湖区",
    "pinyin": "Guanshanhu",
    "level": 3,
    "area_code": 851,
    "post_code": 550009,
    "pid": 520100,
    "id": 2579,
    "area_id": 520115
  },
  {
    "area_name": "开阳县",
    "pinyin": "Kaiyang",
    "level": 3,
    "area_code": 851,
    "post_code": 550300,
    "pid": 520100,
    "id": 2580,
    "area_id": 520121
  },
  {
    "area_name": "息烽县",
    "pinyin": "Xifeng",
    "level": 3,
    "area_code": 851,
    "post_code": 551100,
    "pid": 520100,
    "id": 2581,
    "area_id": 520122
  },
  {
    "area_name": "修文县",
    "pinyin": "Xiuwen",
    "level": 3,
    "area_code": 851,
    "post_code": 550200,
    "pid": 520100,
    "id": 2582,
    "area_id": 520123
  },
  {
    "area_name": "清镇市",
    "pinyin": "Qingzhen",
    "level": 3,
    "area_code": 851,
    "post_code": 551400,
    "pid": 520100,
    "id": 2583,
    "area_id": 520181
  },
  {
    "area_name": "六盘水市",
    "pinyin": "Liupanshui",
    "level": 2,
    "area_code": 858,
    "post_code": 553400,
    "pid": 520000,
    "id": 2584,
    "area_id": 520200
  },
  {
    "area_name": "钟山区",
    "pinyin": "Zhongshan",
    "level": 3,
    "area_code": 858,
    "post_code": 553000,
    "pid": 520200,
    "id": 2585,
    "area_id": 520201
  },
  {
    "area_name": "六枝特区",
    "pinyin": "Liuzhi",
    "level": 3,
    "area_code": 858,
    "post_code": 553400,
    "pid": 520200,
    "id": 2586,
    "area_id": 520203
  },
  {
    "area_name": "水城县",
    "pinyin": "Shuicheng",
    "level": 3,
    "area_code": 858,
    "post_code": 553000,
    "pid": 520200,
    "id": 2587,
    "area_id": 520221
  },
  {
    "area_name": "盘县",
    "pinyin": "Panxian",
    "level": 3,
    "area_code": 858,
    "post_code": 561601,
    "pid": 520200,
    "id": 2588,
    "area_id": 520222
  },
  {
    "area_name": "遵义市",
    "pinyin": "Zunyi",
    "level": 2,
    "area_code": 852,
    "post_code": 563000,
    "pid": 520000,
    "id": 2589,
    "area_id": 520300
  },
  {
    "area_name": "红花岗区",
    "pinyin": "Honghuagang",
    "level": 3,
    "area_code": 852,
    "post_code": 563000,
    "pid": 520300,
    "id": 2590,
    "area_id": 520302
  },
  {
    "area_name": "汇川区",
    "pinyin": "Huichuan",
    "level": 3,
    "area_code": 852,
    "post_code": 563000,
    "pid": 520300,
    "id": 2591,
    "area_id": 520303
  },
  {
    "area_name": "遵义县",
    "pinyin": "Zunyi",
    "level": 3,
    "area_code": 852,
    "post_code": 563100,
    "pid": 520300,
    "id": 2592,
    "area_id": 520321
  },
  {
    "area_name": "桐梓县",
    "pinyin": "Tongzi",
    "level": 3,
    "area_code": 852,
    "post_code": 563200,
    "pid": 520300,
    "id": 2593,
    "area_id": 520322
  },
  {
    "area_name": "绥阳县",
    "pinyin": "Suiyang",
    "level": 3,
    "area_code": 852,
    "post_code": 563300,
    "pid": 520300,
    "id": 2594,
    "area_id": 520323
  },
  {
    "area_name": "正安县",
    "pinyin": "Zheng'an",
    "level": 3,
    "area_code": 852,
    "post_code": 563400,
    "pid": 520300,
    "id": 2595,
    "area_id": 520324
  },
  {
    "area_name": "道真仡佬族苗族自治县",
    "pinyin": "Daozhen",
    "level": 3,
    "area_code": 852,
    "post_code": 563500,
    "pid": 520300,
    "id": 2596,
    "area_id": 520325
  },
  {
    "area_name": "务川仡佬族苗族自治县",
    "pinyin": "Wuchuan",
    "level": 3,
    "area_code": 852,
    "post_code": 564300,
    "pid": 520300,
    "id": 2597,
    "area_id": 520326
  },
  {
    "area_name": "凤冈县",
    "pinyin": "Fenggang",
    "level": 3,
    "area_code": 852,
    "post_code": 564200,
    "pid": 520300,
    "id": 2598,
    "area_id": 520327
  },
  {
    "area_name": "湄潭县",
    "pinyin": "Meitan",
    "level": 3,
    "area_code": 852,
    "post_code": 564100,
    "pid": 520300,
    "id": 2599,
    "area_id": 520328
  },
  {
    "area_name": "余庆县",
    "pinyin": "Yuqing",
    "level": 3,
    "area_code": 852,
    "post_code": 564400,
    "pid": 520300,
    "id": 2600,
    "area_id": 520329
  },
  {
    "area_name": "习水县",
    "pinyin": "Xishui",
    "level": 3,
    "area_code": 852,
    "post_code": 564600,
    "pid": 520300,
    "id": 2601,
    "area_id": 520330
  },
  {
    "area_name": "赤水市",
    "pinyin": "Chishui",
    "level": 3,
    "area_code": 852,
    "post_code": 564700,
    "pid": 520300,
    "id": 2602,
    "area_id": 520381
  },
  {
    "area_name": "仁怀市",
    "pinyin": "Renhuai",
    "level": 3,
    "area_code": 852,
    "post_code": 564500,
    "pid": 520300,
    "id": 2603,
    "area_id": 520382
  },
  {
    "area_name": "安顺市",
    "pinyin": "Anshun",
    "level": 2,
    "area_code": 853,
    "post_code": 561000,
    "pid": 520000,
    "id": 2604,
    "area_id": 520400
  },
  {
    "area_name": "西秀区",
    "pinyin": "Xixiu",
    "level": 3,
    "area_code": 853,
    "post_code": 561000,
    "pid": 520400,
    "id": 2605,
    "area_id": 520402
  },
  {
    "area_name": "平坝区",
    "pinyin": "Pingba",
    "level": 3,
    "area_code": 853,
    "post_code": 561100,
    "pid": 520400,
    "id": 2606,
    "area_id": 520421
  },
  {
    "area_name": "普定县",
    "pinyin": "Puding",
    "level": 3,
    "area_code": 853,
    "post_code": 562100,
    "pid": 520400,
    "id": 2607,
    "area_id": 520422
  },
  {
    "area_name": "镇宁布依族苗族自治县",
    "pinyin": "Zhenning",
    "level": 3,
    "area_code": 853,
    "post_code": 561200,
    "pid": 520400,
    "id": 2608,
    "area_id": 520423
  },
  {
    "area_name": "关岭布依族苗族自治县",
    "pinyin": "Guanling",
    "level": 3,
    "area_code": 853,
    "post_code": 561300,
    "pid": 520400,
    "id": 2609,
    "area_id": 520424
  },
  {
    "area_name": "紫云苗族布依族自治县",
    "pinyin": "Ziyun",
    "level": 3,
    "area_code": 853,
    "post_code": 550800,
    "pid": 520400,
    "id": 2610,
    "area_id": 520425
  },
  {
    "area_name": "毕节市",
    "pinyin": "Bijie",
    "level": 2,
    "area_code": 857,
    "post_code": 551700,
    "pid": 520000,
    "id": 2611,
    "area_id": 520500
  },
  {
    "area_name": "七星关区",
    "pinyin": "Qixingguan",
    "level": 3,
    "area_code": 857,
    "post_code": 551700,
    "pid": 520500,
    "id": 2612,
    "area_id": 520502
  },
  {
    "area_name": "大方县",
    "pinyin": "Dafang",
    "level": 3,
    "area_code": 857,
    "post_code": 551600,
    "pid": 520500,
    "id": 2613,
    "area_id": 520521
  },
  {
    "area_name": "黔西县",
    "pinyin": "Qianxi",
    "level": 3,
    "area_code": 857,
    "post_code": 551500,
    "pid": 520500,
    "id": 2614,
    "area_id": 520522
  },
  {
    "area_name": "金沙县",
    "pinyin": "Jinsha",
    "level": 3,
    "area_code": 857,
    "post_code": 551800,
    "pid": 520500,
    "id": 2615,
    "area_id": 520523
  },
  {
    "area_name": "织金县",
    "pinyin": "Zhijin",
    "level": 3,
    "area_code": 857,
    "post_code": 552100,
    "pid": 520500,
    "id": 2616,
    "area_id": 520524
  },
  {
    "area_name": "纳雍县",
    "pinyin": "Nayong",
    "level": 3,
    "area_code": 857,
    "post_code": 553300,
    "pid": 520500,
    "id": 2617,
    "area_id": 520525
  },
  {
    "area_name": "威宁彝族回族苗族自治县",
    "pinyin": "Weining",
    "level": 3,
    "area_code": 857,
    "post_code": 553100,
    "pid": 520500,
    "id": 2618,
    "area_id": 520526
  },
  {
    "area_name": "赫章县",
    "pinyin": "Hezhang",
    "level": 3,
    "area_code": 857,
    "post_code": 553200,
    "pid": 520500,
    "id": 2619,
    "area_id": 520527
  },
  {
    "area_name": "铜仁市",
    "pinyin": "Tongren",
    "level": 2,
    "area_code": 856,
    "post_code": 554300,
    "pid": 520000,
    "id": 2620,
    "area_id": 520600
  },
  {
    "area_name": "碧江区",
    "pinyin": "Bijiang",
    "level": 3,
    "area_code": 856,
    "post_code": 554300,
    "pid": 520600,
    "id": 2621,
    "area_id": 520602
  },
  {
    "area_name": "万山区",
    "pinyin": "Wanshan",
    "level": 3,
    "area_code": 856,
    "post_code": 554200,
    "pid": 520600,
    "id": 2622,
    "area_id": 520603
  },
  {
    "area_name": "江口县",
    "pinyin": "Jiangkou",
    "level": 3,
    "area_code": 856,
    "post_code": 554400,
    "pid": 520600,
    "id": 2623,
    "area_id": 520621
  },
  {
    "area_name": "玉屏侗族自治县",
    "pinyin": "Yuping",
    "level": 3,
    "area_code": 856,
    "post_code": 554004,
    "pid": 520600,
    "id": 2624,
    "area_id": 520622
  },
  {
    "area_name": "石阡县",
    "pinyin": "Shiqian",
    "level": 3,
    "area_code": 856,
    "post_code": 555100,
    "pid": 520600,
    "id": 2625,
    "area_id": 520623
  },
  {
    "area_name": "思南县",
    "pinyin": "Sinan",
    "level": 3,
    "area_code": 856,
    "post_code": 565100,
    "pid": 520600,
    "id": 2626,
    "area_id": 520624
  },
  {
    "area_name": "印江土家族苗族自治县",
    "pinyin": "Yinjiang",
    "level": 3,
    "area_code": 856,
    "post_code": 555200,
    "pid": 520600,
    "id": 2627,
    "area_id": 520625
  },
  {
    "area_name": "德江县",
    "pinyin": "Dejiang",
    "level": 3,
    "area_code": 856,
    "post_code": 565200,
    "pid": 520600,
    "id": 2628,
    "area_id": 520626
  },
  {
    "area_name": "沿河土家族自治县",
    "pinyin": "Yuanhe",
    "level": 3,
    "area_code": 856,
    "post_code": 565300,
    "pid": 520600,
    "id": 2629,
    "area_id": 520627
  },
  {
    "area_name": "松桃苗族自治县",
    "pinyin": "Songtao",
    "level": 3,
    "area_code": 856,
    "post_code": 554100,
    "pid": 520600,
    "id": 2630,
    "area_id": 520628
  },
  {
    "area_name": "黔西南布依族苗族自治州",
    "pinyin": "Qianxinan",
    "level": 2,
    "area_code": 859,
    "post_code": 562400,
    "pid": 520000,
    "id": 2631,
    "area_id": 522300
  },
  {
    "area_name": "兴义市",
    "pinyin": "Xingyi",
    "level": 3,
    "area_code": 859,
    "post_code": 562400,
    "pid": 522300,
    "id": 2632,
    "area_id": 522301
  },
  {
    "area_name": "兴仁县",
    "pinyin": "Xingren",
    "level": 3,
    "area_code": 859,
    "post_code": 562300,
    "pid": 522300,
    "id": 2633,
    "area_id": 522322
  },
  {
    "area_name": "普安县",
    "pinyin": "Pu'an",
    "level": 3,
    "area_code": 859,
    "post_code": 561500,
    "pid": 522300,
    "id": 2634,
    "area_id": 522323
  },
  {
    "area_name": "晴隆县",
    "pinyin": "Qinglong",
    "level": 3,
    "area_code": 859,
    "post_code": 561400,
    "pid": 522300,
    "id": 2635,
    "area_id": 522324
  },
  {
    "area_name": "贞丰县",
    "pinyin": "Zhenfeng",
    "level": 3,
    "area_code": 859,
    "post_code": 562200,
    "pid": 522300,
    "id": 2636,
    "area_id": 522325
  },
  {
    "area_name": "望谟县",
    "pinyin": "Wangmo",
    "level": 3,
    "area_code": 859,
    "post_code": 552300,
    "pid": 522300,
    "id": 2637,
    "area_id": 522326
  },
  {
    "area_name": "册亨县",
    "pinyin": "Ceheng",
    "level": 3,
    "area_code": 859,
    "post_code": 552200,
    "pid": 522300,
    "id": 2638,
    "area_id": 522327
  },
  {
    "area_name": "安龙县",
    "pinyin": "Anlong",
    "level": 3,
    "area_code": 859,
    "post_code": 552400,
    "pid": 522300,
    "id": 2639,
    "area_id": 522328
  },
  {
    "area_name": "黔东南苗族侗族自治州",
    "pinyin": "Qiandongnan",
    "level": 2,
    "area_code": 855,
    "post_code": 556000,
    "pid": 520000,
    "id": 2640,
    "area_id": 522600
  },
  {
    "area_name": "凯里市",
    "pinyin": "Kaili",
    "level": 3,
    "area_code": 855,
    "post_code": 556000,
    "pid": 522600,
    "id": 2641,
    "area_id": 522601
  },
  {
    "area_name": "黄平县",
    "pinyin": "Huangping",
    "level": 3,
    "area_code": 855,
    "post_code": 556100,
    "pid": 522600,
    "id": 2642,
    "area_id": 522622
  },
  {
    "area_name": "施秉县",
    "pinyin": "Shibing",
    "level": 3,
    "area_code": 855,
    "post_code": 556200,
    "pid": 522600,
    "id": 2643,
    "area_id": 522623
  },
  {
    "area_name": "三穗县",
    "pinyin": "Sansui",
    "level": 3,
    "area_code": 855,
    "post_code": 556500,
    "pid": 522600,
    "id": 2644,
    "area_id": 522624
  },
  {
    "area_name": "镇远县",
    "pinyin": "Zhenyuan",
    "level": 3,
    "area_code": 855,
    "post_code": 557700,
    "pid": 522600,
    "id": 2645,
    "area_id": 522625
  },
  {
    "area_name": "岑巩县",
    "pinyin": "Cengong",
    "level": 3,
    "area_code": 855,
    "post_code": 557800,
    "pid": 522600,
    "id": 2646,
    "area_id": 522626
  },
  {
    "area_name": "天柱县",
    "pinyin": "Tianzhu",
    "level": 3,
    "area_code": 855,
    "post_code": 556600,
    "pid": 522600,
    "id": 2647,
    "area_id": 522627
  },
  {
    "area_name": "锦屏县",
    "pinyin": "Jinping",
    "level": 3,
    "area_code": 855,
    "post_code": 556700,
    "pid": 522600,
    "id": 2648,
    "area_id": 522628
  },
  {
    "area_name": "剑河县",
    "pinyin": "Jianhe",
    "level": 3,
    "area_code": 855,
    "post_code": 556400,
    "pid": 522600,
    "id": 2649,
    "area_id": 522629
  },
  {
    "area_name": "台江县",
    "pinyin": "Taijiang",
    "level": 3,
    "area_code": 855,
    "post_code": 556300,
    "pid": 522600,
    "id": 2650,
    "area_id": 522630
  },
  {
    "area_name": "黎平县",
    "pinyin": "Liping",
    "level": 3,
    "area_code": 855,
    "post_code": 557300,
    "pid": 522600,
    "id": 2651,
    "area_id": 522631
  },
  {
    "area_name": "榕江县",
    "pinyin": "Rongjiang",
    "level": 3,
    "area_code": 855,
    "post_code": 557200,
    "pid": 522600,
    "id": 2652,
    "area_id": 522632
  },
  {
    "area_name": "从江县",
    "pinyin": "Congjiang",
    "level": 3,
    "area_code": 855,
    "post_code": 557400,
    "pid": 522600,
    "id": 2653,
    "area_id": 522633
  },
  {
    "area_name": "雷山县",
    "pinyin": "Leishan",
    "level": 3,
    "area_code": 855,
    "post_code": 557100,
    "pid": 522600,
    "id": 2654,
    "area_id": 522634
  },
  {
    "area_name": "麻江县",
    "pinyin": "Majiang",
    "level": 3,
    "area_code": 855,
    "post_code": 557600,
    "pid": 522600,
    "id": 2655,
    "area_id": 522635
  },
  {
    "area_name": "丹寨县",
    "pinyin": "Danzhai",
    "level": 3,
    "area_code": 855,
    "post_code": 557500,
    "pid": 522600,
    "id": 2656,
    "area_id": 522636
  },
  {
    "area_name": "黔南布依族苗族自治州",
    "pinyin": "Qiannan",
    "level": 2,
    "area_code": 854,
    "post_code": 558000,
    "pid": 520000,
    "id": 2657,
    "area_id": 522700
  },
  {
    "area_name": "都匀市",
    "pinyin": "Duyun",
    "level": 3,
    "area_code": 854,
    "post_code": 558000,
    "pid": 522700,
    "id": 2658,
    "area_id": 522701
  },
  {
    "area_name": "福泉市",
    "pinyin": "Fuquan",
    "level": 3,
    "area_code": 854,
    "post_code": 550500,
    "pid": 522700,
    "id": 2659,
    "area_id": 522702
  },
  {
    "area_name": "荔波县",
    "pinyin": "Libo",
    "level": 3,
    "area_code": 854,
    "post_code": 558400,
    "pid": 522700,
    "id": 2660,
    "area_id": 522722
  },
  {
    "area_name": "贵定县",
    "pinyin": "Guiding",
    "level": 3,
    "area_code": 854,
    "post_code": 551300,
    "pid": 522700,
    "id": 2661,
    "area_id": 522723
  },
  {
    "area_name": "瓮安县",
    "pinyin": "Weng'an",
    "level": 3,
    "area_code": 854,
    "post_code": 550400,
    "pid": 522700,
    "id": 2662,
    "area_id": 522725
  },
  {
    "area_name": "独山县",
    "pinyin": "Dushan",
    "level": 3,
    "area_code": 854,
    "post_code": 558200,
    "pid": 522700,
    "id": 2663,
    "area_id": 522726
  },
  {
    "area_name": "平塘县",
    "pinyin": "Pingtang",
    "level": 3,
    "area_code": 854,
    "post_code": 558300,
    "pid": 522700,
    "id": 2664,
    "area_id": 522727
  },
  {
    "area_name": "罗甸县",
    "pinyin": "Luodian",
    "level": 3,
    "area_code": 854,
    "post_code": 550100,
    "pid": 522700,
    "id": 2665,
    "area_id": 522728
  },
  {
    "area_name": "长顺县",
    "pinyin": "Changshun",
    "level": 3,
    "area_code": 854,
    "post_code": 550700,
    "pid": 522700,
    "id": 2666,
    "area_id": 522729
  },
  {
    "area_name": "龙里县",
    "pinyin": "Longli",
    "level": 3,
    "area_code": 854,
    "post_code": 551200,
    "pid": 522700,
    "id": 2667,
    "area_id": 522730
  },
  {
    "area_name": "惠水县",
    "pinyin": "Huishui",
    "level": 3,
    "area_code": 854,
    "post_code": 550600,
    "pid": 522700,
    "id": 2668,
    "area_id": 522731
  },
  {
    "area_name": "三都水族自治县",
    "pinyin": "Sandu",
    "level": 3,
    "area_code": 854,
    "post_code": 558100,
    "pid": 522700,
    "id": 2669,
    "area_id": 522732
  },
  {
    "area_name": "云南省",
    "pinyin": "Yunnan",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2670,
    "area_id": 530000
  },
  {
    "area_name": "昆明市",
    "pinyin": "Kunming",
    "level": 2,
    "area_code": 871,
    "post_code": 650500,
    "pid": 530000,
    "id": 2671,
    "area_id": 530100
  },
  {
    "area_name": "五华区",
    "pinyin": "Wuhua",
    "level": 3,
    "area_code": 871,
    "post_code": 650021,
    "pid": 530100,
    "id": 2672,
    "area_id": 530102
  },
  {
    "area_name": "盘龙区",
    "pinyin": "Panlong",
    "level": 3,
    "area_code": 871,
    "post_code": 650051,
    "pid": 530100,
    "id": 2673,
    "area_id": 530103
  },
  {
    "area_name": "官渡区",
    "pinyin": "Guandu",
    "level": 3,
    "area_code": 871,
    "post_code": 650200,
    "pid": 530100,
    "id": 2674,
    "area_id": 530111
  },
  {
    "area_name": "西山区",
    "pinyin": "Xishan",
    "level": 3,
    "area_code": 871,
    "post_code": 650118,
    "pid": 530100,
    "id": 2675,
    "area_id": 530112
  },
  {
    "area_name": "东川区",
    "pinyin": "Dongchuan",
    "level": 3,
    "area_code": 871,
    "post_code": 654100,
    "pid": 530100,
    "id": 2676,
    "area_id": 530113
  },
  {
    "area_name": "呈贡区",
    "pinyin": "Chenggong",
    "level": 3,
    "area_code": 871,
    "post_code": 650500,
    "pid": 530100,
    "id": 2677,
    "area_id": 530114
  },
  {
    "area_name": "晋宁县",
    "pinyin": "Jinning",
    "level": 3,
    "area_code": 871,
    "post_code": 650600,
    "pid": 530100,
    "id": 2678,
    "area_id": 530122
  },
  {
    "area_name": "富民县",
    "pinyin": "Fumin",
    "level": 3,
    "area_code": 871,
    "post_code": 650400,
    "pid": 530100,
    "id": 2679,
    "area_id": 530124
  },
  {
    "area_name": "宜良县",
    "pinyin": "Yiliang",
    "level": 3,
    "area_code": 871,
    "post_code": 652100,
    "pid": 530100,
    "id": 2680,
    "area_id": 530125
  },
  {
    "area_name": "石林彝族自治县",
    "pinyin": "Shilin",
    "level": 3,
    "area_code": 871,
    "post_code": 652200,
    "pid": 530100,
    "id": 2681,
    "area_id": 530126
  },
  {
    "area_name": "嵩明县",
    "pinyin": "Songming",
    "level": 3,
    "area_code": 871,
    "post_code": 651700,
    "pid": 530100,
    "id": 2682,
    "area_id": 530127
  },
  {
    "area_name": "禄劝彝族苗族自治县",
    "pinyin": "Luquan",
    "level": 3,
    "area_code": 871,
    "post_code": 651500,
    "pid": 530100,
    "id": 2683,
    "area_id": 530128
  },
  {
    "area_name": "寻甸回族彝族自治县",
    "pinyin": "Xundian",
    "level": 3,
    "area_code": 871,
    "post_code": 655200,
    "pid": 530100,
    "id": 2684,
    "area_id": 530129
  },
  {
    "area_name": "安宁市",
    "pinyin": "Anning",
    "level": 3,
    "area_code": 871,
    "post_code": 650300,
    "pid": 530100,
    "id": 2685,
    "area_id": 530181
  },
  {
    "area_name": "曲靖市",
    "pinyin": "Qujing",
    "level": 2,
    "area_code": 874,
    "post_code": 655000,
    "pid": 530000,
    "id": 2686,
    "area_id": 530300
  },
  {
    "area_name": "麒麟区",
    "pinyin": "Qilin",
    "level": 3,
    "area_code": 874,
    "post_code": 655000,
    "pid": 530300,
    "id": 2687,
    "area_id": 530302
  },
  {
    "area_name": "马龙县",
    "pinyin": "Malong",
    "level": 3,
    "area_code": 874,
    "post_code": 655100,
    "pid": 530300,
    "id": 2688,
    "area_id": 530321
  },
  {
    "area_name": "陆良县",
    "pinyin": "Luliang",
    "level": 3,
    "area_code": 874,
    "post_code": 655600,
    "pid": 530300,
    "id": 2689,
    "area_id": 530322
  },
  {
    "area_name": "师宗县",
    "pinyin": "Shizong",
    "level": 3,
    "area_code": 874,
    "post_code": 655700,
    "pid": 530300,
    "id": 2690,
    "area_id": 530323
  },
  {
    "area_name": "罗平县",
    "pinyin": "Luoping",
    "level": 3,
    "area_code": 874,
    "post_code": 655800,
    "pid": 530300,
    "id": 2691,
    "area_id": 530324
  },
  {
    "area_name": "富源县",
    "pinyin": "Fuyuan",
    "level": 3,
    "area_code": 874,
    "post_code": 655500,
    "pid": 530300,
    "id": 2692,
    "area_id": 530325
  },
  {
    "area_name": "会泽县",
    "pinyin": "Huize",
    "level": 3,
    "area_code": 874,
    "post_code": 654200,
    "pid": 530300,
    "id": 2693,
    "area_id": 530326
  },
  {
    "area_name": "沾益县",
    "pinyin": "Zhanyi",
    "level": 3,
    "area_code": 874,
    "post_code": 655331,
    "pid": 530300,
    "id": 2694,
    "area_id": 530328
  },
  {
    "area_name": "宣威市",
    "pinyin": "Xuanwei",
    "level": 3,
    "area_code": 874,
    "post_code": 655400,
    "pid": 530300,
    "id": 2695,
    "area_id": 530381
  },
  {
    "area_name": "玉溪市",
    "pinyin": "Yuxi",
    "level": 2,
    "area_code": 877,
    "post_code": 653100,
    "pid": 530000,
    "id": 2696,
    "area_id": 530400
  },
  {
    "area_name": "红塔区",
    "pinyin": "Hongta",
    "level": 3,
    "area_code": 877,
    "post_code": 653100,
    "pid": 530400,
    "id": 2697,
    "area_id": 530402
  },
  {
    "area_name": "江川县",
    "pinyin": "Jiangchuan",
    "level": 3,
    "area_code": 877,
    "post_code": 652600,
    "pid": 530400,
    "id": 2698,
    "area_id": 530421
  },
  {
    "area_name": "澄江县",
    "pinyin": "Chengjiang",
    "level": 3,
    "area_code": 877,
    "post_code": 652500,
    "pid": 530400,
    "id": 2699,
    "area_id": 530422
  },
  {
    "area_name": "通海县",
    "pinyin": "Tonghai",
    "level": 3,
    "area_code": 877,
    "post_code": 652700,
    "pid": 530400,
    "id": 2700,
    "area_id": 530423
  },
  {
    "area_name": "华宁县",
    "pinyin": "Huaning",
    "level": 3,
    "area_code": 877,
    "post_code": 652800,
    "pid": 530400,
    "id": 2701,
    "area_id": 530424
  },
  {
    "area_name": "易门县",
    "pinyin": "Yimen",
    "level": 3,
    "area_code": 877,
    "post_code": 651100,
    "pid": 530400,
    "id": 2702,
    "area_id": 530425
  },
  {
    "area_name": "峨山彝族自治县",
    "pinyin": "Eshan",
    "level": 3,
    "area_code": 877,
    "post_code": 653200,
    "pid": 530400,
    "id": 2703,
    "area_id": 530426
  },
  {
    "area_name": "新平彝族傣族自治县",
    "pinyin": "Xinping",
    "level": 3,
    "area_code": 877,
    "post_code": 653400,
    "pid": 530400,
    "id": 2704,
    "area_id": 530427
  },
  {
    "area_name": "元江哈尼族彝族傣族自治县",
    "pinyin": "Yuanjiang",
    "level": 3,
    "area_code": 877,
    "post_code": 653300,
    "pid": 530400,
    "id": 2705,
    "area_id": 530428
  },
  {
    "area_name": "保山市",
    "pinyin": "Baoshan",
    "level": 2,
    "area_code": 875,
    "post_code": 678000,
    "pid": 530000,
    "id": 2706,
    "area_id": 530500
  },
  {
    "area_name": "隆阳区",
    "pinyin": "Longyang",
    "level": 3,
    "area_code": 875,
    "post_code": 678000,
    "pid": 530500,
    "id": 2707,
    "area_id": 530502
  },
  {
    "area_name": "施甸县",
    "pinyin": "Shidian",
    "level": 3,
    "area_code": 875,
    "post_code": 678200,
    "pid": 530500,
    "id": 2708,
    "area_id": 530521
  },
  {
    "area_name": "腾冲县",
    "pinyin": "Tengchong",
    "level": 3,
    "area_code": 875,
    "post_code": 679100,
    "pid": 530500,
    "id": 2709,
    "area_id": 530522
  },
  {
    "area_name": "龙陵县",
    "pinyin": "Longling",
    "level": 3,
    "area_code": 875,
    "post_code": 678300,
    "pid": 530500,
    "id": 2710,
    "area_id": 530523
  },
  {
    "area_name": "昌宁县",
    "pinyin": "Changning",
    "level": 3,
    "area_code": 875,
    "post_code": 678100,
    "pid": 530500,
    "id": 2711,
    "area_id": 530524
  },
  {
    "area_name": "昭通市",
    "pinyin": "Zhaotong",
    "level": 2,
    "area_code": 870,
    "post_code": 657000,
    "pid": 530000,
    "id": 2712,
    "area_id": 530600
  },
  {
    "area_name": "昭阳区",
    "pinyin": "Zhaoyang",
    "level": 3,
    "area_code": 870,
    "post_code": 657000,
    "pid": 530600,
    "id": 2713,
    "area_id": 530602
  },
  {
    "area_name": "鲁甸县",
    "pinyin": "Ludian",
    "level": 3,
    "area_code": 870,
    "post_code": 657100,
    "pid": 530600,
    "id": 2714,
    "area_id": 530621
  },
  {
    "area_name": "巧家县",
    "pinyin": "Qiaojia",
    "level": 3,
    "area_code": 870,
    "post_code": 654600,
    "pid": 530600,
    "id": 2715,
    "area_id": 530622
  },
  {
    "area_name": "盐津县",
    "pinyin": "Yanjin",
    "level": 3,
    "area_code": 870,
    "post_code": 657500,
    "pid": 530600,
    "id": 2716,
    "area_id": 530623
  },
  {
    "area_name": "大关县",
    "pinyin": "Daguan",
    "level": 3,
    "area_code": 870,
    "post_code": 657400,
    "pid": 530600,
    "id": 2717,
    "area_id": 530624
  },
  {
    "area_name": "永善县",
    "pinyin": "Yongshan",
    "level": 3,
    "area_code": 870,
    "post_code": 657300,
    "pid": 530600,
    "id": 2718,
    "area_id": 530625
  },
  {
    "area_name": "绥江县",
    "pinyin": "Suijiang",
    "level": 3,
    "area_code": 870,
    "post_code": 657700,
    "pid": 530600,
    "id": 2719,
    "area_id": 530626
  },
  {
    "area_name": "镇雄县",
    "pinyin": "Zhenxiong",
    "level": 3,
    "area_code": 870,
    "post_code": 657200,
    "pid": 530600,
    "id": 2720,
    "area_id": 530627
  },
  {
    "area_name": "彝良县",
    "pinyin": "Yiliang",
    "level": 3,
    "area_code": 870,
    "post_code": 657600,
    "pid": 530600,
    "id": 2721,
    "area_id": 530628
  },
  {
    "area_name": "威信县",
    "pinyin": "Weixin",
    "level": 3,
    "area_code": 870,
    "post_code": 657900,
    "pid": 530600,
    "id": 2722,
    "area_id": 530629
  },
  {
    "area_name": "水富县",
    "pinyin": "Shuifu",
    "level": 3,
    "area_code": 870,
    "post_code": 657800,
    "pid": 530600,
    "id": 2723,
    "area_id": 530630
  },
  {
    "area_name": "丽江市",
    "pinyin": "Lijiang",
    "level": 2,
    "area_code": 888,
    "post_code": 674100,
    "pid": 530000,
    "id": 2724,
    "area_id": 530700
  },
  {
    "area_name": "古城区",
    "pinyin": "Gucheng",
    "level": 3,
    "area_code": 888,
    "post_code": 674100,
    "pid": 530700,
    "id": 2725,
    "area_id": 530702
  },
  {
    "area_name": "玉龙纳西族自治县",
    "pinyin": "Yulong",
    "level": 3,
    "area_code": 888,
    "post_code": 674100,
    "pid": 530700,
    "id": 2726,
    "area_id": 530721
  },
  {
    "area_name": "永胜县",
    "pinyin": "Yongsheng",
    "level": 3,
    "area_code": 888,
    "post_code": 674200,
    "pid": 530700,
    "id": 2727,
    "area_id": 530722
  },
  {
    "area_name": "华坪县",
    "pinyin": "Huaping",
    "level": 3,
    "area_code": 888,
    "post_code": 674800,
    "pid": 530700,
    "id": 2728,
    "area_id": 530723
  },
  {
    "area_name": "宁蒗彝族自治县",
    "pinyin": "Ninglang",
    "level": 3,
    "area_code": 888,
    "post_code": 674300,
    "pid": 530700,
    "id": 2729,
    "area_id": 530724
  },
  {
    "area_name": "普洱市",
    "pinyin": "Pu'er",
    "level": 2,
    "area_code": 879,
    "post_code": 665000,
    "pid": 530000,
    "id": 2730,
    "area_id": 530800
  },
  {
    "area_name": "思茅区",
    "pinyin": "Simao",
    "level": 3,
    "area_code": 879,
    "post_code": 665000,
    "pid": 530800,
    "id": 2731,
    "area_id": 530802
  },
  {
    "area_name": "宁洱哈尼族彝族自治县",
    "pinyin": "Ninger",
    "level": 3,
    "area_code": 879,
    "post_code": 665100,
    "pid": 530800,
    "id": 2732,
    "area_id": 530821
  },
  {
    "area_name": "墨江哈尼族自治县",
    "pinyin": "Mojiang",
    "level": 3,
    "area_code": 879,
    "post_code": 654800,
    "pid": 530800,
    "id": 2733,
    "area_id": 530822
  },
  {
    "area_name": "景东彝族自治县",
    "pinyin": "Jingdong",
    "level": 3,
    "area_code": 879,
    "post_code": 676200,
    "pid": 530800,
    "id": 2734,
    "area_id": 530823
  },
  {
    "area_name": "景谷傣族彝族自治县",
    "pinyin": "Jinggu",
    "level": 3,
    "area_code": 879,
    "post_code": 666400,
    "pid": 530800,
    "id": 2735,
    "area_id": 530824
  },
  {
    "area_name": "镇沅彝族哈尼族拉祜族自治县",
    "pinyin": "Zhenyuan",
    "level": 3,
    "area_code": 879,
    "post_code": 666500,
    "pid": 530800,
    "id": 2736,
    "area_id": 530825
  },
  {
    "area_name": "江城哈尼族彝族自治县",
    "pinyin": "Jiangcheng",
    "level": 3,
    "area_code": 879,
    "post_code": 665900,
    "pid": 530800,
    "id": 2737,
    "area_id": 530826
  },
  {
    "area_name": "孟连傣族拉祜族佤族自治县",
    "pinyin": "Menglian",
    "level": 3,
    "area_code": 879,
    "post_code": 665800,
    "pid": 530800,
    "id": 2738,
    "area_id": 530827
  },
  {
    "area_name": "澜沧拉祜族自治县",
    "pinyin": "Lancang",
    "level": 3,
    "area_code": 879,
    "post_code": 665600,
    "pid": 530800,
    "id": 2739,
    "area_id": 530828
  },
  {
    "area_name": "西盟佤族自治县",
    "pinyin": "Ximeng",
    "level": 3,
    "area_code": 879,
    "post_code": 665700,
    "pid": 530800,
    "id": 2740,
    "area_id": 530829
  },
  {
    "area_name": "临沧市",
    "pinyin": "Lincang",
    "level": 2,
    "area_code": 883,
    "post_code": 677000,
    "pid": 530000,
    "id": 2741,
    "area_id": 530900
  },
  {
    "area_name": "临翔区",
    "pinyin": "Linxiang",
    "level": 3,
    "area_code": 883,
    "post_code": 677000,
    "pid": 530900,
    "id": 2742,
    "area_id": 530902
  },
  {
    "area_name": "凤庆县",
    "pinyin": "Fengqing",
    "level": 3,
    "area_code": 883,
    "post_code": 675900,
    "pid": 530900,
    "id": 2743,
    "area_id": 530921
  },
  {
    "area_name": "云县",
    "pinyin": "Yunxian",
    "level": 3,
    "area_code": 883,
    "post_code": 675800,
    "pid": 530900,
    "id": 2744,
    "area_id": 530922
  },
  {
    "area_name": "永德县",
    "pinyin": "Yongde",
    "level": 3,
    "area_code": 883,
    "post_code": 677600,
    "pid": 530900,
    "id": 2745,
    "area_id": 530923
  },
  {
    "area_name": "镇康县",
    "pinyin": "Zhenkang",
    "level": 3,
    "area_code": 883,
    "post_code": 677704,
    "pid": 530900,
    "id": 2746,
    "area_id": 530924
  },
  {
    "area_name": "双江拉祜族佤族布朗族傣族自治县",
    "pinyin": "Shuangjiang",
    "level": 3,
    "area_code": 883,
    "post_code": 677300,
    "pid": 530900,
    "id": 2747,
    "area_id": 530925
  },
  {
    "area_name": "耿马傣族佤族自治县",
    "pinyin": "Gengma",
    "level": 3,
    "area_code": 883,
    "post_code": 677500,
    "pid": 530900,
    "id": 2748,
    "area_id": 530926
  },
  {
    "area_name": "沧源佤族自治县",
    "pinyin": "Cangyuan",
    "level": 3,
    "area_code": 883,
    "post_code": 677400,
    "pid": 530900,
    "id": 2749,
    "area_id": 530927
  },
  {
    "area_name": "楚雄彝族自治州",
    "pinyin": "Chuxiong",
    "level": 2,
    "area_code": 878,
    "post_code": 675000,
    "pid": 530000,
    "id": 2750,
    "area_id": 532300
  },
  {
    "area_name": "楚雄市",
    "pinyin": "Chuxiong",
    "level": 3,
    "area_code": 878,
    "post_code": 675000,
    "pid": 532300,
    "id": 2751,
    "area_id": 532301
  },
  {
    "area_name": "双柏县",
    "pinyin": "Shuangbai",
    "level": 3,
    "area_code": 878,
    "post_code": 675100,
    "pid": 532300,
    "id": 2752,
    "area_id": 532322
  },
  {
    "area_name": "牟定县",
    "pinyin": "Mouding",
    "level": 3,
    "area_code": 878,
    "post_code": 675500,
    "pid": 532300,
    "id": 2753,
    "area_id": 532323
  },
  {
    "area_name": "南华县",
    "pinyin": "Nanhua",
    "level": 3,
    "area_code": 878,
    "post_code": 675200,
    "pid": 532300,
    "id": 2754,
    "area_id": 532324
  },
  {
    "area_name": "姚安县",
    "pinyin": "Yao'an",
    "level": 3,
    "area_code": 878,
    "post_code": 675300,
    "pid": 532300,
    "id": 2755,
    "area_id": 532325
  },
  {
    "area_name": "大姚县",
    "pinyin": "Dayao",
    "level": 3,
    "area_code": 878,
    "post_code": 675400,
    "pid": 532300,
    "id": 2756,
    "area_id": 532326
  },
  {
    "area_name": "永仁县",
    "pinyin": "Yongren",
    "level": 3,
    "area_code": 878,
    "post_code": 651400,
    "pid": 532300,
    "id": 2757,
    "area_id": 532327
  },
  {
    "area_name": "元谋县",
    "pinyin": "Yuanmou",
    "level": 3,
    "area_code": 878,
    "post_code": 651300,
    "pid": 532300,
    "id": 2758,
    "area_id": 532328
  },
  {
    "area_name": "武定县",
    "pinyin": "Wuding",
    "level": 3,
    "area_code": 878,
    "post_code": 651600,
    "pid": 532300,
    "id": 2759,
    "area_id": 532329
  },
  {
    "area_name": "禄丰县",
    "pinyin": "Lufeng",
    "level": 3,
    "area_code": 878,
    "post_code": 651200,
    "pid": 532300,
    "id": 2760,
    "area_id": 532331
  },
  {
    "area_name": "红河哈尼族彝族自治州",
    "pinyin": "Honghe",
    "level": 2,
    "area_code": 873,
    "post_code": 661400,
    "pid": 530000,
    "id": 2761,
    "area_id": 532500
  },
  {
    "area_name": "个旧市",
    "pinyin": "Gejiu",
    "level": 3,
    "area_code": 873,
    "post_code": 661000,
    "pid": 532500,
    "id": 2762,
    "area_id": 532501
  },
  {
    "area_name": "开远市",
    "pinyin": "Kaiyuan",
    "level": 3,
    "area_code": 873,
    "post_code": 661600,
    "pid": 532500,
    "id": 2763,
    "area_id": 532502
  },
  {
    "area_name": "蒙自市",
    "pinyin": "Mengzi",
    "level": 3,
    "area_code": 873,
    "post_code": 661101,
    "pid": 532500,
    "id": 2764,
    "area_id": 532503
  },
  {
    "area_name": "弥勒市",
    "pinyin": "Mile ",
    "level": 3,
    "area_code": 873,
    "post_code": 652300,
    "pid": 532500,
    "id": 2765,
    "area_id": 532504
  },
  {
    "area_name": "屏边苗族自治县",
    "pinyin": "Pingbian",
    "level": 3,
    "area_code": 873,
    "post_code": 661200,
    "pid": 532500,
    "id": 2766,
    "area_id": 532523
  },
  {
    "area_name": "建水县",
    "pinyin": "Jianshui",
    "level": 3,
    "area_code": 873,
    "post_code": 654300,
    "pid": 532500,
    "id": 2767,
    "area_id": 532524
  },
  {
    "area_name": "石屏县",
    "pinyin": "Shiping",
    "level": 3,
    "area_code": 873,
    "post_code": 662200,
    "pid": 532500,
    "id": 2768,
    "area_id": 532525
  },
  {
    "area_name": "泸西县",
    "pinyin": "Luxi",
    "level": 3,
    "area_code": 873,
    "post_code": 652400,
    "pid": 532500,
    "id": 2769,
    "area_id": 532527
  },
  {
    "area_name": "元阳县",
    "pinyin": "Yuanyang",
    "level": 3,
    "area_code": 873,
    "post_code": 662400,
    "pid": 532500,
    "id": 2770,
    "area_id": 532528
  },
  {
    "area_name": "红河县",
    "pinyin": "Honghexian",
    "level": 3,
    "area_code": 873,
    "post_code": 654400,
    "pid": 532500,
    "id": 2771,
    "area_id": 532529
  },
  {
    "area_name": "金平苗族瑶族傣族自治县",
    "pinyin": "Jinping",
    "level": 3,
    "area_code": 873,
    "post_code": 661500,
    "pid": 532500,
    "id": 2772,
    "area_id": 532530
  },
  {
    "area_name": "绿春县",
    "pinyin": "Lvchun",
    "level": 3,
    "area_code": 873,
    "post_code": 662500,
    "pid": 532500,
    "id": 2773,
    "area_id": 532531
  },
  {
    "area_name": "河口瑶族自治县",
    "pinyin": "Hekou",
    "level": 3,
    "area_code": 873,
    "post_code": 661300,
    "pid": 532500,
    "id": 2774,
    "area_id": 532532
  },
  {
    "area_name": "文山壮族苗族自治州",
    "pinyin": "Wenshan",
    "level": 2,
    "area_code": 876,
    "post_code": 663000,
    "pid": 530000,
    "id": 2775,
    "area_id": 532600
  },
  {
    "area_name": "文山市",
    "pinyin": "Wenshan",
    "level": 3,
    "area_code": 876,
    "post_code": 663000,
    "pid": 532600,
    "id": 2776,
    "area_id": 532601
  },
  {
    "area_name": "砚山县",
    "pinyin": "Yanshan",
    "level": 3,
    "area_code": 876,
    "post_code": 663100,
    "pid": 532600,
    "id": 2777,
    "area_id": 532622
  },
  {
    "area_name": "西畴县",
    "pinyin": "Xichou",
    "level": 3,
    "area_code": 876,
    "post_code": 663500,
    "pid": 532600,
    "id": 2778,
    "area_id": 532623
  },
  {
    "area_name": "麻栗坡县",
    "pinyin": "Malipo",
    "level": 3,
    "area_code": 876,
    "post_code": 663600,
    "pid": 532600,
    "id": 2779,
    "area_id": 532624
  },
  {
    "area_name": "马关县",
    "pinyin": "Maguan",
    "level": 3,
    "area_code": 876,
    "post_code": 663700,
    "pid": 532600,
    "id": 2780,
    "area_id": 532625
  },
  {
    "area_name": "丘北县",
    "pinyin": "Qiubei",
    "level": 3,
    "area_code": 876,
    "post_code": 663200,
    "pid": 532600,
    "id": 2781,
    "area_id": 532626
  },
  {
    "area_name": "广南县",
    "pinyin": "Guangnan",
    "level": 3,
    "area_code": 876,
    "post_code": 663300,
    "pid": 532600,
    "id": 2782,
    "area_id": 532627
  },
  {
    "area_name": "富宁县",
    "pinyin": "Funing",
    "level": 3,
    "area_code": 876,
    "post_code": 663400,
    "pid": 532600,
    "id": 2783,
    "area_id": 532628
  },
  {
    "area_name": "西双版纳傣族自治州",
    "pinyin": "Xishuangbanna",
    "level": 2,
    "area_code": 691,
    "post_code": 666100,
    "pid": 530000,
    "id": 2784,
    "area_id": 532800
  },
  {
    "area_name": "景洪市",
    "pinyin": "Jinghong",
    "level": 3,
    "area_code": 691,
    "post_code": 666100,
    "pid": 532800,
    "id": 2785,
    "area_id": 532801
  },
  {
    "area_name": "勐海县",
    "pinyin": "Menghai",
    "level": 3,
    "area_code": 691,
    "post_code": 666200,
    "pid": 532800,
    "id": 2786,
    "area_id": 532822
  },
  {
    "area_name": "勐腊县",
    "pinyin": "Mengla",
    "level": 3,
    "area_code": 691,
    "post_code": 666300,
    "pid": 532800,
    "id": 2787,
    "area_id": 532823
  },
  {
    "area_name": "大理白族自治州",
    "pinyin": "Dali",
    "level": 2,
    "area_code": 872,
    "post_code": 671000,
    "pid": 530000,
    "id": 2788,
    "area_id": 532900
  },
  {
    "area_name": "大理市",
    "pinyin": "Dali",
    "level": 3,
    "area_code": 872,
    "post_code": 671000,
    "pid": 532900,
    "id": 2789,
    "area_id": 532901
  },
  {
    "area_name": "漾濞彝族自治县",
    "pinyin": "Yangbi",
    "level": 3,
    "area_code": 872,
    "post_code": 672500,
    "pid": 532900,
    "id": 2790,
    "area_id": 532922
  },
  {
    "area_name": "祥云县",
    "pinyin": "Xiangyun",
    "level": 3,
    "area_code": 872,
    "post_code": 672100,
    "pid": 532900,
    "id": 2791,
    "area_id": 532923
  },
  {
    "area_name": "宾川县",
    "pinyin": "Binchuan",
    "level": 3,
    "area_code": 872,
    "post_code": 671600,
    "pid": 532900,
    "id": 2792,
    "area_id": 532924
  },
  {
    "area_name": "弥渡县",
    "pinyin": "Midu",
    "level": 3,
    "area_code": 872,
    "post_code": 675600,
    "pid": 532900,
    "id": 2793,
    "area_id": 532925
  },
  {
    "area_name": "南涧彝族自治县",
    "pinyin": "Nanjian",
    "level": 3,
    "area_code": 872,
    "post_code": 675700,
    "pid": 532900,
    "id": 2794,
    "area_id": 532926
  },
  {
    "area_name": "巍山彝族回族自治县",
    "pinyin": "Weishan",
    "level": 3,
    "area_code": 872,
    "post_code": 672400,
    "pid": 532900,
    "id": 2795,
    "area_id": 532927
  },
  {
    "area_name": "永平县",
    "pinyin": "Yongping",
    "level": 3,
    "area_code": 872,
    "post_code": 672600,
    "pid": 532900,
    "id": 2796,
    "area_id": 532928
  },
  {
    "area_name": "云龙县",
    "pinyin": "Yunlong",
    "level": 3,
    "area_code": 872,
    "post_code": 672700,
    "pid": 532900,
    "id": 2797,
    "area_id": 532929
  },
  {
    "area_name": "洱源县",
    "pinyin": "Eryuan",
    "level": 3,
    "area_code": 872,
    "post_code": 671200,
    "pid": 532900,
    "id": 2798,
    "area_id": 532930
  },
  {
    "area_name": "剑川县",
    "pinyin": "Jianchuan",
    "level": 3,
    "area_code": 872,
    "post_code": 671300,
    "pid": 532900,
    "id": 2799,
    "area_id": 532931
  },
  {
    "area_name": "鹤庆县",
    "pinyin": "Heqing",
    "level": 3,
    "area_code": 872,
    "post_code": 671500,
    "pid": 532900,
    "id": 2800,
    "area_id": 532932
  },
  {
    "area_name": "德宏傣族景颇族自治州",
    "pinyin": "Dehong",
    "level": 2,
    "area_code": 692,
    "post_code": 678400,
    "pid": 530000,
    "id": 2801,
    "area_id": 533100
  },
  {
    "area_name": "瑞丽市",
    "pinyin": "Ruili",
    "level": 3,
    "area_code": 692,
    "post_code": 678600,
    "pid": 533100,
    "id": 2802,
    "area_id": 533102
  },
  {
    "area_name": "芒市",
    "pinyin": "Mangshi",
    "level": 3,
    "area_code": 692,
    "post_code": 678400,
    "pid": 533100,
    "id": 2803,
    "area_id": 533103
  },
  {
    "area_name": "梁河县",
    "pinyin": "Lianghe",
    "level": 3,
    "area_code": 692,
    "post_code": 679200,
    "pid": 533100,
    "id": 2804,
    "area_id": 533122
  },
  {
    "area_name": "盈江县",
    "pinyin": "Yingjiang",
    "level": 3,
    "area_code": 692,
    "post_code": 679300,
    "pid": 533100,
    "id": 2805,
    "area_id": 533123
  },
  {
    "area_name": "陇川县",
    "pinyin": "Longchuan",
    "level": 3,
    "area_code": 692,
    "post_code": 678700,
    "pid": 533100,
    "id": 2806,
    "area_id": 533124
  },
  {
    "area_name": "怒江傈僳族自治州",
    "pinyin": "Nujiang",
    "level": 2,
    "area_code": 886,
    "post_code": 673100,
    "pid": 530000,
    "id": 2807,
    "area_id": 533300
  },
  {
    "area_name": "泸水县",
    "pinyin": "Lushui",
    "level": 3,
    "area_code": 886,
    "post_code": 673100,
    "pid": 533300,
    "id": 2808,
    "area_id": 533321
  },
  {
    "area_name": "福贡县",
    "pinyin": "Fugong",
    "level": 3,
    "area_code": 886,
    "post_code": 673400,
    "pid": 533300,
    "id": 2809,
    "area_id": 533323
  },
  {
    "area_name": "贡山独龙族怒族自治县",
    "pinyin": "Gongshan",
    "level": 3,
    "area_code": 886,
    "post_code": 673500,
    "pid": 533300,
    "id": 2810,
    "area_id": 533324
  },
  {
    "area_name": "兰坪白族普米族自治县",
    "pinyin": "Lanping",
    "level": 3,
    "area_code": 886,
    "post_code": 671400,
    "pid": 533300,
    "id": 2811,
    "area_id": 533325
  },
  {
    "area_name": "迪庆藏族自治州",
    "pinyin": "Deqen",
    "level": 2,
    "area_code": 887,
    "post_code": 674400,
    "pid": 530000,
    "id": 2812,
    "area_id": 533400
  },
  {
    "area_name": "香格里拉市",
    "pinyin": "Xianggelila",
    "level": 3,
    "area_code": 887,
    "post_code": 674400,
    "pid": 533400,
    "id": 2813,
    "area_id": 533421
  },
  {
    "area_name": "德钦县",
    "pinyin": "Deqin",
    "level": 3,
    "area_code": 887,
    "post_code": 674500,
    "pid": 533400,
    "id": 2814,
    "area_id": 533422
  },
  {
    "area_name": "维西傈僳族自治县",
    "pinyin": "Weixi",
    "level": 3,
    "area_code": 887,
    "post_code": 674600,
    "pid": 533400,
    "id": 2815,
    "area_id": 533423
  },
  {
    "area_name": "西藏自治区",
    "pinyin": "Tibet",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2816,
    "area_id": 540000
  },
  {
    "area_name": "拉萨市",
    "pinyin": "Lhasa",
    "level": 2,
    "area_code": 891,
    "post_code": 850000,
    "pid": 540000,
    "id": 2817,
    "area_id": 540100
  },
  {
    "area_name": "城关区",
    "pinyin": "Chengguan",
    "level": 3,
    "area_code": 891,
    "post_code": 850000,
    "pid": 540100,
    "id": 2818,
    "area_id": 540102
  },
  {
    "area_name": "林周县",
    "pinyin": "Linzhou",
    "level": 3,
    "area_code": 891,
    "post_code": 851600,
    "pid": 540100,
    "id": 2819,
    "area_id": 540121
  },
  {
    "area_name": "当雄县",
    "pinyin": "Dangxiong",
    "level": 3,
    "area_code": 891,
    "post_code": 851500,
    "pid": 540100,
    "id": 2820,
    "area_id": 540122
  },
  {
    "area_name": "尼木县",
    "pinyin": "Nimu",
    "level": 3,
    "area_code": 891,
    "post_code": 851300,
    "pid": 540100,
    "id": 2821,
    "area_id": 540123
  },
  {
    "area_name": "曲水县",
    "pinyin": "Qushui",
    "level": 3,
    "area_code": 891,
    "post_code": 850600,
    "pid": 540100,
    "id": 2822,
    "area_id": 540124
  },
  {
    "area_name": "堆龙德庆县",
    "pinyin": "Duilongdeqing",
    "level": 3,
    "area_code": 891,
    "post_code": 851400,
    "pid": 540100,
    "id": 2823,
    "area_id": 540125
  },
  {
    "area_name": "达孜县",
    "pinyin": "Dazi",
    "level": 3,
    "area_code": 891,
    "post_code": 850100,
    "pid": 540100,
    "id": 2824,
    "area_id": 540126
  },
  {
    "area_name": "墨竹工卡县",
    "pinyin": "Mozhugongka",
    "level": 3,
    "area_code": 891,
    "post_code": 850200,
    "pid": 540100,
    "id": 2825,
    "area_id": 540127
  },
  {
    "area_name": "日喀则市",
    "pinyin": "Rikaze",
    "level": 2,
    "area_code": 892,
    "post_code": 857000,
    "pid": 540000,
    "id": 2826,
    "area_id": 540200
  },
  {
    "area_name": "桑珠孜区",
    "pinyin": "Sangzhuzi",
    "level": 3,
    "area_code": 892,
    "post_code": 857000,
    "pid": 540200,
    "id": 2827,
    "area_id": 540202
  },
  {
    "area_name": "南木林县",
    "pinyin": "Nanmulin",
    "level": 3,
    "area_code": 892,
    "post_code": 857100,
    "pid": 540200,
    "id": 2828,
    "area_id": 540221
  },
  {
    "area_name": "江孜县",
    "pinyin": "Jiangzi",
    "level": 3,
    "area_code": 892,
    "post_code": 857400,
    "pid": 540200,
    "id": 2829,
    "area_id": 540222
  },
  {
    "area_name": "定日县",
    "pinyin": "Dingri",
    "level": 3,
    "area_code": 892,
    "post_code": 858200,
    "pid": 540200,
    "id": 2830,
    "area_id": 540223
  },
  {
    "area_name": "萨迦县",
    "pinyin": "Sajia",
    "level": 3,
    "area_code": 892,
    "post_code": 857800,
    "pid": 540200,
    "id": 2831,
    "area_id": 540224
  },
  {
    "area_name": "拉孜县",
    "pinyin": "Lazi",
    "level": 3,
    "area_code": 892,
    "post_code": 858100,
    "pid": 540200,
    "id": 2832,
    "area_id": 540225
  },
  {
    "area_name": "昂仁县",
    "pinyin": "Angren",
    "level": 3,
    "area_code": 892,
    "post_code": 858500,
    "pid": 540200,
    "id": 2833,
    "area_id": 540226
  },
  {
    "area_name": "谢通门县",
    "pinyin": "Xietongmen",
    "level": 3,
    "area_code": 892,
    "post_code": 858900,
    "pid": 540200,
    "id": 2834,
    "area_id": 540227
  },
  {
    "area_name": "白朗县",
    "pinyin": "Bailang",
    "level": 3,
    "area_code": 892,
    "post_code": 857300,
    "pid": 540200,
    "id": 2835,
    "area_id": 540228
  },
  {
    "area_name": "仁布县",
    "pinyin": "Renbu",
    "level": 3,
    "area_code": 892,
    "post_code": 857200,
    "pid": 540200,
    "id": 2836,
    "area_id": 540229
  },
  {
    "area_name": "康马县",
    "pinyin": "Kangma",
    "level": 3,
    "area_code": 892,
    "post_code": 857500,
    "pid": 540200,
    "id": 2837,
    "area_id": 540230
  },
  {
    "area_name": "定结县",
    "pinyin": "Dingjie",
    "level": 3,
    "area_code": 892,
    "post_code": 857900,
    "pid": 540200,
    "id": 2838,
    "area_id": 540231
  },
  {
    "area_name": "仲巴县",
    "pinyin": "Zhongba",
    "level": 3,
    "area_code": 892,
    "post_code": 858800,
    "pid": 540200,
    "id": 2839,
    "area_id": 540232
  },
  {
    "area_name": "亚东县",
    "pinyin": "Yadong",
    "level": 3,
    "area_code": 892,
    "post_code": 857600,
    "pid": 540200,
    "id": 2840,
    "area_id": 540233
  },
  {
    "area_name": "吉隆县",
    "pinyin": "Jilong",
    "level": 3,
    "area_code": 892,
    "post_code": 858700,
    "pid": 540200,
    "id": 2841,
    "area_id": 540234
  },
  {
    "area_name": "聂拉木县",
    "pinyin": "Nielamu",
    "level": 3,
    "area_code": 892,
    "post_code": 858300,
    "pid": 540200,
    "id": 2842,
    "area_id": 540235
  },
  {
    "area_name": "萨嘎县",
    "pinyin": "Saga",
    "level": 3,
    "area_code": 892,
    "post_code": 857800,
    "pid": 540200,
    "id": 2843,
    "area_id": 540236
  },
  {
    "area_name": "岗巴县",
    "pinyin": "Gangba",
    "level": 3,
    "area_code": 892,
    "post_code": 857700,
    "pid": 540200,
    "id": 2844,
    "area_id": 540237
  },
  {
    "area_name": "昌都市",
    "pinyin": "Qamdo",
    "level": 2,
    "area_code": 895,
    "post_code": 854000,
    "pid": 540000,
    "id": 2845,
    "area_id": 540300
  },
  {
    "area_name": "卡若区",
    "pinyin": "Karuo",
    "level": 3,
    "area_code": 895,
    "post_code": 854000,
    "pid": 540300,
    "id": 2846,
    "area_id": 540302
  },
  {
    "area_name": "江达县",
    "pinyin": "Jiangda",
    "level": 3,
    "area_code": 895,
    "post_code": 854100,
    "pid": 540300,
    "id": 2847,
    "area_id": 540321
  },
  {
    "area_name": "贡觉县",
    "pinyin": "Gongjue",
    "level": 3,
    "area_code": 895,
    "post_code": 854200,
    "pid": 540300,
    "id": 2848,
    "area_id": 540322
  },
  {
    "area_name": "类乌齐县",
    "pinyin": "Leiwuqi",
    "level": 3,
    "area_code": 895,
    "post_code": 855600,
    "pid": 540300,
    "id": 2849,
    "area_id": 540323
  },
  {
    "area_name": "丁青县",
    "pinyin": "Dingqing",
    "level": 3,
    "area_code": 895,
    "post_code": 855700,
    "pid": 540300,
    "id": 2850,
    "area_id": 540324
  },
  {
    "area_name": "察雅县",
    "pinyin": "Chaya",
    "level": 3,
    "area_code": 895,
    "post_code": 854300,
    "pid": 540300,
    "id": 2851,
    "area_id": 540325
  },
  {
    "area_name": "八宿县",
    "pinyin": "Basu",
    "level": 3,
    "area_code": 895,
    "post_code": 854600,
    "pid": 540300,
    "id": 2852,
    "area_id": 540326
  },
  {
    "area_name": "左贡县",
    "pinyin": "Zuogong",
    "level": 3,
    "area_code": 895,
    "post_code": 854400,
    "pid": 540300,
    "id": 2853,
    "area_id": 540327
  },
  {
    "area_name": "芒康县",
    "pinyin": "Mangkang",
    "level": 3,
    "area_code": 895,
    "post_code": 854500,
    "pid": 540300,
    "id": 2854,
    "area_id": 540328
  },
  {
    "area_name": "洛隆县",
    "pinyin": "Luolong",
    "level": 3,
    "area_code": 895,
    "post_code": 855400,
    "pid": 540300,
    "id": 2855,
    "area_id": 540329
  },
  {
    "area_name": "边坝县",
    "pinyin": "Bianba",
    "level": 3,
    "area_code": 895,
    "post_code": 855500,
    "pid": 540300,
    "id": 2856,
    "area_id": 540330
  },
  {
    "area_name": "山南地区",
    "pinyin": "Shannan",
    "level": 2,
    "area_code": 893,
    "post_code": 856000,
    "pid": 540000,
    "id": 2857,
    "area_id": 542200
  },
  {
    "area_name": "乃东县",
    "pinyin": "Naidong",
    "level": 3,
    "area_code": 893,
    "post_code": 856100,
    "pid": 542200,
    "id": 2858,
    "area_id": 542221
  },
  {
    "area_name": "扎囊县",
    "pinyin": "Zhanang",
    "level": 3,
    "area_code": 893,
    "post_code": 850800,
    "pid": 542200,
    "id": 2859,
    "area_id": 542222
  },
  {
    "area_name": "贡嘎县",
    "pinyin": "Gongga",
    "level": 3,
    "area_code": 893,
    "post_code": 850700,
    "pid": 542200,
    "id": 2860,
    "area_id": 542223
  },
  {
    "area_name": "桑日县",
    "pinyin": "Sangri",
    "level": 3,
    "area_code": 893,
    "post_code": 856200,
    "pid": 542200,
    "id": 2861,
    "area_id": 542224
  },
  {
    "area_name": "琼结县",
    "pinyin": "Qiongjie",
    "level": 3,
    "area_code": 893,
    "post_code": 856800,
    "pid": 542200,
    "id": 2862,
    "area_id": 542225
  },
  {
    "area_name": "曲松县",
    "pinyin": "Qusong",
    "level": 3,
    "area_code": 893,
    "post_code": 856300,
    "pid": 542200,
    "id": 2863,
    "area_id": 542226
  },
  {
    "area_name": "措美县",
    "pinyin": "Cuomei",
    "level": 3,
    "area_code": 893,
    "post_code": 856900,
    "pid": 542200,
    "id": 2864,
    "area_id": 542227
  },
  {
    "area_name": "洛扎县",
    "pinyin": "Luozha",
    "level": 3,
    "area_code": 893,
    "post_code": 856600,
    "pid": 542200,
    "id": 2865,
    "area_id": 542228
  },
  {
    "area_name": "加查县",
    "pinyin": "Jiacha",
    "level": 3,
    "area_code": 893,
    "post_code": 856400,
    "pid": 542200,
    "id": 2866,
    "area_id": 542229
  },
  {
    "area_name": "隆子县",
    "pinyin": "Longzi",
    "level": 3,
    "area_code": 893,
    "post_code": 856600,
    "pid": 542200,
    "id": 2867,
    "area_id": 542231
  },
  {
    "area_name": "错那县",
    "pinyin": "Cuona",
    "level": 3,
    "area_code": 893,
    "post_code": 856700,
    "pid": 542200,
    "id": 2868,
    "area_id": 542232
  },
  {
    "area_name": "浪卡子县",
    "pinyin": "Langkazi",
    "level": 3,
    "area_code": 893,
    "post_code": 851100,
    "pid": 542200,
    "id": 2869,
    "area_id": 542233
  },
  {
    "area_name": "那曲地区",
    "pinyin": "Nagqu",
    "level": 2,
    "area_code": 896,
    "post_code": 852000,
    "pid": 540000,
    "id": 2870,
    "area_id": 542400
  },
  {
    "area_name": "那曲县",
    "pinyin": "Naqu",
    "level": 3,
    "area_code": 896,
    "post_code": 852000,
    "pid": 542400,
    "id": 2871,
    "area_id": 542421
  },
  {
    "area_name": "嘉黎县",
    "pinyin": "Jiali",
    "level": 3,
    "area_code": 896,
    "post_code": 852400,
    "pid": 542400,
    "id": 2872,
    "area_id": 542422
  },
  {
    "area_name": "比如县",
    "pinyin": "Biru",
    "level": 3,
    "area_code": 896,
    "post_code": 852300,
    "pid": 542400,
    "id": 2873,
    "area_id": 542423
  },
  {
    "area_name": "聂荣县",
    "pinyin": "Nierong",
    "level": 3,
    "area_code": 896,
    "post_code": 853500,
    "pid": 542400,
    "id": 2874,
    "area_id": 542424
  },
  {
    "area_name": "安多县",
    "pinyin": "Anduo",
    "level": 3,
    "area_code": 896,
    "post_code": 853400,
    "pid": 542400,
    "id": 2875,
    "area_id": 542425
  },
  {
    "area_name": "申扎县",
    "pinyin": "Shenzha",
    "level": 3,
    "area_code": 896,
    "post_code": 853100,
    "pid": 542400,
    "id": 2876,
    "area_id": 542426
  },
  {
    "area_name": "索县",
    "pinyin": "Suoxian",
    "level": 3,
    "area_code": 896,
    "post_code": 852200,
    "pid": 542400,
    "id": 2877,
    "area_id": 542427
  },
  {
    "area_name": "班戈县",
    "pinyin": "Bange",
    "level": 3,
    "area_code": 896,
    "post_code": 852500,
    "pid": 542400,
    "id": 2878,
    "area_id": 542428
  },
  {
    "area_name": "巴青县",
    "pinyin": "Baqing",
    "level": 3,
    "area_code": 896,
    "post_code": 852100,
    "pid": 542400,
    "id": 2879,
    "area_id": 542429
  },
  {
    "area_name": "尼玛县",
    "pinyin": "Nima",
    "level": 3,
    "area_code": 896,
    "post_code": 852600,
    "pid": 542400,
    "id": 2880,
    "area_id": 542430
  },
  {
    "area_name": "双湖县",
    "pinyin": "Shuanghu",
    "level": 3,
    "area_code": 896,
    "post_code": 852600,
    "pid": 542400,
    "id": 2881,
    "area_id": 542431
  },
  {
    "area_name": "阿里地区",
    "pinyin": "Ngari",
    "level": 2,
    "area_code": 897,
    "post_code": 859000,
    "pid": 540000,
    "id": 2882,
    "area_id": 542500
  },
  {
    "area_name": "普兰县",
    "pinyin": "Pulan",
    "level": 3,
    "area_code": 897,
    "post_code": 859500,
    "pid": 542500,
    "id": 2883,
    "area_id": 542521
  },
  {
    "area_name": "札达县",
    "pinyin": "Zhada",
    "level": 3,
    "area_code": 897,
    "post_code": 859600,
    "pid": 542500,
    "id": 2884,
    "area_id": 542522
  },
  {
    "area_name": "噶尔县",
    "pinyin": "Gaer",
    "level": 3,
    "area_code": 897,
    "post_code": 859400,
    "pid": 542500,
    "id": 2885,
    "area_id": 542523
  },
  {
    "area_name": "日土县",
    "pinyin": "Ritu",
    "level": 3,
    "area_code": 897,
    "post_code": 859700,
    "pid": 542500,
    "id": 2886,
    "area_id": 542524
  },
  {
    "area_name": "革吉县",
    "pinyin": "Geji",
    "level": 3,
    "area_code": 897,
    "post_code": 859100,
    "pid": 542500,
    "id": 2887,
    "area_id": 542525
  },
  {
    "area_name": "改则县",
    "pinyin": "Gaize",
    "level": 3,
    "area_code": 897,
    "post_code": 859200,
    "pid": 542500,
    "id": 2888,
    "area_id": 542526
  },
  {
    "area_name": "措勤县",
    "pinyin": "Cuoqin",
    "level": 3,
    "area_code": 897,
    "post_code": 859300,
    "pid": 542500,
    "id": 2889,
    "area_id": 542527
  },
  {
    "area_name": "林芝地区",
    "pinyin": "Nyingchi",
    "level": 2,
    "area_code": 894,
    "post_code": 850400,
    "pid": 540000,
    "id": 2890,
    "area_id": 542600
  },
  {
    "area_name": "林芝县",
    "pinyin": "Linzhi",
    "level": 3,
    "area_code": 894,
    "post_code": 850400,
    "pid": 542600,
    "id": 2891,
    "area_id": 542621
  },
  {
    "area_name": "工布江达县",
    "pinyin": "Gongbujiangda",
    "level": 3,
    "area_code": 894,
    "post_code": 850300,
    "pid": 542600,
    "id": 2892,
    "area_id": 542622
  },
  {
    "area_name": "米林县",
    "pinyin": "Milin",
    "level": 3,
    "area_code": 894,
    "post_code": 850500,
    "pid": 542600,
    "id": 2893,
    "area_id": 542623
  },
  {
    "area_name": "墨脱县",
    "pinyin": "Motuo",
    "level": 3,
    "area_code": 894,
    "post_code": 855300,
    "pid": 542600,
    "id": 2894,
    "area_id": 542624
  },
  {
    "area_name": "波密县",
    "pinyin": "Bomi",
    "level": 3,
    "area_code": 894,
    "post_code": 855200,
    "pid": 542600,
    "id": 2895,
    "area_id": 542625
  },
  {
    "area_name": "察隅县",
    "pinyin": "Chayu",
    "level": 3,
    "area_code": 894,
    "post_code": 855100,
    "pid": 542600,
    "id": 2896,
    "area_id": 542626
  },
  {
    "area_name": "朗县",
    "pinyin": "Langxian",
    "level": 3,
    "area_code": 894,
    "post_code": 856500,
    "pid": 542600,
    "id": 2897,
    "area_id": 542627
  },
  {
    "area_name": "陕西省",
    "pinyin": "Shaanxi",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 2898,
    "area_id": 610000
  },
  {
    "area_name": "西安市",
    "pinyin": "Xi'an",
    "level": 2,
    "area_code": 29,
    "post_code": 710003,
    "pid": 610000,
    "id": 2899,
    "area_id": 610100
  },
  {
    "area_name": "新城区",
    "pinyin": "Xincheng",
    "level": 3,
    "area_code": 29,
    "post_code": 710004,
    "pid": 610100,
    "id": 2900,
    "area_id": 610102
  },
  {
    "area_name": "碑林区",
    "pinyin": "Beilin",
    "level": 3,
    "area_code": 29,
    "post_code": 710001,
    "pid": 610100,
    "id": 2901,
    "area_id": 610103
  },
  {
    "area_name": "莲湖区",
    "pinyin": "Lianhu",
    "level": 3,
    "area_code": 29,
    "post_code": 710003,
    "pid": 610100,
    "id": 2902,
    "area_id": 610104
  },
  {
    "area_name": "灞桥区",
    "pinyin": "Baqiao",
    "level": 3,
    "area_code": 29,
    "post_code": 710038,
    "pid": 610100,
    "id": 2903,
    "area_id": 610111
  },
  {
    "area_name": "未央区",
    "pinyin": "Weiyang",
    "level": 3,
    "area_code": 29,
    "post_code": 710014,
    "pid": 610100,
    "id": 2904,
    "area_id": 610112
  },
  {
    "area_name": "雁塔区",
    "pinyin": "Yanta",
    "level": 3,
    "area_code": 29,
    "post_code": 710061,
    "pid": 610100,
    "id": 2905,
    "area_id": 610113
  },
  {
    "area_name": "阎良区",
    "pinyin": "Yanliang",
    "level": 3,
    "area_code": 29,
    "post_code": 710087,
    "pid": 610100,
    "id": 2906,
    "area_id": 610114
  },
  {
    "area_name": "临潼区",
    "pinyin": "Lintong",
    "level": 3,
    "area_code": 29,
    "post_code": 710600,
    "pid": 610100,
    "id": 2907,
    "area_id": 610115
  },
  {
    "area_name": "长安区",
    "pinyin": "Chang'an",
    "level": 3,
    "area_code": 29,
    "post_code": 710100,
    "pid": 610100,
    "id": 2908,
    "area_id": 610116
  },
  {
    "area_name": "蓝田县",
    "pinyin": "Lantian",
    "level": 3,
    "area_code": 29,
    "post_code": 710500,
    "pid": 610100,
    "id": 2909,
    "area_id": 610122
  },
  {
    "area_name": "周至县",
    "pinyin": "Zhouzhi",
    "level": 3,
    "area_code": 29,
    "post_code": 710400,
    "pid": 610100,
    "id": 2910,
    "area_id": 610124
  },
  {
    "area_name": "户县",
    "pinyin": "Huxian",
    "level": 3,
    "area_code": 29,
    "post_code": 710300,
    "pid": 610100,
    "id": 2911,
    "area_id": 610125
  },
  {
    "area_name": "高陵区",
    "pinyin": "Gaoling",
    "level": 3,
    "area_code": 29,
    "post_code": 710200,
    "pid": 610100,
    "id": 2912,
    "area_id": 610126
  },
  {
    "area_name": "铜川市",
    "pinyin": "Tongchuan",
    "level": 2,
    "area_code": 919,
    "post_code": 727100,
    "pid": 610000,
    "id": 2913,
    "area_id": 610200
  },
  {
    "area_name": "王益区",
    "pinyin": "Wangyi",
    "level": 3,
    "area_code": 919,
    "post_code": 727000,
    "pid": 610200,
    "id": 2914,
    "area_id": 610202
  },
  {
    "area_name": "印台区",
    "pinyin": "Yintai",
    "level": 3,
    "area_code": 919,
    "post_code": 727007,
    "pid": 610200,
    "id": 2915,
    "area_id": 610203
  },
  {
    "area_name": "耀州区",
    "pinyin": "Yaozhou",
    "level": 3,
    "area_code": 919,
    "post_code": 727100,
    "pid": 610200,
    "id": 2916,
    "area_id": 610204
  },
  {
    "area_name": "宜君县",
    "pinyin": "Yijun",
    "level": 3,
    "area_code": 919,
    "post_code": 727200,
    "pid": 610200,
    "id": 2917,
    "area_id": 610222
  },
  {
    "area_name": "宝鸡市",
    "pinyin": "Baoji",
    "level": 2,
    "area_code": 917,
    "post_code": 721000,
    "pid": 610000,
    "id": 2918,
    "area_id": 610300
  },
  {
    "area_name": "渭滨区",
    "pinyin": "Weibin",
    "level": 3,
    "area_code": 917,
    "post_code": 721000,
    "pid": 610300,
    "id": 2919,
    "area_id": 610302
  },
  {
    "area_name": "金台区",
    "pinyin": "Jintai",
    "level": 3,
    "area_code": 917,
    "post_code": 721000,
    "pid": 610300,
    "id": 2920,
    "area_id": 610303
  },
  {
    "area_name": "陈仓区",
    "pinyin": "Chencang",
    "level": 3,
    "area_code": 917,
    "post_code": 721300,
    "pid": 610300,
    "id": 2921,
    "area_id": 610304
  },
  {
    "area_name": "凤翔县",
    "pinyin": "Fengxiang",
    "level": 3,
    "area_code": 917,
    "post_code": 721400,
    "pid": 610300,
    "id": 2922,
    "area_id": 610322
  },
  {
    "area_name": "岐山县",
    "pinyin": "Qishan",
    "level": 3,
    "area_code": 917,
    "post_code": 722400,
    "pid": 610300,
    "id": 2923,
    "area_id": 610323
  },
  {
    "area_name": "扶风县",
    "pinyin": "Fufeng",
    "level": 3,
    "area_code": 917,
    "post_code": 722200,
    "pid": 610300,
    "id": 2924,
    "area_id": 610324
  },
  {
    "area_name": "眉县",
    "pinyin": "Meixian",
    "level": 3,
    "area_code": 917,
    "post_code": 722300,
    "pid": 610300,
    "id": 2925,
    "area_id": 610326
  },
  {
    "area_name": "陇县",
    "pinyin": "Longxian",
    "level": 3,
    "area_code": 917,
    "post_code": 721200,
    "pid": 610300,
    "id": 2926,
    "area_id": 610327
  },
  {
    "area_name": "千阳县",
    "pinyin": "Qianyang",
    "level": 3,
    "area_code": 917,
    "post_code": 721100,
    "pid": 610300,
    "id": 2927,
    "area_id": 610328
  },
  {
    "area_name": "麟游县",
    "pinyin": "Linyou",
    "level": 3,
    "area_code": 917,
    "post_code": 721500,
    "pid": 610300,
    "id": 2928,
    "area_id": 610329
  },
  {
    "area_name": "凤县",
    "pinyin": "Fengxian",
    "level": 3,
    "area_code": 917,
    "post_code": 721700,
    "pid": 610300,
    "id": 2929,
    "area_id": 610330
  },
  {
    "area_name": "太白县",
    "pinyin": "Taibai",
    "level": 3,
    "area_code": 917,
    "post_code": 721600,
    "pid": 610300,
    "id": 2930,
    "area_id": 610331
  },
  {
    "area_name": "咸阳市",
    "pinyin": "Xianyang",
    "level": 2,
    "area_code": 29,
    "post_code": 712000,
    "pid": 610000,
    "id": 2931,
    "area_id": 610400
  },
  {
    "area_name": "秦都区",
    "pinyin": "Qindu",
    "level": 3,
    "area_code": 29,
    "post_code": 712000,
    "pid": 610400,
    "id": 2932,
    "area_id": 610402
  },
  {
    "area_name": "杨陵区",
    "pinyin": "Yangling",
    "level": 3,
    "area_code": 29,
    "post_code": 712100,
    "pid": 610400,
    "id": 2933,
    "area_id": 610403
  },
  {
    "area_name": "渭城区",
    "pinyin": "Weicheng",
    "level": 3,
    "area_code": 29,
    "post_code": 712000,
    "pid": 610400,
    "id": 2934,
    "area_id": 610404
  },
  {
    "area_name": "三原县",
    "pinyin": "Sanyuan",
    "level": 3,
    "area_code": 29,
    "post_code": 713800,
    "pid": 610400,
    "id": 2935,
    "area_id": 610422
  },
  {
    "area_name": "泾阳县",
    "pinyin": "Jingyang",
    "level": 3,
    "area_code": 29,
    "post_code": 713700,
    "pid": 610400,
    "id": 2936,
    "area_id": 610423
  },
  {
    "area_name": "乾县",
    "pinyin": "Qianxian",
    "level": 3,
    "area_code": 29,
    "post_code": 713300,
    "pid": 610400,
    "id": 2937,
    "area_id": 610424
  },
  {
    "area_name": "礼泉县",
    "pinyin": "Liquan",
    "level": 3,
    "area_code": 29,
    "post_code": 713200,
    "pid": 610400,
    "id": 2938,
    "area_id": 610425
  },
  {
    "area_name": "永寿县",
    "pinyin": "Yongshou",
    "level": 3,
    "area_code": 29,
    "post_code": 713400,
    "pid": 610400,
    "id": 2939,
    "area_id": 610426
  },
  {
    "area_name": "彬县",
    "pinyin": "Binxian",
    "level": 3,
    "area_code": 29,
    "post_code": 713500,
    "pid": 610400,
    "id": 2940,
    "area_id": 610427
  },
  {
    "area_name": "长武县",
    "pinyin": "Changwu",
    "level": 3,
    "area_code": 29,
    "post_code": 713600,
    "pid": 610400,
    "id": 2941,
    "area_id": 610428
  },
  {
    "area_name": "旬邑县",
    "pinyin": "Xunyi",
    "level": 3,
    "area_code": 29,
    "post_code": 711300,
    "pid": 610400,
    "id": 2942,
    "area_id": 610429
  },
  {
    "area_name": "淳化县",
    "pinyin": "Chunhua",
    "level": 3,
    "area_code": 29,
    "post_code": 711200,
    "pid": 610400,
    "id": 2943,
    "area_id": 610430
  },
  {
    "area_name": "武功县",
    "pinyin": "Wugong",
    "level": 3,
    "area_code": 29,
    "post_code": 712200,
    "pid": 610400,
    "id": 2944,
    "area_id": 610431
  },
  {
    "area_name": "兴平市",
    "pinyin": "Xingping",
    "level": 3,
    "area_code": 29,
    "post_code": 713100,
    "pid": 610400,
    "id": 2945,
    "area_id": 610481
  },
  {
    "area_name": "渭南市",
    "pinyin": "Weinan",
    "level": 2,
    "area_code": 913,
    "post_code": 714000,
    "pid": 610000,
    "id": 2946,
    "area_id": 610500
  },
  {
    "area_name": "临渭区",
    "pinyin": "Linwei",
    "level": 3,
    "area_code": 913,
    "post_code": 714000,
    "pid": 610500,
    "id": 2947,
    "area_id": 610502
  },
  {
    "area_name": "华县",
    "pinyin": "Huaxian",
    "level": 3,
    "area_code": 913,
    "post_code": 714100,
    "pid": 610500,
    "id": 2948,
    "area_id": 610521
  },
  {
    "area_name": "潼关县",
    "pinyin": "Tongguan",
    "level": 3,
    "area_code": 913,
    "post_code": 714300,
    "pid": 610500,
    "id": 2949,
    "area_id": 610522
  },
  {
    "area_name": "大荔县",
    "pinyin": "Dali",
    "level": 3,
    "area_code": 913,
    "post_code": 715100,
    "pid": 610500,
    "id": 2950,
    "area_id": 610523
  },
  {
    "area_name": "合阳县",
    "pinyin": "Heyang",
    "level": 3,
    "area_code": 913,
    "post_code": 715300,
    "pid": 610500,
    "id": 2951,
    "area_id": 610524
  },
  {
    "area_name": "澄城县",
    "pinyin": "Chengcheng",
    "level": 3,
    "area_code": 913,
    "post_code": 715200,
    "pid": 610500,
    "id": 2952,
    "area_id": 610525
  },
  {
    "area_name": "蒲城县",
    "pinyin": "Pucheng",
    "level": 3,
    "area_code": 913,
    "post_code": 715500,
    "pid": 610500,
    "id": 2953,
    "area_id": 610526
  },
  {
    "area_name": "白水县",
    "pinyin": "Baishui",
    "level": 3,
    "area_code": 913,
    "post_code": 715600,
    "pid": 610500,
    "id": 2954,
    "area_id": 610527
  },
  {
    "area_name": "富平县",
    "pinyin": "Fuping",
    "level": 3,
    "area_code": 913,
    "post_code": 711700,
    "pid": 610500,
    "id": 2955,
    "area_id": 610528
  },
  {
    "area_name": "韩城市",
    "pinyin": "Hancheng",
    "level": 3,
    "area_code": 913,
    "post_code": 715400,
    "pid": 610500,
    "id": 2956,
    "area_id": 610581
  },
  {
    "area_name": "华阴市",
    "pinyin": "Huayin",
    "level": 3,
    "area_code": 913,
    "post_code": 714200,
    "pid": 610500,
    "id": 2957,
    "area_id": 610582
  },
  {
    "area_name": "延安市",
    "pinyin": "Yan'an",
    "level": 2,
    "area_code": 911,
    "post_code": 716000,
    "pid": 610000,
    "id": 2958,
    "area_id": 610600
  },
  {
    "area_name": "宝塔区",
    "pinyin": "Baota",
    "level": 3,
    "area_code": 911,
    "post_code": 716000,
    "pid": 610600,
    "id": 2959,
    "area_id": 610602
  },
  {
    "area_name": "延长县",
    "pinyin": "Yanchang",
    "level": 3,
    "area_code": 911,
    "post_code": 717100,
    "pid": 610600,
    "id": 2960,
    "area_id": 610621
  },
  {
    "area_name": "延川县",
    "pinyin": "Yanchuan",
    "level": 3,
    "area_code": 911,
    "post_code": 717200,
    "pid": 610600,
    "id": 2961,
    "area_id": 610622
  },
  {
    "area_name": "子长县",
    "pinyin": "Zichang",
    "level": 3,
    "area_code": 911,
    "post_code": 717300,
    "pid": 610600,
    "id": 2962,
    "area_id": 610623
  },
  {
    "area_name": "安塞县",
    "pinyin": "Ansai",
    "level": 3,
    "area_code": 911,
    "post_code": 717400,
    "pid": 610600,
    "id": 2963,
    "area_id": 610624
  },
  {
    "area_name": "志丹县",
    "pinyin": "Zhidan",
    "level": 3,
    "area_code": 911,
    "post_code": 717500,
    "pid": 610600,
    "id": 2964,
    "area_id": 610625
  },
  {
    "area_name": "吴起县",
    "pinyin": "Wuqi",
    "level": 3,
    "area_code": 911,
    "post_code": 717600,
    "pid": 610600,
    "id": 2965,
    "area_id": 610626
  },
  {
    "area_name": "甘泉县",
    "pinyin": "Ganquan",
    "level": 3,
    "area_code": 911,
    "post_code": 716100,
    "pid": 610600,
    "id": 2966,
    "area_id": 610627
  },
  {
    "area_name": "富县",
    "pinyin": "Fuxian",
    "level": 3,
    "area_code": 911,
    "post_code": 727500,
    "pid": 610600,
    "id": 2967,
    "area_id": 610628
  },
  {
    "area_name": "洛川县",
    "pinyin": "Luochuan",
    "level": 3,
    "area_code": 911,
    "post_code": 727400,
    "pid": 610600,
    "id": 2968,
    "area_id": 610629
  },
  {
    "area_name": "宜川县",
    "pinyin": "Yichuan",
    "level": 3,
    "area_code": 911,
    "post_code": 716200,
    "pid": 610600,
    "id": 2969,
    "area_id": 610630
  },
  {
    "area_name": "黄龙县",
    "pinyin": "Huanglong",
    "level": 3,
    "area_code": 911,
    "post_code": 715700,
    "pid": 610600,
    "id": 2970,
    "area_id": 610631
  },
  {
    "area_name": "黄陵县",
    "pinyin": "Huangling",
    "level": 3,
    "area_code": 911,
    "post_code": 727300,
    "pid": 610600,
    "id": 2971,
    "area_id": 610632
  },
  {
    "area_name": "汉中市",
    "pinyin": "Hanzhong",
    "level": 2,
    "area_code": 916,
    "post_code": 723000,
    "pid": 610000,
    "id": 2972,
    "area_id": 610700
  },
  {
    "area_name": "汉台区",
    "pinyin": "Hantai",
    "level": 3,
    "area_code": 916,
    "post_code": 723000,
    "pid": 610700,
    "id": 2973,
    "area_id": 610702
  },
  {
    "area_name": "南郑县",
    "pinyin": "Nanzheng",
    "level": 3,
    "area_code": 916,
    "post_code": 723100,
    "pid": 610700,
    "id": 2974,
    "area_id": 610721
  },
  {
    "area_name": "城固县",
    "pinyin": "Chenggu",
    "level": 3,
    "area_code": 916,
    "post_code": 723200,
    "pid": 610700,
    "id": 2975,
    "area_id": 610722
  },
  {
    "area_name": "洋县",
    "pinyin": "Yangxian",
    "level": 3,
    "area_code": 916,
    "post_code": 723300,
    "pid": 610700,
    "id": 2976,
    "area_id": 610723
  },
  {
    "area_name": "西乡县",
    "pinyin": "Xixiang",
    "level": 3,
    "area_code": 916,
    "post_code": 723500,
    "pid": 610700,
    "id": 2977,
    "area_id": 610724
  },
  {
    "area_name": "勉县",
    "pinyin": "Mianxian",
    "level": 3,
    "area_code": 916,
    "post_code": 724200,
    "pid": 610700,
    "id": 2978,
    "area_id": 610725
  },
  {
    "area_name": "宁强县",
    "pinyin": "Ningqiang",
    "level": 3,
    "area_code": 916,
    "post_code": 724400,
    "pid": 610700,
    "id": 2979,
    "area_id": 610726
  },
  {
    "area_name": "略阳县",
    "pinyin": "Lueyang",
    "level": 3,
    "area_code": 916,
    "post_code": 724300,
    "pid": 610700,
    "id": 2980,
    "area_id": 610727
  },
  {
    "area_name": "镇巴县",
    "pinyin": "Zhenba",
    "level": 3,
    "area_code": 916,
    "post_code": 723600,
    "pid": 610700,
    "id": 2981,
    "area_id": 610728
  },
  {
    "area_name": "留坝县",
    "pinyin": "Liuba",
    "level": 3,
    "area_code": 916,
    "post_code": 724100,
    "pid": 610700,
    "id": 2982,
    "area_id": 610729
  },
  {
    "area_name": "佛坪县",
    "pinyin": "Foping",
    "level": 3,
    "area_code": 916,
    "post_code": 723400,
    "pid": 610700,
    "id": 2983,
    "area_id": 610730
  },
  {
    "area_name": "榆林市",
    "pinyin": "Yulin",
    "level": 2,
    "area_code": 912,
    "post_code": 719000,
    "pid": 610000,
    "id": 2984,
    "area_id": 610800
  },
  {
    "area_name": "榆阳区",
    "pinyin": "Yuyang",
    "level": 3,
    "area_code": 912,
    "post_code": 719000,
    "pid": 610800,
    "id": 2985,
    "area_id": 610802
  },
  {
    "area_name": "神木县",
    "pinyin": "Shenmu",
    "level": 3,
    "area_code": 912,
    "post_code": 719300,
    "pid": 610800,
    "id": 2986,
    "area_id": 610821
  },
  {
    "area_name": "府谷县",
    "pinyin": "Fugu",
    "level": 3,
    "area_code": 912,
    "post_code": 719400,
    "pid": 610800,
    "id": 2987,
    "area_id": 610822
  },
  {
    "area_name": "横山县",
    "pinyin": "Hengshan",
    "level": 3,
    "area_code": 912,
    "post_code": 719100,
    "pid": 610800,
    "id": 2988,
    "area_id": 610823
  },
  {
    "area_name": "靖边县",
    "pinyin": "Jingbian",
    "level": 3,
    "area_code": 912,
    "post_code": 718500,
    "pid": 610800,
    "id": 2989,
    "area_id": 610824
  },
  {
    "area_name": "定边县",
    "pinyin": "Dingbian",
    "level": 3,
    "area_code": 912,
    "post_code": 718600,
    "pid": 610800,
    "id": 2990,
    "area_id": 610825
  },
  {
    "area_name": "绥德县",
    "pinyin": "Suide",
    "level": 3,
    "area_code": 912,
    "post_code": 718000,
    "pid": 610800,
    "id": 2991,
    "area_id": 610826
  },
  {
    "area_name": "米脂县",
    "pinyin": "Mizhi",
    "level": 3,
    "area_code": 912,
    "post_code": 718100,
    "pid": 610800,
    "id": 2992,
    "area_id": 610827
  },
  {
    "area_name": "佳县",
    "pinyin": "Jiaxian",
    "level": 3,
    "area_code": 912,
    "post_code": 719200,
    "pid": 610800,
    "id": 2993,
    "area_id": 610828
  },
  {
    "area_name": "吴堡县",
    "pinyin": "Wubu",
    "level": 3,
    "area_code": 912,
    "post_code": 718200,
    "pid": 610800,
    "id": 2994,
    "area_id": 610829
  },
  {
    "area_name": "清涧县",
    "pinyin": "Qingjian",
    "level": 3,
    "area_code": 912,
    "post_code": 718300,
    "pid": 610800,
    "id": 2995,
    "area_id": 610830
  },
  {
    "area_name": "子洲县",
    "pinyin": "Zizhou",
    "level": 3,
    "area_code": 912,
    "post_code": 718400,
    "pid": 610800,
    "id": 2996,
    "area_id": 610831
  },
  {
    "area_name": "安康市",
    "pinyin": "Ankang",
    "level": 2,
    "area_code": 915,
    "post_code": 725000,
    "pid": 610000,
    "id": 2997,
    "area_id": 610900
  },
  {
    "area_name": "汉滨区",
    "pinyin": "Hanbin",
    "level": 3,
    "area_code": 915,
    "post_code": 725000,
    "pid": 610900,
    "id": 2998,
    "area_id": 610902
  },
  {
    "area_name": "汉阴县",
    "pinyin": "Hanyin",
    "level": 3,
    "area_code": 915,
    "post_code": 725100,
    "pid": 610900,
    "id": 2999,
    "area_id": 610921
  },
  {
    "area_name": "石泉县",
    "pinyin": "Shiquan",
    "level": 3,
    "area_code": 915,
    "post_code": 725200,
    "pid": 610900,
    "id": 3000,
    "area_id": 610922
  },
  {
    "area_name": "宁陕县",
    "pinyin": "Ningshan",
    "level": 3,
    "area_code": 915,
    "post_code": 711600,
    "pid": 610900,
    "id": 3001,
    "area_id": 610923
  },
  {
    "area_name": "紫阳县",
    "pinyin": "Ziyang",
    "level": 3,
    "area_code": 915,
    "post_code": 725300,
    "pid": 610900,
    "id": 3002,
    "area_id": 610924
  },
  {
    "area_name": "岚皋县",
    "pinyin": "Langao",
    "level": 3,
    "area_code": 915,
    "post_code": 725400,
    "pid": 610900,
    "id": 3003,
    "area_id": 610925
  },
  {
    "area_name": "平利县",
    "pinyin": "Pingli",
    "level": 3,
    "area_code": 915,
    "post_code": 725500,
    "pid": 610900,
    "id": 3004,
    "area_id": 610926
  },
  {
    "area_name": "镇坪县",
    "pinyin": "Zhenping",
    "level": 3,
    "area_code": 915,
    "post_code": 725600,
    "pid": 610900,
    "id": 3005,
    "area_id": 610927
  },
  {
    "area_name": "旬阳县",
    "pinyin": "Xunyang",
    "level": 3,
    "area_code": 915,
    "post_code": 725700,
    "pid": 610900,
    "id": 3006,
    "area_id": 610928
  },
  {
    "area_name": "白河县",
    "pinyin": "Baihe",
    "level": 3,
    "area_code": 915,
    "post_code": 725800,
    "pid": 610900,
    "id": 3007,
    "area_id": 610929
  },
  {
    "area_name": "商洛市",
    "pinyin": "Shangluo",
    "level": 2,
    "area_code": 914,
    "post_code": 726000,
    "pid": 610000,
    "id": 3008,
    "area_id": 611000
  },
  {
    "area_name": "商州区",
    "pinyin": "Shangzhou",
    "level": 3,
    "area_code": 914,
    "post_code": 726000,
    "pid": 611000,
    "id": 3009,
    "area_id": 611002
  },
  {
    "area_name": "洛南县",
    "pinyin": "Luonan",
    "level": 3,
    "area_code": 914,
    "post_code": 726100,
    "pid": 611000,
    "id": 3010,
    "area_id": 611021
  },
  {
    "area_name": "丹凤县",
    "pinyin": "Danfeng",
    "level": 3,
    "area_code": 914,
    "post_code": 726200,
    "pid": 611000,
    "id": 3011,
    "area_id": 611022
  },
  {
    "area_name": "商南县",
    "pinyin": "Shangnan",
    "level": 3,
    "area_code": 914,
    "post_code": 726300,
    "pid": 611000,
    "id": 3012,
    "area_id": 611023
  },
  {
    "area_name": "山阳县",
    "pinyin": "Shanyang",
    "level": 3,
    "area_code": 914,
    "post_code": 726400,
    "pid": 611000,
    "id": 3013,
    "area_id": 611024
  },
  {
    "area_name": "镇安县",
    "pinyin": "Zhen'an",
    "level": 3,
    "area_code": 914,
    "post_code": 711500,
    "pid": 611000,
    "id": 3014,
    "area_id": 611025
  },
  {
    "area_name": "柞水县",
    "pinyin": "Zhashui",
    "level": 3,
    "area_code": 914,
    "post_code": 711400,
    "pid": 611000,
    "id": 3015,
    "area_id": 611026
  },
  {
    "area_name": "西咸新区",
    "pinyin": "Xixian",
    "level": 2,
    "area_code": 29,
    "post_code": 712000,
    "pid": 610000,
    "id": 3016,
    "area_id": 611100
  },
  {
    "area_name": "空港新城",
    "pinyin": "Konggang",
    "level": 3,
    "area_code": 374,
    "post_code": 461000,
    "pid": 611100,
    "id": 3017,
    "area_id": 611101
  },
  {
    "area_name": "沣东新城",
    "pinyin": "Fengdong",
    "level": 3,
    "area_code": 29,
    "post_code": 710000,
    "pid": 611100,
    "id": 3018,
    "area_id": 611102
  },
  {
    "area_name": "秦汉新城",
    "pinyin": "Qinhan",
    "level": 3,
    "area_code": 29,
    "post_code": 712000,
    "pid": 611100,
    "id": 3019,
    "area_id": 611103
  },
  {
    "area_name": "沣西新城",
    "pinyin": "Fengxi",
    "level": 3,
    "area_code": 29,
    "post_code": 710000,
    "pid": 611100,
    "id": 3020,
    "area_id": 611104
  },
  {
    "area_name": "泾河新城",
    "pinyin": "Jinghe",
    "level": 3,
    "area_code": 29,
    "post_code": 713700,
    "pid": 611100,
    "id": 3021,
    "area_id": 611105
  },
  {
    "area_name": "甘肃省",
    "pinyin": "Gansu",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3022,
    "area_id": 620000
  },
  {
    "area_name": "兰州市",
    "pinyin": "Lanzhou",
    "level": 2,
    "area_code": 931,
    "post_code": 730030,
    "pid": 620000,
    "id": 3023,
    "area_id": 620100
  },
  {
    "area_name": "城关区",
    "pinyin": "Chengguan",
    "level": 3,
    "area_code": 931,
    "post_code": 730030,
    "pid": 620100,
    "id": 3024,
    "area_id": 620102
  },
  {
    "area_name": "七里河区",
    "pinyin": "Qilihe",
    "level": 3,
    "area_code": 931,
    "post_code": 730050,
    "pid": 620100,
    "id": 3025,
    "area_id": 620103
  },
  {
    "area_name": "西固区",
    "pinyin": "Xigu",
    "level": 3,
    "area_code": 931,
    "post_code": 730060,
    "pid": 620100,
    "id": 3026,
    "area_id": 620104
  },
  {
    "area_name": "安宁区",
    "pinyin": "Anning",
    "level": 3,
    "area_code": 931,
    "post_code": 730070,
    "pid": 620100,
    "id": 3027,
    "area_id": 620105
  },
  {
    "area_name": "红古区",
    "pinyin": "Honggu",
    "level": 3,
    "area_code": 931,
    "post_code": 730084,
    "pid": 620100,
    "id": 3028,
    "area_id": 620111
  },
  {
    "area_name": "永登县",
    "pinyin": "Yongdeng",
    "level": 3,
    "area_code": 931,
    "post_code": 730300,
    "pid": 620100,
    "id": 3029,
    "area_id": 620121
  },
  {
    "area_name": "皋兰县",
    "pinyin": "Gaolan",
    "level": 3,
    "area_code": 931,
    "post_code": 730200,
    "pid": 620100,
    "id": 3030,
    "area_id": 620122
  },
  {
    "area_name": "榆中县",
    "pinyin": "Yuzhong",
    "level": 3,
    "area_code": 931,
    "post_code": 730100,
    "pid": 620100,
    "id": 3031,
    "area_id": 620123
  },
  {
    "area_name": "嘉峪关市",
    "pinyin": "Jiayuguan",
    "level": 2,
    "area_code": 937,
    "post_code": 735100,
    "pid": 620000,
    "id": 3032,
    "area_id": 620200
  },
  {
    "area_name": "雄关区",
    "pinyin": "Xiongguan",
    "level": 3,
    "area_code": 937,
    "post_code": 735100,
    "pid": 620200,
    "id": 3033,
    "area_id": 620201
  },
  {
    "area_name": "长城区",
    "pinyin": "Changcheng",
    "level": 3,
    "area_code": 937,
    "post_code": 735106,
    "pid": 620200,
    "id": 3034,
    "area_id": 620202
  },
  {
    "area_name": "镜铁区",
    "pinyin": "Jingtie",
    "level": 3,
    "area_code": 937,
    "post_code": 735100,
    "pid": 620200,
    "id": 3035,
    "area_id": 620203
  },
  {
    "area_name": "金昌市",
    "pinyin": "Jinchang",
    "level": 2,
    "area_code": 935,
    "post_code": 737100,
    "pid": 620000,
    "id": 3036,
    "area_id": 620300
  },
  {
    "area_name": "金川区",
    "pinyin": "Jinchuan",
    "level": 3,
    "area_code": 935,
    "post_code": 737100,
    "pid": 620300,
    "id": 3037,
    "area_id": 620302
  },
  {
    "area_name": "永昌县",
    "pinyin": "Yongchang",
    "level": 3,
    "area_code": 935,
    "post_code": 737200,
    "pid": 620300,
    "id": 3038,
    "area_id": 620321
  },
  {
    "area_name": "白银市",
    "pinyin": "Baiyin",
    "level": 2,
    "area_code": 943,
    "post_code": 730900,
    "pid": 620000,
    "id": 3039,
    "area_id": 620400
  },
  {
    "area_name": "白银区",
    "pinyin": "Baiyin",
    "level": 3,
    "area_code": 943,
    "post_code": 730900,
    "pid": 620400,
    "id": 3040,
    "area_id": 620402
  },
  {
    "area_name": "平川区",
    "pinyin": "Pingchuan",
    "level": 3,
    "area_code": 943,
    "post_code": 730913,
    "pid": 620400,
    "id": 3041,
    "area_id": 620403
  },
  {
    "area_name": "靖远县",
    "pinyin": "Jingyuan",
    "level": 3,
    "area_code": 943,
    "post_code": 730600,
    "pid": 620400,
    "id": 3042,
    "area_id": 620421
  },
  {
    "area_name": "会宁县",
    "pinyin": "Huining",
    "level": 3,
    "area_code": 943,
    "post_code": 730700,
    "pid": 620400,
    "id": 3043,
    "area_id": 620422
  },
  {
    "area_name": "景泰县",
    "pinyin": "Jingtai",
    "level": 3,
    "area_code": 943,
    "post_code": 730400,
    "pid": 620400,
    "id": 3044,
    "area_id": 620423
  },
  {
    "area_name": "天水市",
    "pinyin": "Tianshui",
    "level": 2,
    "area_code": 938,
    "post_code": 741000,
    "pid": 620000,
    "id": 3045,
    "area_id": 620500
  },
  {
    "area_name": "秦州区",
    "pinyin": "Qinzhou",
    "level": 3,
    "area_code": 938,
    "post_code": 741000,
    "pid": 620500,
    "id": 3046,
    "area_id": 620502
  },
  {
    "area_name": "麦积区",
    "pinyin": "Maiji",
    "level": 3,
    "area_code": 938,
    "post_code": 741020,
    "pid": 620500,
    "id": 3047,
    "area_id": 620503
  },
  {
    "area_name": "清水县",
    "pinyin": "Qingshui",
    "level": 3,
    "area_code": 938,
    "post_code": 741400,
    "pid": 620500,
    "id": 3048,
    "area_id": 620521
  },
  {
    "area_name": "秦安县",
    "pinyin": "Qin'an",
    "level": 3,
    "area_code": 938,
    "post_code": 741600,
    "pid": 620500,
    "id": 3049,
    "area_id": 620522
  },
  {
    "area_name": "甘谷县",
    "pinyin": "Gangu",
    "level": 3,
    "area_code": 938,
    "post_code": 741200,
    "pid": 620500,
    "id": 3050,
    "area_id": 620523
  },
  {
    "area_name": "武山县",
    "pinyin": "Wushan",
    "level": 3,
    "area_code": 938,
    "post_code": 741300,
    "pid": 620500,
    "id": 3051,
    "area_id": 620524
  },
  {
    "area_name": "张家川回族自治县",
    "pinyin": "Zhangjiachuan",
    "level": 3,
    "area_code": 938,
    "post_code": 741500,
    "pid": 620500,
    "id": 3052,
    "area_id": 620525
  },
  {
    "area_name": "武威市",
    "pinyin": "Wuwei",
    "level": 2,
    "area_code": 935,
    "post_code": 733000,
    "pid": 620000,
    "id": 3053,
    "area_id": 620600
  },
  {
    "area_name": "凉州区",
    "pinyin": "Liangzhou",
    "level": 3,
    "area_code": 935,
    "post_code": 733000,
    "pid": 620600,
    "id": 3054,
    "area_id": 620602
  },
  {
    "area_name": "民勤县",
    "pinyin": "Minqin",
    "level": 3,
    "area_code": 935,
    "post_code": 733300,
    "pid": 620600,
    "id": 3055,
    "area_id": 620621
  },
  {
    "area_name": "古浪县",
    "pinyin": "Gulang",
    "level": 3,
    "area_code": 935,
    "post_code": 733100,
    "pid": 620600,
    "id": 3056,
    "area_id": 620622
  },
  {
    "area_name": "天祝藏族自治县",
    "pinyin": "Tianzhu",
    "level": 3,
    "area_code": 935,
    "post_code": 733200,
    "pid": 620600,
    "id": 3057,
    "area_id": 620623
  },
  {
    "area_name": "张掖市",
    "pinyin": "Zhangye",
    "level": 2,
    "area_code": 936,
    "post_code": 734000,
    "pid": 620000,
    "id": 3058,
    "area_id": 620700
  },
  {
    "area_name": "甘州区",
    "pinyin": "Ganzhou",
    "level": 3,
    "area_code": 936,
    "post_code": 734000,
    "pid": 620700,
    "id": 3059,
    "area_id": 620702
  },
  {
    "area_name": "肃南裕固族自治县",
    "pinyin": "Sunan",
    "level": 3,
    "area_code": 936,
    "post_code": 734400,
    "pid": 620700,
    "id": 3060,
    "area_id": 620721
  },
  {
    "area_name": "民乐县",
    "pinyin": "Minle",
    "level": 3,
    "area_code": 936,
    "post_code": 734500,
    "pid": 620700,
    "id": 3061,
    "area_id": 620722
  },
  {
    "area_name": "临泽县",
    "pinyin": "Linze",
    "level": 3,
    "area_code": 936,
    "post_code": 734200,
    "pid": 620700,
    "id": 3062,
    "area_id": 620723
  },
  {
    "area_name": "高台县",
    "pinyin": "Gaotai",
    "level": 3,
    "area_code": 936,
    "post_code": 734300,
    "pid": 620700,
    "id": 3063,
    "area_id": 620724
  },
  {
    "area_name": "山丹县",
    "pinyin": "Shandan",
    "level": 3,
    "area_code": 936,
    "post_code": 734100,
    "pid": 620700,
    "id": 3064,
    "area_id": 620725
  },
  {
    "area_name": "平凉市",
    "pinyin": "Pingliang",
    "level": 2,
    "area_code": 933,
    "post_code": 744000,
    "pid": 620000,
    "id": 3065,
    "area_id": 620800
  },
  {
    "area_name": "崆峒区",
    "pinyin": "Kongtong",
    "level": 3,
    "area_code": 933,
    "post_code": 744000,
    "pid": 620800,
    "id": 3066,
    "area_id": 620802
  },
  {
    "area_name": "泾川县",
    "pinyin": "Jingchuan",
    "level": 3,
    "area_code": 933,
    "post_code": 744300,
    "pid": 620800,
    "id": 3067,
    "area_id": 620821
  },
  {
    "area_name": "灵台县",
    "pinyin": "Lingtai",
    "level": 3,
    "area_code": 933,
    "post_code": 744400,
    "pid": 620800,
    "id": 3068,
    "area_id": 620822
  },
  {
    "area_name": "崇信县",
    "pinyin": "Chongxin",
    "level": 3,
    "area_code": 933,
    "post_code": 744200,
    "pid": 620800,
    "id": 3069,
    "area_id": 620823
  },
  {
    "area_name": "华亭县",
    "pinyin": "Huating",
    "level": 3,
    "area_code": 933,
    "post_code": 744100,
    "pid": 620800,
    "id": 3070,
    "area_id": 620824
  },
  {
    "area_name": "庄浪县",
    "pinyin": "Zhuanglang",
    "level": 3,
    "area_code": 933,
    "post_code": 744600,
    "pid": 620800,
    "id": 3071,
    "area_id": 620825
  },
  {
    "area_name": "静宁县",
    "pinyin": "Jingning",
    "level": 3,
    "area_code": 933,
    "post_code": 743400,
    "pid": 620800,
    "id": 3072,
    "area_id": 620826
  },
  {
    "area_name": "酒泉市",
    "pinyin": "Jiuquan",
    "level": 2,
    "area_code": 937,
    "post_code": 735000,
    "pid": 620000,
    "id": 3073,
    "area_id": 620900
  },
  {
    "area_name": "肃州区",
    "pinyin": "Suzhou",
    "level": 3,
    "area_code": 937,
    "post_code": 735000,
    "pid": 620900,
    "id": 3074,
    "area_id": 620902
  },
  {
    "area_name": "金塔县",
    "pinyin": "Jinta",
    "level": 3,
    "area_code": 937,
    "post_code": 735300,
    "pid": 620900,
    "id": 3075,
    "area_id": 620921
  },
  {
    "area_name": "瓜州县",
    "pinyin": "Guazhou",
    "level": 3,
    "area_code": 937,
    "post_code": 736100,
    "pid": 620900,
    "id": 3076,
    "area_id": 620922
  },
  {
    "area_name": "肃北蒙古族自治县",
    "pinyin": "Subei",
    "level": 3,
    "area_code": 937,
    "post_code": 736300,
    "pid": 620900,
    "id": 3077,
    "area_id": 620923
  },
  {
    "area_name": "阿克塞哈萨克族自治县",
    "pinyin": "Akesai",
    "level": 3,
    "area_code": 937,
    "post_code": 736400,
    "pid": 620900,
    "id": 3078,
    "area_id": 620924
  },
  {
    "area_name": "玉门市",
    "pinyin": "Yumen",
    "level": 3,
    "area_code": 937,
    "post_code": 735200,
    "pid": 620900,
    "id": 3079,
    "area_id": 620981
  },
  {
    "area_name": "敦煌市",
    "pinyin": "Dunhuang",
    "level": 3,
    "area_code": 937,
    "post_code": 736200,
    "pid": 620900,
    "id": 3080,
    "area_id": 620982
  },
  {
    "area_name": "庆阳市",
    "pinyin": "Qingyang",
    "level": 2,
    "area_code": 934,
    "post_code": 745000,
    "pid": 620000,
    "id": 3081,
    "area_id": 621000
  },
  {
    "area_name": "西峰区",
    "pinyin": "Xifeng",
    "level": 3,
    "area_code": 934,
    "post_code": 745000,
    "pid": 621000,
    "id": 3082,
    "area_id": 621002
  },
  {
    "area_name": "庆城县",
    "pinyin": "Qingcheng",
    "level": 3,
    "area_code": 934,
    "post_code": 745100,
    "pid": 621000,
    "id": 3083,
    "area_id": 621021
  },
  {
    "area_name": "环县",
    "pinyin": "Huanxian",
    "level": 3,
    "area_code": 934,
    "post_code": 745700,
    "pid": 621000,
    "id": 3084,
    "area_id": 621022
  },
  {
    "area_name": "华池县",
    "pinyin": "Huachi",
    "level": 3,
    "area_code": 934,
    "post_code": 745600,
    "pid": 621000,
    "id": 3085,
    "area_id": 621023
  },
  {
    "area_name": "合水县",
    "pinyin": "Heshui",
    "level": 3,
    "area_code": 934,
    "post_code": 745400,
    "pid": 621000,
    "id": 3086,
    "area_id": 621024
  },
  {
    "area_name": "正宁县",
    "pinyin": "Zhengning",
    "level": 3,
    "area_code": 934,
    "post_code": 745300,
    "pid": 621000,
    "id": 3087,
    "area_id": 621025
  },
  {
    "area_name": "宁县",
    "pinyin": "Ningxian",
    "level": 3,
    "area_code": 934,
    "post_code": 745200,
    "pid": 621000,
    "id": 3088,
    "area_id": 621026
  },
  {
    "area_name": "镇原县",
    "pinyin": "Zhenyuan",
    "level": 3,
    "area_code": 934,
    "post_code": 744500,
    "pid": 621000,
    "id": 3089,
    "area_id": 621027
  },
  {
    "area_name": "定西市",
    "pinyin": "Dingxi",
    "level": 2,
    "area_code": 932,
    "post_code": 743000,
    "pid": 620000,
    "id": 3090,
    "area_id": 621100
  },
  {
    "area_name": "安定区",
    "pinyin": "Anding",
    "level": 3,
    "area_code": 932,
    "post_code": 743000,
    "pid": 621100,
    "id": 3091,
    "area_id": 621102
  },
  {
    "area_name": "通渭县",
    "pinyin": "Tongwei",
    "level": 3,
    "area_code": 932,
    "post_code": 743300,
    "pid": 621100,
    "id": 3092,
    "area_id": 621121
  },
  {
    "area_name": "陇西县",
    "pinyin": "Longxi",
    "level": 3,
    "area_code": 932,
    "post_code": 748100,
    "pid": 621100,
    "id": 3093,
    "area_id": 621122
  },
  {
    "area_name": "渭源县",
    "pinyin": "Weiyuan",
    "level": 3,
    "area_code": 932,
    "post_code": 748200,
    "pid": 621100,
    "id": 3094,
    "area_id": 621123
  },
  {
    "area_name": "临洮县",
    "pinyin": "Lintao",
    "level": 3,
    "area_code": 932,
    "post_code": 730500,
    "pid": 621100,
    "id": 3095,
    "area_id": 621124
  },
  {
    "area_name": "漳县",
    "pinyin": "Zhangxian",
    "level": 3,
    "area_code": 932,
    "post_code": 748300,
    "pid": 621100,
    "id": 3096,
    "area_id": 621125
  },
  {
    "area_name": "岷县",
    "pinyin": "Minxian",
    "level": 3,
    "area_code": 932,
    "post_code": 748400,
    "pid": 621100,
    "id": 3097,
    "area_id": 621126
  },
  {
    "area_name": "陇南市",
    "pinyin": "Longnan",
    "level": 2,
    "area_code": 939,
    "post_code": 746000,
    "pid": 620000,
    "id": 3098,
    "area_id": 621200
  },
  {
    "area_name": "武都区",
    "pinyin": "Wudu",
    "level": 3,
    "area_code": 939,
    "post_code": 746000,
    "pid": 621200,
    "id": 3099,
    "area_id": 621202
  },
  {
    "area_name": "成县",
    "pinyin": "Chengxian",
    "level": 3,
    "area_code": 939,
    "post_code": 742500,
    "pid": 621200,
    "id": 3100,
    "area_id": 621221
  },
  {
    "area_name": "文县",
    "pinyin": "Wenxian",
    "level": 3,
    "area_code": 939,
    "post_code": 746400,
    "pid": 621200,
    "id": 3101,
    "area_id": 621222
  },
  {
    "area_name": "宕昌县",
    "pinyin": "Dangchang",
    "level": 3,
    "area_code": 939,
    "post_code": 748500,
    "pid": 621200,
    "id": 3102,
    "area_id": 621223
  },
  {
    "area_name": "康县",
    "pinyin": "Kangxian",
    "level": 3,
    "area_code": 939,
    "post_code": 746500,
    "pid": 621200,
    "id": 3103,
    "area_id": 621224
  },
  {
    "area_name": "西和县",
    "pinyin": "Xihe",
    "level": 3,
    "area_code": 939,
    "post_code": 742100,
    "pid": 621200,
    "id": 3104,
    "area_id": 621225
  },
  {
    "area_name": "礼县",
    "pinyin": "Lixian",
    "level": 3,
    "area_code": 939,
    "post_code": 742200,
    "pid": 621200,
    "id": 3105,
    "area_id": 621226
  },
  {
    "area_name": "徽县",
    "pinyin": "Huixian",
    "level": 3,
    "area_code": 939,
    "post_code": 742300,
    "pid": 621200,
    "id": 3106,
    "area_id": 621227
  },
  {
    "area_name": "两当县",
    "pinyin": "Liangdang",
    "level": 3,
    "area_code": 939,
    "post_code": 742400,
    "pid": 621200,
    "id": 3107,
    "area_id": 621228
  },
  {
    "area_name": "临夏回族自治州",
    "pinyin": "Linxia",
    "level": 2,
    "area_code": 930,
    "post_code": 731100,
    "pid": 620000,
    "id": 3108,
    "area_id": 622900
  },
  {
    "area_name": "临夏市",
    "pinyin": "Linxia",
    "level": 3,
    "area_code": 930,
    "post_code": 731100,
    "pid": 622900,
    "id": 3109,
    "area_id": 622901
  },
  {
    "area_name": "临夏县",
    "pinyin": "Linxia",
    "level": 3,
    "area_code": 930,
    "post_code": 731800,
    "pid": 622900,
    "id": 3110,
    "area_id": 622921
  },
  {
    "area_name": "康乐县",
    "pinyin": "Kangle",
    "level": 3,
    "area_code": 930,
    "post_code": 731500,
    "pid": 622900,
    "id": 3111,
    "area_id": 622922
  },
  {
    "area_name": "永靖县",
    "pinyin": "Yongjing",
    "level": 3,
    "area_code": 930,
    "post_code": 731600,
    "pid": 622900,
    "id": 3112,
    "area_id": 622923
  },
  {
    "area_name": "广河县",
    "pinyin": "Guanghe",
    "level": 3,
    "area_code": 930,
    "post_code": 731300,
    "pid": 622900,
    "id": 3113,
    "area_id": 622924
  },
  {
    "area_name": "和政县",
    "pinyin": "Hezheng",
    "level": 3,
    "area_code": 930,
    "post_code": 731200,
    "pid": 622900,
    "id": 3114,
    "area_id": 622925
  },
  {
    "area_name": "东乡族自治县",
    "pinyin": "Dongxiangzu",
    "level": 3,
    "area_code": 930,
    "post_code": 731400,
    "pid": 622900,
    "id": 3115,
    "area_id": 622926
  },
  {
    "area_name": "积石山保安族东乡族撒拉族自治县",
    "pinyin": "Jishishan",
    "level": 3,
    "area_code": 930,
    "post_code": 731700,
    "pid": 622900,
    "id": 3116,
    "area_id": 622927
  },
  {
    "area_name": "甘南藏族自治州",
    "pinyin": "Gannan",
    "level": 2,
    "area_code": 941,
    "post_code": 747000,
    "pid": 620000,
    "id": 3117,
    "area_id": 623000
  },
  {
    "area_name": "合作市",
    "pinyin": "Hezuo",
    "level": 3,
    "area_code": 941,
    "post_code": 747000,
    "pid": 623000,
    "id": 3118,
    "area_id": 623001
  },
  {
    "area_name": "临潭县",
    "pinyin": "Lintan",
    "level": 3,
    "area_code": 941,
    "post_code": 747500,
    "pid": 623000,
    "id": 3119,
    "area_id": 623021
  },
  {
    "area_name": "卓尼县",
    "pinyin": "Zhuoni",
    "level": 3,
    "area_code": 941,
    "post_code": 747600,
    "pid": 623000,
    "id": 3120,
    "area_id": 623022
  },
  {
    "area_name": "舟曲县",
    "pinyin": "Zhouqu",
    "level": 3,
    "area_code": 941,
    "post_code": 746300,
    "pid": 623000,
    "id": 3121,
    "area_id": 623023
  },
  {
    "area_name": "迭部县",
    "pinyin": "Diebu",
    "level": 3,
    "area_code": 941,
    "post_code": 747400,
    "pid": 623000,
    "id": 3122,
    "area_id": 623024
  },
  {
    "area_name": "玛曲县",
    "pinyin": "Maqu",
    "level": 3,
    "area_code": 941,
    "post_code": 747300,
    "pid": 623000,
    "id": 3123,
    "area_id": 623025
  },
  {
    "area_name": "碌曲县",
    "pinyin": "Luqu",
    "level": 3,
    "area_code": 941,
    "post_code": 747200,
    "pid": 623000,
    "id": 3124,
    "area_id": 623026
  },
  {
    "area_name": "夏河县",
    "pinyin": "Xiahe",
    "level": 3,
    "area_code": 941,
    "post_code": 747100,
    "pid": 623000,
    "id": 3125,
    "area_id": 623027
  },
  {
    "area_name": "青海省",
    "pinyin": "Qinghai",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3126,
    "area_id": 630000
  },
  {
    "area_name": "西宁市",
    "pinyin": "Xining",
    "level": 2,
    "area_code": 971,
    "post_code": 810000,
    "pid": 630000,
    "id": 3127,
    "area_id": 630100
  },
  {
    "area_name": "城东区",
    "pinyin": "Chengdong",
    "level": 3,
    "area_code": 971,
    "post_code": 810007,
    "pid": 630100,
    "id": 3128,
    "area_id": 630102
  },
  {
    "area_name": "城中区",
    "pinyin": "Chengzhong",
    "level": 3,
    "area_code": 971,
    "post_code": 810000,
    "pid": 630100,
    "id": 3129,
    "area_id": 630103
  },
  {
    "area_name": "城西区",
    "pinyin": "Chengxi",
    "level": 3,
    "area_code": 971,
    "post_code": 810001,
    "pid": 630100,
    "id": 3130,
    "area_id": 630104
  },
  {
    "area_name": "城北区",
    "pinyin": "Chengbei",
    "level": 3,
    "area_code": 971,
    "post_code": 810003,
    "pid": 630100,
    "id": 3131,
    "area_id": 630105
  },
  {
    "area_name": "大通回族土族自治县",
    "pinyin": "Datong",
    "level": 3,
    "area_code": 971,
    "post_code": 810100,
    "pid": 630100,
    "id": 3132,
    "area_id": 630121
  },
  {
    "area_name": "湟中县",
    "pinyin": "Huangzhong",
    "level": 3,
    "area_code": 971,
    "post_code": 811600,
    "pid": 630100,
    "id": 3133,
    "area_id": 630122
  },
  {
    "area_name": "湟源县",
    "pinyin": "Huangyuan",
    "level": 3,
    "area_code": 971,
    "post_code": 812100,
    "pid": 630100,
    "id": 3134,
    "area_id": 630123
  },
  {
    "area_name": "海东市",
    "pinyin": "Haidong",
    "level": 2,
    "area_code": 972,
    "post_code": 810700,
    "pid": 630000,
    "id": 3135,
    "area_id": 630200
  },
  {
    "area_name": "乐都区",
    "pinyin": "Ledu",
    "level": 3,
    "area_code": 972,
    "post_code": 810700,
    "pid": 630200,
    "id": 3136,
    "area_id": 630202
  },
  {
    "area_name": "平安县",
    "pinyin": "Ping'an",
    "level": 3,
    "area_code": 972,
    "post_code": 810600,
    "pid": 630200,
    "id": 3137,
    "area_id": 630221
  },
  {
    "area_name": "民和回族土族自治县",
    "pinyin": "Minhe",
    "level": 3,
    "area_code": 972,
    "post_code": 810800,
    "pid": 630200,
    "id": 3138,
    "area_id": 630222
  },
  {
    "area_name": "互助土族自治县",
    "pinyin": "Huzhu",
    "level": 3,
    "area_code": 972,
    "post_code": 810500,
    "pid": 630200,
    "id": 3139,
    "area_id": 630223
  },
  {
    "area_name": "化隆回族自治县",
    "pinyin": "Hualong",
    "level": 3,
    "area_code": 972,
    "post_code": 810900,
    "pid": 630200,
    "id": 3140,
    "area_id": 630224
  },
  {
    "area_name": "循化撒拉族自治县",
    "pinyin": "Xunhua",
    "level": 3,
    "area_code": 972,
    "post_code": 811100,
    "pid": 630200,
    "id": 3141,
    "area_id": 630225
  },
  {
    "area_name": "海北藏族自治州",
    "pinyin": "Haibei",
    "level": 2,
    "area_code": 970,
    "post_code": 812200,
    "pid": 630000,
    "id": 3142,
    "area_id": 632200
  },
  {
    "area_name": "门源回族自治县",
    "pinyin": "Menyuan",
    "level": 3,
    "area_code": 970,
    "post_code": 810300,
    "pid": 632200,
    "id": 3143,
    "area_id": 632221
  },
  {
    "area_name": "祁连县",
    "pinyin": "Qilian",
    "level": 3,
    "area_code": 970,
    "post_code": 810400,
    "pid": 632200,
    "id": 3144,
    "area_id": 632222
  },
  {
    "area_name": "海晏县",
    "pinyin": "Haiyan",
    "level": 3,
    "area_code": 970,
    "post_code": 812200,
    "pid": 632200,
    "id": 3145,
    "area_id": 632223
  },
  {
    "area_name": "刚察县",
    "pinyin": "Gangcha",
    "level": 3,
    "area_code": 970,
    "post_code": 812300,
    "pid": 632200,
    "id": 3146,
    "area_id": 632224
  },
  {
    "area_name": "黄南藏族自治州",
    "pinyin": "Huangnan",
    "level": 2,
    "area_code": 973,
    "post_code": 811300,
    "pid": 630000,
    "id": 3147,
    "area_id": 632300
  },
  {
    "area_name": "同仁县",
    "pinyin": "Tongren",
    "level": 3,
    "area_code": 973,
    "post_code": 811300,
    "pid": 632300,
    "id": 3148,
    "area_id": 632321
  },
  {
    "area_name": "尖扎县",
    "pinyin": "Jianzha",
    "level": 3,
    "area_code": 973,
    "post_code": 811200,
    "pid": 632300,
    "id": 3149,
    "area_id": 632322
  },
  {
    "area_name": "泽库县",
    "pinyin": "Zeku",
    "level": 3,
    "area_code": 973,
    "post_code": 811400,
    "pid": 632300,
    "id": 3150,
    "area_id": 632323
  },
  {
    "area_name": "河南蒙古族自治县",
    "pinyin": "Henan",
    "level": 3,
    "area_code": 973,
    "post_code": 811500,
    "pid": 632300,
    "id": 3151,
    "area_id": 632324
  },
  {
    "area_name": "海南藏族自治州",
    "pinyin": "Hainan",
    "level": 2,
    "area_code": 974,
    "post_code": 813000,
    "pid": 630000,
    "id": 3152,
    "area_id": 632500
  },
  {
    "area_name": "共和县",
    "pinyin": "Gonghe",
    "level": 3,
    "area_code": 974,
    "post_code": 813000,
    "pid": 632500,
    "id": 3153,
    "area_id": 632521
  },
  {
    "area_name": "同德县",
    "pinyin": "Tongde",
    "level": 3,
    "area_code": 974,
    "post_code": 813200,
    "pid": 632500,
    "id": 3154,
    "area_id": 632522
  },
  {
    "area_name": "贵德县",
    "pinyin": "Guide",
    "level": 3,
    "area_code": 974,
    "post_code": 811700,
    "pid": 632500,
    "id": 3155,
    "area_id": 632523
  },
  {
    "area_name": "兴海县",
    "pinyin": "Xinghai",
    "level": 3,
    "area_code": 974,
    "post_code": 813300,
    "pid": 632500,
    "id": 3156,
    "area_id": 632524
  },
  {
    "area_name": "贵南县",
    "pinyin": "Guinan",
    "level": 3,
    "area_code": 974,
    "post_code": 813100,
    "pid": 632500,
    "id": 3157,
    "area_id": 632525
  },
  {
    "area_name": "果洛藏族自治州",
    "pinyin": "Golog",
    "level": 2,
    "area_code": 975,
    "post_code": 814000,
    "pid": 630000,
    "id": 3158,
    "area_id": 632600
  },
  {
    "area_name": "玛沁县",
    "pinyin": "Maqin",
    "level": 3,
    "area_code": 975,
    "post_code": 814000,
    "pid": 632600,
    "id": 3159,
    "area_id": 632621
  },
  {
    "area_name": "班玛县",
    "pinyin": "Banma",
    "level": 3,
    "area_code": 975,
    "post_code": 814300,
    "pid": 632600,
    "id": 3160,
    "area_id": 632622
  },
  {
    "area_name": "甘德县",
    "pinyin": "Gande",
    "level": 3,
    "area_code": 975,
    "post_code": 814100,
    "pid": 632600,
    "id": 3161,
    "area_id": 632623
  },
  {
    "area_name": "达日县",
    "pinyin": "Dari",
    "level": 3,
    "area_code": 975,
    "post_code": 814200,
    "pid": 632600,
    "id": 3162,
    "area_id": 632624
  },
  {
    "area_name": "久治县",
    "pinyin": "Jiuzhi",
    "level": 3,
    "area_code": 975,
    "post_code": 624700,
    "pid": 632600,
    "id": 3163,
    "area_id": 632625
  },
  {
    "area_name": "玛多县",
    "pinyin": "Maduo",
    "level": 3,
    "area_code": 975,
    "post_code": 813500,
    "pid": 632600,
    "id": 3164,
    "area_id": 632626
  },
  {
    "area_name": "玉树藏族自治州",
    "pinyin": "Yushu",
    "level": 2,
    "area_code": 976,
    "post_code": 815000,
    "pid": 630000,
    "id": 3165,
    "area_id": 632700
  },
  {
    "area_name": "玉树市",
    "pinyin": "Yushu",
    "level": 3,
    "area_code": 976,
    "post_code": 815000,
    "pid": 632700,
    "id": 3166,
    "area_id": 632701
  },
  {
    "area_name": "杂多县",
    "pinyin": "Zaduo",
    "level": 3,
    "area_code": 976,
    "post_code": 815300,
    "pid": 632700,
    "id": 3167,
    "area_id": 632722
  },
  {
    "area_name": "称多县",
    "pinyin": "Chenduo",
    "level": 3,
    "area_code": 976,
    "post_code": 815100,
    "pid": 632700,
    "id": 3168,
    "area_id": 632723
  },
  {
    "area_name": "治多县",
    "pinyin": "Zhiduo",
    "level": 3,
    "area_code": 976,
    "post_code": 815400,
    "pid": 632700,
    "id": 3169,
    "area_id": 632724
  },
  {
    "area_name": "囊谦县",
    "pinyin": "Nangqian",
    "level": 3,
    "area_code": 976,
    "post_code": 815200,
    "pid": 632700,
    "id": 3170,
    "area_id": 632725
  },
  {
    "area_name": "曲麻莱县",
    "pinyin": "Qumalai",
    "level": 3,
    "area_code": 976,
    "post_code": 815500,
    "pid": 632700,
    "id": 3171,
    "area_id": 632726
  },
  {
    "area_name": "海西蒙古族藏族自治州",
    "pinyin": "Haixi",
    "level": 2,
    "area_code": 977,
    "post_code": 817000,
    "pid": 630000,
    "id": 3172,
    "area_id": 632800
  },
  {
    "area_name": "格尔木市",
    "pinyin": "Geermu",
    "level": 3,
    "area_code": 977,
    "post_code": 816000,
    "pid": 632800,
    "id": 3173,
    "area_id": 632801
  },
  {
    "area_name": "德令哈市",
    "pinyin": "Delingha",
    "level": 3,
    "area_code": 977,
    "post_code": 817000,
    "pid": 632800,
    "id": 3174,
    "area_id": 632802
  },
  {
    "area_name": "乌兰县",
    "pinyin": "Wulan",
    "level": 3,
    "area_code": 977,
    "post_code": 817100,
    "pid": 632800,
    "id": 3175,
    "area_id": 632821
  },
  {
    "area_name": "都兰县",
    "pinyin": "Dulan",
    "level": 3,
    "area_code": 977,
    "post_code": 816100,
    "pid": 632800,
    "id": 3176,
    "area_id": 632822
  },
  {
    "area_name": "天峻县",
    "pinyin": "Tianjun",
    "level": 3,
    "area_code": 977,
    "post_code": 817200,
    "pid": 632800,
    "id": 3177,
    "area_id": 632823
  },
  {
    "area_name": "宁夏回族自治区",
    "pinyin": "Ningxia",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3178,
    "area_id": 640000
  },
  {
    "area_name": "银川市",
    "pinyin": "Yinchuan",
    "level": 2,
    "area_code": 951,
    "post_code": 750004,
    "pid": 640000,
    "id": 3179,
    "area_id": 640100
  },
  {
    "area_name": "兴庆区",
    "pinyin": "Xingqing",
    "level": 3,
    "area_code": 951,
    "post_code": 750001,
    "pid": 640100,
    "id": 3180,
    "area_id": 640104
  },
  {
    "area_name": "西夏区",
    "pinyin": "Xixia",
    "level": 3,
    "area_code": 951,
    "post_code": 750021,
    "pid": 640100,
    "id": 3181,
    "area_id": 640105
  },
  {
    "area_name": "金凤区",
    "pinyin": "Jinfeng",
    "level": 3,
    "area_code": 951,
    "post_code": 750011,
    "pid": 640100,
    "id": 3182,
    "area_id": 640106
  },
  {
    "area_name": "永宁县",
    "pinyin": "Yongning",
    "level": 3,
    "area_code": 951,
    "post_code": 750100,
    "pid": 640100,
    "id": 3183,
    "area_id": 640121
  },
  {
    "area_name": "贺兰县",
    "pinyin": "Helan",
    "level": 3,
    "area_code": 951,
    "post_code": 750200,
    "pid": 640100,
    "id": 3184,
    "area_id": 640122
  },
  {
    "area_name": "灵武市",
    "pinyin": "Lingwu",
    "level": 3,
    "area_code": 951,
    "post_code": 750004,
    "pid": 640100,
    "id": 3185,
    "area_id": 640181
  },
  {
    "area_name": "石嘴山市",
    "pinyin": "Shizuishan",
    "level": 2,
    "area_code": 952,
    "post_code": 753000,
    "pid": 640000,
    "id": 3186,
    "area_id": 640200
  },
  {
    "area_name": "大武口区",
    "pinyin": "Dawukou",
    "level": 3,
    "area_code": 952,
    "post_code": 753000,
    "pid": 640200,
    "id": 3187,
    "area_id": 640202
  },
  {
    "area_name": "惠农区",
    "pinyin": "Huinong",
    "level": 3,
    "area_code": 952,
    "post_code": 753600,
    "pid": 640200,
    "id": 3188,
    "area_id": 640205
  },
  {
    "area_name": "平罗县",
    "pinyin": "Pingluo",
    "level": 3,
    "area_code": 952,
    "post_code": 753400,
    "pid": 640200,
    "id": 3189,
    "area_id": 640221
  },
  {
    "area_name": "吴忠市",
    "pinyin": "Wuzhong",
    "level": 2,
    "area_code": 953,
    "post_code": 751100,
    "pid": 640000,
    "id": 3190,
    "area_id": 640300
  },
  {
    "area_name": "利通区",
    "pinyin": "Litong",
    "level": 3,
    "area_code": 953,
    "post_code": 751100,
    "pid": 640300,
    "id": 3191,
    "area_id": 640302
  },
  {
    "area_name": "红寺堡区",
    "pinyin": "Hongsibao",
    "level": 3,
    "area_code": 953,
    "post_code": 751900,
    "pid": 640300,
    "id": 3192,
    "area_id": 640303
  },
  {
    "area_name": "盐池县",
    "pinyin": "Yanchi",
    "level": 3,
    "area_code": 953,
    "post_code": 751500,
    "pid": 640300,
    "id": 3193,
    "area_id": 640323
  },
  {
    "area_name": "同心县",
    "pinyin": "Tongxin",
    "level": 3,
    "area_code": 953,
    "post_code": 751300,
    "pid": 640300,
    "id": 3194,
    "area_id": 640324
  },
  {
    "area_name": "青铜峡市",
    "pinyin": "Qingtongxia",
    "level": 3,
    "area_code": 953,
    "post_code": 751600,
    "pid": 640300,
    "id": 3195,
    "area_id": 640381
  },
  {
    "area_name": "固原市",
    "pinyin": "Guyuan",
    "level": 2,
    "area_code": 954,
    "post_code": 756000,
    "pid": 640000,
    "id": 3196,
    "area_id": 640400
  },
  {
    "area_name": "原州区",
    "pinyin": "Yuanzhou",
    "level": 3,
    "area_code": 954,
    "post_code": 756000,
    "pid": 640400,
    "id": 3197,
    "area_id": 640402
  },
  {
    "area_name": "西吉县",
    "pinyin": "Xiji",
    "level": 3,
    "area_code": 954,
    "post_code": 756200,
    "pid": 640400,
    "id": 3198,
    "area_id": 640422
  },
  {
    "area_name": "隆德县",
    "pinyin": "Longde",
    "level": 3,
    "area_code": 954,
    "post_code": 756300,
    "pid": 640400,
    "id": 3199,
    "area_id": 640423
  },
  {
    "area_name": "泾源县",
    "pinyin": "Jingyuan",
    "level": 3,
    "area_code": 954,
    "post_code": 756400,
    "pid": 640400,
    "id": 3200,
    "area_id": 640424
  },
  {
    "area_name": "彭阳县",
    "pinyin": "Pengyang",
    "level": 3,
    "area_code": 954,
    "post_code": 756500,
    "pid": 640400,
    "id": 3201,
    "area_id": 640425
  },
  {
    "area_name": "中卫市",
    "pinyin": "Zhongwei",
    "level": 2,
    "area_code": 955,
    "post_code": 751700,
    "pid": 640000,
    "id": 3202,
    "area_id": 640500
  },
  {
    "area_name": "沙坡头区",
    "pinyin": "Shapotou",
    "level": 3,
    "area_code": 955,
    "post_code": 755000,
    "pid": 640500,
    "id": 3203,
    "area_id": 640502
  },
  {
    "area_name": "中宁县",
    "pinyin": "Zhongning",
    "level": 3,
    "area_code": 955,
    "post_code": 751200,
    "pid": 640500,
    "id": 3204,
    "area_id": 640521
  },
  {
    "area_name": "海原县",
    "pinyin": "Haiyuan",
    "level": 3,
    "area_code": 955,
    "post_code": 751800,
    "pid": 640500,
    "id": 3205,
    "area_id": 640522
  },
  {
    "area_name": "新疆维吾尔自治区",
    "pinyin": "Xinjiang",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3206,
    "area_id": 650000
  },
  {
    "area_name": "乌鲁木齐市",
    "pinyin": "Urumqi",
    "level": 2,
    "area_code": 991,
    "post_code": 830002,
    "pid": 650000,
    "id": 3207,
    "area_id": 650100
  },
  {
    "area_name": "天山区",
    "pinyin": "Tianshan",
    "level": 3,
    "area_code": 991,
    "post_code": 830002,
    "pid": 650100,
    "id": 3208,
    "area_id": 650102
  },
  {
    "area_name": "沙依巴克区",
    "pinyin": "Shayibake",
    "level": 3,
    "area_code": 991,
    "post_code": 830000,
    "pid": 650100,
    "id": 3209,
    "area_id": 650103
  },
  {
    "area_name": "新市区",
    "pinyin": "Xinshi",
    "level": 3,
    "area_code": 991,
    "post_code": 830011,
    "pid": 650100,
    "id": 3210,
    "area_id": 650104
  },
  {
    "area_name": "水磨沟区",
    "pinyin": "Shuimogou",
    "level": 3,
    "area_code": 991,
    "post_code": 830017,
    "pid": 650100,
    "id": 3211,
    "area_id": 650105
  },
  {
    "area_name": "头屯河区",
    "pinyin": "Toutunhe",
    "level": 3,
    "area_code": 991,
    "post_code": 830022,
    "pid": 650100,
    "id": 3212,
    "area_id": 650106
  },
  {
    "area_name": "达坂城区",
    "pinyin": "Dabancheng",
    "level": 3,
    "area_code": 991,
    "post_code": 830039,
    "pid": 650100,
    "id": 3213,
    "area_id": 650107
  },
  {
    "area_name": "米东区",
    "pinyin": "Midong",
    "level": 3,
    "area_code": 991,
    "post_code": 830019,
    "pid": 650100,
    "id": 3214,
    "area_id": 650109
  },
  {
    "area_name": "乌鲁木齐县",
    "pinyin": "Wulumuqi",
    "level": 3,
    "area_code": 991,
    "post_code": 830063,
    "pid": 650100,
    "id": 3215,
    "area_id": 650121
  },
  {
    "area_name": "克拉玛依市",
    "pinyin": "Karamay",
    "level": 2,
    "area_code": 990,
    "post_code": 834000,
    "pid": 650000,
    "id": 3216,
    "area_id": 650200
  },
  {
    "area_name": "独山子区",
    "pinyin": "Dushanzi",
    "level": 3,
    "area_code": 992,
    "post_code": 834021,
    "pid": 650200,
    "id": 3217,
    "area_id": 650202
  },
  {
    "area_name": "克拉玛依区",
    "pinyin": "Kelamayi",
    "level": 3,
    "area_code": 990,
    "post_code": 834000,
    "pid": 650200,
    "id": 3218,
    "area_id": 650203
  },
  {
    "area_name": "白碱滩区",
    "pinyin": "Baijiantan",
    "level": 3,
    "area_code": 990,
    "post_code": 834008,
    "pid": 650200,
    "id": 3219,
    "area_id": 650204
  },
  {
    "area_name": "乌尔禾区",
    "pinyin": "Wuerhe",
    "level": 3,
    "area_code": 990,
    "post_code": 834012,
    "pid": 650200,
    "id": 3220,
    "area_id": 650205
  },
  {
    "area_name": "吐鲁番地区",
    "pinyin": "Turpan",
    "level": 2,
    "area_code": 995,
    "post_code": 838000,
    "pid": 650000,
    "id": 3221,
    "area_id": 652100
  },
  {
    "area_name": "吐鲁番市",
    "pinyin": "Tulufan",
    "level": 3,
    "area_code": 995,
    "post_code": 838000,
    "pid": 652100,
    "id": 3222,
    "area_id": 652101
  },
  {
    "area_name": "鄯善县",
    "pinyin": "Shanshan",
    "level": 3,
    "area_code": 995,
    "post_code": 838200,
    "pid": 652100,
    "id": 3223,
    "area_id": 652122
  },
  {
    "area_name": "托克逊县",
    "pinyin": "Tuokexun",
    "level": 3,
    "area_code": 995,
    "post_code": 838100,
    "pid": 652100,
    "id": 3224,
    "area_id": 652123
  },
  {
    "area_name": "哈密地区",
    "pinyin": "Hami",
    "level": 2,
    "area_code": 902,
    "post_code": 839000,
    "pid": 650000,
    "id": 3225,
    "area_id": 652200
  },
  {
    "area_name": "哈密市",
    "pinyin": "Hami",
    "level": 3,
    "area_code": 902,
    "post_code": 839000,
    "pid": 652200,
    "id": 3226,
    "area_id": 652201
  },
  {
    "area_name": "巴里坤哈萨克自治县",
    "pinyin": "Balikun",
    "level": 3,
    "area_code": 902,
    "post_code": 839200,
    "pid": 652200,
    "id": 3227,
    "area_id": 652222
  },
  {
    "area_name": "伊吾县",
    "pinyin": "Yiwu",
    "level": 3,
    "area_code": 902,
    "post_code": 839300,
    "pid": 652200,
    "id": 3228,
    "area_id": 652223
  },
  {
    "area_name": "昌吉回族自治州",
    "pinyin": "Changji",
    "level": 2,
    "area_code": 994,
    "post_code": 831100,
    "pid": 650000,
    "id": 3229,
    "area_id": 652300
  },
  {
    "area_name": "昌吉市",
    "pinyin": "Changji",
    "level": 3,
    "area_code": 994,
    "post_code": 831100,
    "pid": 652300,
    "id": 3230,
    "area_id": 652301
  },
  {
    "area_name": "阜康市",
    "pinyin": "Fukang",
    "level": 3,
    "area_code": 994,
    "post_code": 831500,
    "pid": 652300,
    "id": 3231,
    "area_id": 652302
  },
  {
    "area_name": "呼图壁县",
    "pinyin": "Hutubi",
    "level": 3,
    "area_code": 994,
    "post_code": 831200,
    "pid": 652300,
    "id": 3232,
    "area_id": 652323
  },
  {
    "area_name": "玛纳斯县",
    "pinyin": "Manasi",
    "level": 3,
    "area_code": 994,
    "post_code": 832200,
    "pid": 652300,
    "id": 3233,
    "area_id": 652324
  },
  {
    "area_name": "奇台县",
    "pinyin": "Qitai",
    "level": 3,
    "area_code": 994,
    "post_code": 831800,
    "pid": 652300,
    "id": 3234,
    "area_id": 652325
  },
  {
    "area_name": "吉木萨尔县",
    "pinyin": "Jimusaer",
    "level": 3,
    "area_code": 994,
    "post_code": 831700,
    "pid": 652300,
    "id": 3235,
    "area_id": 652327
  },
  {
    "area_name": "木垒哈萨克自治县",
    "pinyin": "Mulei",
    "level": 3,
    "area_code": 994,
    "post_code": 831900,
    "pid": 652300,
    "id": 3236,
    "area_id": 652328
  },
  {
    "area_name": "博尔塔拉蒙古自治州",
    "pinyin": "Bortala",
    "level": 2,
    "area_code": 909,
    "post_code": 833400,
    "pid": 650000,
    "id": 3237,
    "area_id": 652700
  },
  {
    "area_name": "博乐市",
    "pinyin": "Bole",
    "level": 3,
    "area_code": 909,
    "post_code": 833400,
    "pid": 652700,
    "id": 3238,
    "area_id": 652701
  },
  {
    "area_name": "阿拉山口市",
    "pinyin": "Alashankou",
    "level": 3,
    "area_code": 909,
    "post_code": 833400,
    "pid": 652700,
    "id": 3239,
    "area_id": 652702
  },
  {
    "area_name": "精河县",
    "pinyin": "Jinghe",
    "level": 3,
    "area_code": 909,
    "post_code": 833300,
    "pid": 652700,
    "id": 3240,
    "area_id": 652722
  },
  {
    "area_name": "温泉县",
    "pinyin": "Wenquan",
    "level": 3,
    "area_code": 909,
    "post_code": 833500,
    "pid": 652700,
    "id": 3241,
    "area_id": 652723
  },
  {
    "area_name": "巴音郭楞蒙古自治州",
    "pinyin": "Bayingol",
    "level": 2,
    "area_code": 996,
    "post_code": 841000,
    "pid": 650000,
    "id": 3242,
    "area_id": 652800
  },
  {
    "area_name": "库尔勒市",
    "pinyin": "Kuerle",
    "level": 3,
    "area_code": 996,
    "post_code": 841000,
    "pid": 652800,
    "id": 3243,
    "area_id": 652801
  },
  {
    "area_name": "轮台县",
    "pinyin": "Luntai",
    "level": 3,
    "area_code": 996,
    "post_code": 841600,
    "pid": 652800,
    "id": 3244,
    "area_id": 652822
  },
  {
    "area_name": "尉犁县",
    "pinyin": "Yuli",
    "level": 3,
    "area_code": 996,
    "post_code": 841500,
    "pid": 652800,
    "id": 3245,
    "area_id": 652823
  },
  {
    "area_name": "若羌县",
    "pinyin": "Ruoqiang",
    "level": 3,
    "area_code": 996,
    "post_code": 841800,
    "pid": 652800,
    "id": 3246,
    "area_id": 652824
  },
  {
    "area_name": "且末县",
    "pinyin": "Qiemo",
    "level": 3,
    "area_code": 996,
    "post_code": 841900,
    "pid": 652800,
    "id": 3247,
    "area_id": 652825
  },
  {
    "area_name": "焉耆回族自治县",
    "pinyin": "Yanqi",
    "level": 3,
    "area_code": 996,
    "post_code": 841100,
    "pid": 652800,
    "id": 3248,
    "area_id": 652826
  },
  {
    "area_name": "和静县",
    "pinyin": "Hejing",
    "level": 3,
    "area_code": 996,
    "post_code": 841300,
    "pid": 652800,
    "id": 3249,
    "area_id": 652827
  },
  {
    "area_name": "和硕县",
    "pinyin": "Heshuo",
    "level": 3,
    "area_code": 996,
    "post_code": 841200,
    "pid": 652800,
    "id": 3250,
    "area_id": 652828
  },
  {
    "area_name": "博湖县",
    "pinyin": "Bohu",
    "level": 3,
    "area_code": 996,
    "post_code": 841400,
    "pid": 652800,
    "id": 3251,
    "area_id": 652829
  },
  {
    "area_name": "阿克苏地区",
    "pinyin": "Aksu",
    "level": 2,
    "area_code": 997,
    "post_code": 843000,
    "pid": 650000,
    "id": 3252,
    "area_id": 652900
  },
  {
    "area_name": "阿克苏市",
    "pinyin": "Akesu",
    "level": 3,
    "area_code": 997,
    "post_code": 843000,
    "pid": 652900,
    "id": 3253,
    "area_id": 652901
  },
  {
    "area_name": "温宿县",
    "pinyin": "Wensu",
    "level": 3,
    "area_code": 997,
    "post_code": 843100,
    "pid": 652900,
    "id": 3254,
    "area_id": 652922
  },
  {
    "area_name": "库车县",
    "pinyin": "Kuche",
    "level": 3,
    "area_code": 997,
    "post_code": 842000,
    "pid": 652900,
    "id": 3255,
    "area_id": 652923
  },
  {
    "area_name": "沙雅县",
    "pinyin": "Shaya",
    "level": 3,
    "area_code": 997,
    "post_code": 842200,
    "pid": 652900,
    "id": 3256,
    "area_id": 652924
  },
  {
    "area_name": "新和县",
    "pinyin": "Xinhe",
    "level": 3,
    "area_code": 997,
    "post_code": 842100,
    "pid": 652900,
    "id": 3257,
    "area_id": 652925
  },
  {
    "area_name": "拜城县",
    "pinyin": "Baicheng",
    "level": 3,
    "area_code": 997,
    "post_code": 842300,
    "pid": 652900,
    "id": 3258,
    "area_id": 652926
  },
  {
    "area_name": "乌什县",
    "pinyin": "Wushi",
    "level": 3,
    "area_code": 997,
    "post_code": 843400,
    "pid": 652900,
    "id": 3259,
    "area_id": 652927
  },
  {
    "area_name": "阿瓦提县",
    "pinyin": "Awati",
    "level": 3,
    "area_code": 997,
    "post_code": 843200,
    "pid": 652900,
    "id": 3260,
    "area_id": 652928
  },
  {
    "area_name": "柯坪县",
    "pinyin": "Keping",
    "level": 3,
    "area_code": 997,
    "post_code": 843600,
    "pid": 652900,
    "id": 3261,
    "area_id": 652929
  },
  {
    "area_name": "克孜勒苏柯尔克孜自治州",
    "pinyin": "Kizilsu",
    "level": 2,
    "area_code": 908,
    "post_code": 845350,
    "pid": 650000,
    "id": 3262,
    "area_id": 653000
  },
  {
    "area_name": "阿图什市",
    "pinyin": "Atushi",
    "level": 3,
    "area_code": 908,
    "post_code": 845350,
    "pid": 653000,
    "id": 3263,
    "area_id": 653001
  },
  {
    "area_name": "阿克陶县",
    "pinyin": "Aketao",
    "level": 3,
    "area_code": 908,
    "post_code": 845550,
    "pid": 653000,
    "id": 3264,
    "area_id": 653022
  },
  {
    "area_name": "阿合奇县",
    "pinyin": "Aheqi",
    "level": 3,
    "area_code": 997,
    "post_code": 843500,
    "pid": 653000,
    "id": 3265,
    "area_id": 653023
  },
  {
    "area_name": "乌恰县",
    "pinyin": "Wuqia",
    "level": 3,
    "area_code": 908,
    "post_code": 845450,
    "pid": 653000,
    "id": 3266,
    "area_id": 653024
  },
  {
    "area_name": "喀什地区",
    "pinyin": "Kashgar",
    "level": 2,
    "area_code": 998,
    "post_code": 844000,
    "pid": 650000,
    "id": 3267,
    "area_id": 653100
  },
  {
    "area_name": "喀什市",
    "pinyin": "Kashi",
    "level": 3,
    "area_code": 998,
    "post_code": 844000,
    "pid": 653100,
    "id": 3268,
    "area_id": 653101
  },
  {
    "area_name": "疏附县",
    "pinyin": "Shufu",
    "level": 3,
    "area_code": 998,
    "post_code": 844100,
    "pid": 653100,
    "id": 3269,
    "area_id": 653121
  },
  {
    "area_name": "疏勒县",
    "pinyin": "Shule",
    "level": 3,
    "area_code": 998,
    "post_code": 844200,
    "pid": 653100,
    "id": 3270,
    "area_id": 653122
  },
  {
    "area_name": "英吉沙县",
    "pinyin": "Yingjisha",
    "level": 3,
    "area_code": 998,
    "post_code": 844500,
    "pid": 653100,
    "id": 3271,
    "area_id": 653123
  },
  {
    "area_name": "泽普县",
    "pinyin": "Zepu",
    "level": 3,
    "area_code": 998,
    "post_code": 844800,
    "pid": 653100,
    "id": 3272,
    "area_id": 653124
  },
  {
    "area_name": "莎车县",
    "pinyin": "Shache",
    "level": 3,
    "area_code": 998,
    "post_code": 844700,
    "pid": 653100,
    "id": 3273,
    "area_id": 653125
  },
  {
    "area_name": "叶城县",
    "pinyin": "Yecheng",
    "level": 3,
    "area_code": 998,
    "post_code": 844900,
    "pid": 653100,
    "id": 3274,
    "area_id": 653126
  },
  {
    "area_name": "麦盖提县",
    "pinyin": "Maigaiti",
    "level": 3,
    "area_code": 998,
    "post_code": 844600,
    "pid": 653100,
    "id": 3275,
    "area_id": 653127
  },
  {
    "area_name": "岳普湖县",
    "pinyin": "Yuepuhu",
    "level": 3,
    "area_code": 998,
    "post_code": 844400,
    "pid": 653100,
    "id": 3276,
    "area_id": 653128
  },
  {
    "area_name": "伽师县",
    "pinyin": "Jiashi",
    "level": 3,
    "area_code": 998,
    "post_code": 844300,
    "pid": 653100,
    "id": 3277,
    "area_id": 653129
  },
  {
    "area_name": "巴楚县",
    "pinyin": "Bachu",
    "level": 3,
    "area_code": 998,
    "post_code": 843800,
    "pid": 653100,
    "id": 3278,
    "area_id": 653130
  },
  {
    "area_name": "塔什库尔干塔吉克自治县",
    "pinyin": "Tashikuergantajike",
    "level": 3,
    "area_code": 998,
    "post_code": 845250,
    "pid": 653100,
    "id": 3279,
    "area_id": 653131
  },
  {
    "area_name": "和田地区",
    "pinyin": "Hotan",
    "level": 2,
    "area_code": 903,
    "post_code": 848000,
    "pid": 650000,
    "id": 3280,
    "area_id": 653200
  },
  {
    "area_name": "和田市",
    "pinyin": "Hetianshi",
    "level": 3,
    "area_code": 903,
    "post_code": 848000,
    "pid": 653200,
    "id": 3281,
    "area_id": 653201
  },
  {
    "area_name": "和田县",
    "pinyin": "Hetianxian",
    "level": 3,
    "area_code": 903,
    "post_code": 848000,
    "pid": 653200,
    "id": 3282,
    "area_id": 653221
  },
  {
    "area_name": "墨玉县",
    "pinyin": "Moyu",
    "level": 3,
    "area_code": 903,
    "post_code": 848100,
    "pid": 653200,
    "id": 3283,
    "area_id": 653222
  },
  {
    "area_name": "皮山县",
    "pinyin": "Pishan",
    "level": 3,
    "area_code": 903,
    "post_code": 845150,
    "pid": 653200,
    "id": 3284,
    "area_id": 653223
  },
  {
    "area_name": "洛浦县",
    "pinyin": "Luopu",
    "level": 3,
    "area_code": 903,
    "post_code": 848200,
    "pid": 653200,
    "id": 3285,
    "area_id": 653224
  },
  {
    "area_name": "策勒县",
    "pinyin": "Cele",
    "level": 3,
    "area_code": 903,
    "post_code": 848300,
    "pid": 653200,
    "id": 3286,
    "area_id": 653225
  },
  {
    "area_name": "于田县",
    "pinyin": "Yutian",
    "level": 3,
    "area_code": 903,
    "post_code": 848400,
    "pid": 653200,
    "id": 3287,
    "area_id": 653226
  },
  {
    "area_name": "民丰县",
    "pinyin": "Minfeng",
    "level": 3,
    "area_code": 903,
    "post_code": 848500,
    "pid": 653200,
    "id": 3288,
    "area_id": 653227
  },
  {
    "area_name": "伊犁哈萨克自治州",
    "pinyin": "Ili",
    "level": 2,
    "area_code": 999,
    "post_code": 835100,
    "pid": 650000,
    "id": 3289,
    "area_id": 654000
  },
  {
    "area_name": "伊宁市",
    "pinyin": "Yining",
    "level": 3,
    "area_code": 999,
    "post_code": 835000,
    "pid": 654000,
    "id": 3290,
    "area_id": 654002
  },
  {
    "area_name": "奎屯市",
    "pinyin": "Kuitun",
    "level": 3,
    "area_code": 992,
    "post_code": 833200,
    "pid": 654000,
    "id": 3291,
    "area_id": 654003
  },
  {
    "area_name": "霍尔果斯市",
    "pinyin": "Huoerguosi",
    "level": 3,
    "area_code": 999,
    "post_code": 835221,
    "pid": 654000,
    "id": 3292,
    "area_id": 654004
  },
  {
    "area_name": "伊宁县",
    "pinyin": "Yining",
    "level": 3,
    "area_code": 999,
    "post_code": 835100,
    "pid": 654000,
    "id": 3293,
    "area_id": 654021
  },
  {
    "area_name": "察布查尔锡伯自治县",
    "pinyin": "Chabuchaerxibo",
    "level": 3,
    "area_code": 999,
    "post_code": 835300,
    "pid": 654000,
    "id": 3294,
    "area_id": 654022
  },
  {
    "area_name": "霍城县",
    "pinyin": "Huocheng",
    "level": 3,
    "area_code": 999,
    "post_code": 835200,
    "pid": 654000,
    "id": 3295,
    "area_id": 654023
  },
  {
    "area_name": "巩留县",
    "pinyin": "Gongliu",
    "level": 3,
    "area_code": 999,
    "post_code": 835400,
    "pid": 654000,
    "id": 3296,
    "area_id": 654024
  },
  {
    "area_name": "新源县",
    "pinyin": "Xinyuan",
    "level": 3,
    "area_code": 999,
    "post_code": 835800,
    "pid": 654000,
    "id": 3297,
    "area_id": 654025
  },
  {
    "area_name": "昭苏县",
    "pinyin": "Zhaosu",
    "level": 3,
    "area_code": 999,
    "post_code": 835600,
    "pid": 654000,
    "id": 3298,
    "area_id": 654026
  },
  {
    "area_name": "特克斯县",
    "pinyin": "Tekesi",
    "level": 3,
    "area_code": 999,
    "post_code": 835500,
    "pid": 654000,
    "id": 3299,
    "area_id": 654027
  },
  {
    "area_name": "尼勒克县",
    "pinyin": "Nileke",
    "level": 3,
    "area_code": 999,
    "post_code": 835700,
    "pid": 654000,
    "id": 3300,
    "area_id": 654028
  },
  {
    "area_name": "塔城地区",
    "pinyin": "Qoqek",
    "level": 2,
    "area_code": 901,
    "post_code": 834700,
    "pid": 650000,
    "id": 3301,
    "area_id": 654200
  },
  {
    "area_name": "塔城市",
    "pinyin": "Tacheng",
    "level": 3,
    "area_code": 901,
    "post_code": 834700,
    "pid": 654200,
    "id": 3302,
    "area_id": 654201
  },
  {
    "area_name": "乌苏市",
    "pinyin": "Wusu",
    "level": 3,
    "area_code": 992,
    "post_code": 833000,
    "pid": 654200,
    "id": 3303,
    "area_id": 654202
  },
  {
    "area_name": "额敏县",
    "pinyin": "Emin",
    "level": 3,
    "area_code": 901,
    "post_code": 834600,
    "pid": 654200,
    "id": 3304,
    "area_id": 654221
  },
  {
    "area_name": "沙湾县",
    "pinyin": "Shawan",
    "level": 3,
    "area_code": 993,
    "post_code": 832100,
    "pid": 654200,
    "id": 3305,
    "area_id": 654223
  },
  {
    "area_name": "托里县",
    "pinyin": "Tuoli",
    "level": 3,
    "area_code": 901,
    "post_code": 834500,
    "pid": 654200,
    "id": 3306,
    "area_id": 654224
  },
  {
    "area_name": "裕民县",
    "pinyin": "Yumin",
    "level": 3,
    "area_code": 901,
    "post_code": 834800,
    "pid": 654200,
    "id": 3307,
    "area_id": 654225
  },
  {
    "area_name": "和布克赛尔蒙古自治县",
    "pinyin": "Hebukesaier",
    "level": 3,
    "area_code": 990,
    "post_code": 834400,
    "pid": 654200,
    "id": 3308,
    "area_id": 654226
  },
  {
    "area_name": "阿勒泰地区",
    "pinyin": "Altay",
    "level": 2,
    "area_code": 906,
    "post_code": 836500,
    "pid": 650000,
    "id": 3309,
    "area_id": 654300
  },
  {
    "area_name": "阿勒泰市",
    "pinyin": "Aletai",
    "level": 3,
    "area_code": 906,
    "post_code": 836500,
    "pid": 654300,
    "id": 3310,
    "area_id": 654301
  },
  {
    "area_name": "布尔津县",
    "pinyin": "Buerjin",
    "level": 3,
    "area_code": 906,
    "post_code": 836600,
    "pid": 654300,
    "id": 3311,
    "area_id": 654321
  },
  {
    "area_name": "富蕴县",
    "pinyin": "Fuyun",
    "level": 3,
    "area_code": 906,
    "post_code": 836100,
    "pid": 654300,
    "id": 3312,
    "area_id": 654322
  },
  {
    "area_name": "福海县",
    "pinyin": "Fuhai",
    "level": 3,
    "area_code": 906,
    "post_code": 836400,
    "pid": 654300,
    "id": 3313,
    "area_id": 654323
  },
  {
    "area_name": "哈巴河县",
    "pinyin": "Habahe",
    "level": 3,
    "area_code": 906,
    "post_code": 836700,
    "pid": 654300,
    "id": 3314,
    "area_id": 654324
  },
  {
    "area_name": "青河县",
    "pinyin": "Qinghe",
    "level": 3,
    "area_code": 906,
    "post_code": 836200,
    "pid": 654300,
    "id": 3315,
    "area_id": 654325
  },
  {
    "area_name": "吉木乃县",
    "pinyin": "Jimunai",
    "level": 3,
    "area_code": 906,
    "post_code": 836800,
    "pid": 654300,
    "id": 3316,
    "area_id": 654326
  },
  {
    "area_name": "直辖县级",
    "pinyin": "",
    "level": 2,
    "area_code": null,
    "post_code": null,
    "pid": 650000,
    "id": 3317,
    "area_id": 659000
  },
  {
    "area_name": "石河子市",
    "pinyin": "Shihezi",
    "level": 3,
    "area_code": 993,
    "post_code": 832000,
    "pid": 659000,
    "id": 3318,
    "area_id": 659001
  },
  {
    "area_name": "阿拉尔市",
    "pinyin": "Aral",
    "level": 3,
    "area_code": 997,
    "post_code": 843300,
    "pid": 659000,
    "id": 3319,
    "area_id": 659002
  },
  {
    "area_name": "图木舒克市",
    "pinyin": "Tumxuk",
    "level": 3,
    "area_code": 998,
    "post_code": 843806,
    "pid": 659000,
    "id": 3320,
    "area_id": 659003
  },
  {
    "area_name": "五家渠市",
    "pinyin": "Wujiaqu",
    "level": 3,
    "area_code": 994,
    "post_code": 831300,
    "pid": 659000,
    "id": 3321,
    "area_id": 659004
  },
  {
    "area_name": "北屯市",
    "pinyin": "Beitun",
    "level": 3,
    "area_code": 906,
    "post_code": 836000,
    "pid": 659000,
    "id": 3322,
    "area_id": 659005
  },
  {
    "area_name": "铁门关市",
    "pinyin": "Tiemenguan",
    "level": 3,
    "area_code": 906,
    "post_code": 836000,
    "pid": 659000,
    "id": 3323,
    "area_id": 659006
  },
  {
    "area_name": "双河市",
    "pinyin": "Shuanghe",
    "level": 3,
    "area_code": 909,
    "post_code": 833408,
    "pid": 659000,
    "id": 3324,
    "area_id": 659007
  },
  {
    "area_name": "台湾",
    "pinyin": "Taiwan",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3325,
    "area_id": 710000
  },
  {
    "area_name": "台北市",
    "pinyin": "Taipei",
    "level": 2,
    "area_code": 2,
    "post_code": 1,
    "pid": 710000,
    "id": 3326,
    "area_id": 710100
  },
  {
    "area_name": "松山区",
    "pinyin": "Songshan",
    "level": 3,
    "area_code": 2,
    "post_code": 105,
    "pid": 710100,
    "id": 3327,
    "area_id": 710101
  },
  {
    "area_name": "信义区",
    "pinyin": "Xinyi",
    "level": 3,
    "area_code": 2,
    "post_code": 110,
    "pid": 710100,
    "id": 3328,
    "area_id": 710102
  },
  {
    "area_name": "大安区",
    "pinyin": "Da'an",
    "level": 3,
    "area_code": 2,
    "post_code": 106,
    "pid": 710100,
    "id": 3329,
    "area_id": 710103
  },
  {
    "area_name": "中山区",
    "pinyin": "Zhongshan",
    "level": 3,
    "area_code": 2,
    "post_code": 104,
    "pid": 710100,
    "id": 3330,
    "area_id": 710104
  },
  {
    "area_name": "中正区",
    "pinyin": "Zhongzheng",
    "level": 3,
    "area_code": 2,
    "post_code": 100,
    "pid": 710100,
    "id": 3331,
    "area_id": 710105
  },
  {
    "area_name": "大同区",
    "pinyin": "Datong",
    "level": 3,
    "area_code": 2,
    "post_code": 103,
    "pid": 710100,
    "id": 3332,
    "area_id": 710106
  },
  {
    "area_name": "万华区",
    "pinyin": "Wanhua",
    "level": 3,
    "area_code": 2,
    "post_code": 108,
    "pid": 710100,
    "id": 3333,
    "area_id": 710107
  },
  {
    "area_name": "文山区",
    "pinyin": "Wenshan",
    "level": 3,
    "area_code": 2,
    "post_code": 116,
    "pid": 710100,
    "id": 3334,
    "area_id": 710108
  },
  {
    "area_name": "南港区",
    "pinyin": "Nangang",
    "level": 3,
    "area_code": 2,
    "post_code": 115,
    "pid": 710100,
    "id": 3335,
    "area_id": 710109
  },
  {
    "area_name": "内湖区",
    "pinyin": "Nahu",
    "level": 3,
    "area_code": 2,
    "post_code": 114,
    "pid": 710100,
    "id": 3336,
    "area_id": 710110
  },
  {
    "area_name": "士林区",
    "pinyin": "Shilin",
    "level": 3,
    "area_code": 2,
    "post_code": 111,
    "pid": 710100,
    "id": 3337,
    "area_id": 710111
  },
  {
    "area_name": "北投区",
    "pinyin": "Beitou",
    "level": 3,
    "area_code": 2,
    "post_code": 112,
    "pid": 710100,
    "id": 3338,
    "area_id": 710112
  },
  {
    "area_name": "高雄市",
    "pinyin": "Kaohsiung",
    "level": 2,
    "area_code": 7,
    "post_code": 8,
    "pid": 710000,
    "id": 3339,
    "area_id": 710200
  },
  {
    "area_name": "盐埕区",
    "pinyin": "Yancheng",
    "level": 3,
    "area_code": 7,
    "post_code": 803,
    "pid": 710200,
    "id": 3340,
    "area_id": 710201
  },
  {
    "area_name": "鼓山区",
    "pinyin": "Gushan",
    "level": 3,
    "area_code": 7,
    "post_code": 804,
    "pid": 710200,
    "id": 3341,
    "area_id": 710202
  },
  {
    "area_name": "左营区",
    "pinyin": "Zuoying",
    "level": 3,
    "area_code": 7,
    "post_code": 813,
    "pid": 710200,
    "id": 3342,
    "area_id": 710203
  },
  {
    "area_name": "楠梓区",
    "pinyin": "Nanzi",
    "level": 3,
    "area_code": 7,
    "post_code": 811,
    "pid": 710200,
    "id": 3343,
    "area_id": 710204
  },
  {
    "area_name": "三民区",
    "pinyin": "Sanmin",
    "level": 3,
    "area_code": 7,
    "post_code": 807,
    "pid": 710200,
    "id": 3344,
    "area_id": 710205
  },
  {
    "area_name": "新兴区",
    "pinyin": "Xinxing",
    "level": 3,
    "area_code": 7,
    "post_code": 800,
    "pid": 710200,
    "id": 3345,
    "area_id": 710206
  },
  {
    "area_name": "前金区",
    "pinyin": "Qianjin",
    "level": 3,
    "area_code": 7,
    "post_code": 801,
    "pid": 710200,
    "id": 3346,
    "area_id": 710207
  },
  {
    "area_name": "苓雅区",
    "pinyin": "Lingya",
    "level": 3,
    "area_code": 7,
    "post_code": 802,
    "pid": 710200,
    "id": 3347,
    "area_id": 710208
  },
  {
    "area_name": "前镇区",
    "pinyin": "Qianzhen",
    "level": 3,
    "area_code": 7,
    "post_code": 806,
    "pid": 710200,
    "id": 3348,
    "area_id": 710209
  },
  {
    "area_name": "旗津区",
    "pinyin": "Qijin",
    "level": 3,
    "area_code": 7,
    "post_code": 805,
    "pid": 710200,
    "id": 3349,
    "area_id": 710210
  },
  {
    "area_name": "小港区",
    "pinyin": "Xiaogang",
    "level": 3,
    "area_code": 7,
    "post_code": 812,
    "pid": 710200,
    "id": 3350,
    "area_id": 710211
  },
  {
    "area_name": "凤山区",
    "pinyin": "Fengshan",
    "level": 3,
    "area_code": 7,
    "post_code": 830,
    "pid": 710200,
    "id": 3351,
    "area_id": 710212
  },
  {
    "area_name": "林园区",
    "pinyin": "Linyuan",
    "level": 3,
    "area_code": 7,
    "post_code": 832,
    "pid": 710200,
    "id": 3352,
    "area_id": 710213
  },
  {
    "area_name": "大寮区",
    "pinyin": "Daliao",
    "level": 3,
    "area_code": 7,
    "post_code": 831,
    "pid": 710200,
    "id": 3353,
    "area_id": 710214
  },
  {
    "area_name": "大树区",
    "pinyin": "Dashu",
    "level": 3,
    "area_code": 7,
    "post_code": 840,
    "pid": 710200,
    "id": 3354,
    "area_id": 710215
  },
  {
    "area_name": "大社区",
    "pinyin": "Dashe",
    "level": 3,
    "area_code": 7,
    "post_code": 815,
    "pid": 710200,
    "id": 3355,
    "area_id": 710216
  },
  {
    "area_name": "仁武区",
    "pinyin": "Renwu",
    "level": 3,
    "area_code": 7,
    "post_code": 814,
    "pid": 710200,
    "id": 3356,
    "area_id": 710217
  },
  {
    "area_name": "鸟松区",
    "pinyin": "Niaosong",
    "level": 3,
    "area_code": 7,
    "post_code": 833,
    "pid": 710200,
    "id": 3357,
    "area_id": 710218
  },
  {
    "area_name": "冈山区",
    "pinyin": "Gangshan",
    "level": 3,
    "area_code": 7,
    "post_code": 820,
    "pid": 710200,
    "id": 3358,
    "area_id": 710219
  },
  {
    "area_name": "桥头区",
    "pinyin": "Qiaotou",
    "level": 3,
    "area_code": 7,
    "post_code": 825,
    "pid": 710200,
    "id": 3359,
    "area_id": 710220
  },
  {
    "area_name": "燕巢区",
    "pinyin": "Yanchao",
    "level": 3,
    "area_code": 7,
    "post_code": 824,
    "pid": 710200,
    "id": 3360,
    "area_id": 710221
  },
  {
    "area_name": "田寮区",
    "pinyin": "Tianliao",
    "level": 3,
    "area_code": 7,
    "post_code": 823,
    "pid": 710200,
    "id": 3361,
    "area_id": 710222
  },
  {
    "area_name": "阿莲区",
    "pinyin": "Alian",
    "level": 3,
    "area_code": 7,
    "post_code": 822,
    "pid": 710200,
    "id": 3362,
    "area_id": 710223
  },
  {
    "area_name": "路竹区",
    "pinyin": "Luzhu",
    "level": 3,
    "area_code": 7,
    "post_code": 821,
    "pid": 710200,
    "id": 3363,
    "area_id": 710224
  },
  {
    "area_name": "湖内区",
    "pinyin": "Huna",
    "level": 3,
    "area_code": 7,
    "post_code": 829,
    "pid": 710200,
    "id": 3364,
    "area_id": 710225
  },
  {
    "area_name": "茄萣区",
    "pinyin": "Qieding",
    "level": 3,
    "area_code": 7,
    "post_code": 852,
    "pid": 710200,
    "id": 3365,
    "area_id": 710226
  },
  {
    "area_name": "永安区",
    "pinyin": "Yong'an",
    "level": 3,
    "area_code": 7,
    "post_code": 828,
    "pid": 710200,
    "id": 3366,
    "area_id": 710227
  },
  {
    "area_name": "弥陀区",
    "pinyin": "Mituo",
    "level": 3,
    "area_code": 7,
    "post_code": 827,
    "pid": 710200,
    "id": 3367,
    "area_id": 710228
  },
  {
    "area_name": "梓官区",
    "pinyin": "Ziguan",
    "level": 3,
    "area_code": 7,
    "post_code": 826,
    "pid": 710200,
    "id": 3368,
    "area_id": 710229
  },
  {
    "area_name": "旗山区",
    "pinyin": "Qishan",
    "level": 3,
    "area_code": 7,
    "post_code": 842,
    "pid": 710200,
    "id": 3369,
    "area_id": 710230
  },
  {
    "area_name": "美浓区",
    "pinyin": "Meinong",
    "level": 3,
    "area_code": 7,
    "post_code": 843,
    "pid": 710200,
    "id": 3370,
    "area_id": 710231
  },
  {
    "area_name": "六龟区",
    "pinyin": "Liugui",
    "level": 3,
    "area_code": 7,
    "post_code": 844,
    "pid": 710200,
    "id": 3371,
    "area_id": 710232
  },
  {
    "area_name": "甲仙区",
    "pinyin": "Jiaxian",
    "level": 3,
    "area_code": 7,
    "post_code": 847,
    "pid": 710200,
    "id": 3372,
    "area_id": 710233
  },
  {
    "area_name": "杉林区",
    "pinyin": "Shanlin",
    "level": 3,
    "area_code": 7,
    "post_code": 846,
    "pid": 710200,
    "id": 3373,
    "area_id": 710234
  },
  {
    "area_name": "内门区",
    "pinyin": "Namen",
    "level": 3,
    "area_code": 7,
    "post_code": 845,
    "pid": 710200,
    "id": 3374,
    "area_id": 710235
  },
  {
    "area_name": "茂林区",
    "pinyin": "Maolin",
    "level": 3,
    "area_code": 7,
    "post_code": 851,
    "pid": 710200,
    "id": 3375,
    "area_id": 710236
  },
  {
    "area_name": "桃源区",
    "pinyin": "Taoyuan",
    "level": 3,
    "area_code": 7,
    "post_code": 848,
    "pid": 710200,
    "id": 3376,
    "area_id": 710237
  },
  {
    "area_name": "那玛夏区",
    "pinyin": "Namaxia",
    "level": 3,
    "area_code": 7,
    "post_code": 849,
    "pid": 710200,
    "id": 3377,
    "area_id": 710238
  },
  {
    "area_name": "基隆市",
    "pinyin": "Keelung",
    "level": 2,
    "area_code": 2,
    "post_code": 2,
    "pid": 710000,
    "id": 3378,
    "area_id": 710300
  },
  {
    "area_name": "中正区",
    "pinyin": "Zhongzheng",
    "level": 3,
    "area_code": 2,
    "post_code": 202,
    "pid": 710300,
    "id": 3379,
    "area_id": 710301
  },
  {
    "area_name": "七堵区",
    "pinyin": "Qidu",
    "level": 3,
    "area_code": 2,
    "post_code": 206,
    "pid": 710300,
    "id": 3380,
    "area_id": 710302
  },
  {
    "area_name": "暖暖区",
    "pinyin": "Nuannuan",
    "level": 3,
    "area_code": 2,
    "post_code": 205,
    "pid": 710300,
    "id": 3381,
    "area_id": 710303
  },
  {
    "area_name": "仁爱区",
    "pinyin": "Renai",
    "level": 3,
    "area_code": 2,
    "post_code": 200,
    "pid": 710300,
    "id": 3382,
    "area_id": 710304
  },
  {
    "area_name": "中山区",
    "pinyin": "Zhongshan",
    "level": 3,
    "area_code": 2,
    "post_code": 203,
    "pid": 710300,
    "id": 3383,
    "area_id": 710305
  },
  {
    "area_name": "安乐区",
    "pinyin": "Anle",
    "level": 3,
    "area_code": 2,
    "post_code": 204,
    "pid": 710300,
    "id": 3384,
    "area_id": 710306
  },
  {
    "area_name": "信义区",
    "pinyin": "Xinyi",
    "level": 3,
    "area_code": 2,
    "post_code": 201,
    "pid": 710300,
    "id": 3385,
    "area_id": 710307
  },
  {
    "area_name": "台中市",
    "pinyin": "Taichung",
    "level": 2,
    "area_code": 4,
    "post_code": 4,
    "pid": 710000,
    "id": 3386,
    "area_id": 710400
  },
  {
    "area_name": "中区",
    "pinyin": "Zhongqu",
    "level": 3,
    "area_code": 4,
    "post_code": 400,
    "pid": 710400,
    "id": 3387,
    "area_id": 710401
  },
  {
    "area_name": "东区",
    "pinyin": "Dongqu",
    "level": 3,
    "area_code": 4,
    "post_code": 401,
    "pid": 710400,
    "id": 3388,
    "area_id": 710402
  },
  {
    "area_name": "南区",
    "pinyin": "Nanqu",
    "level": 3,
    "area_code": 4,
    "post_code": 402,
    "pid": 710400,
    "id": 3389,
    "area_id": 710403
  },
  {
    "area_name": "西区",
    "pinyin": "Xiqu",
    "level": 3,
    "area_code": 4,
    "post_code": 403,
    "pid": 710400,
    "id": 3390,
    "area_id": 710404
  },
  {
    "area_name": "北区",
    "pinyin": "Beiqu",
    "level": 3,
    "area_code": 4,
    "post_code": 404,
    "pid": 710400,
    "id": 3391,
    "area_id": 710405
  },
  {
    "area_name": "西屯区",
    "pinyin": "Xitun",
    "level": 3,
    "area_code": 4,
    "post_code": 407,
    "pid": 710400,
    "id": 3392,
    "area_id": 710406
  },
  {
    "area_name": "南屯区",
    "pinyin": "Nantun",
    "level": 3,
    "area_code": 4,
    "post_code": 408,
    "pid": 710400,
    "id": 3393,
    "area_id": 710407
  },
  {
    "area_name": "北屯区",
    "pinyin": "Beitun",
    "level": 3,
    "area_code": 4,
    "post_code": 406,
    "pid": 710400,
    "id": 3394,
    "area_id": 710408
  },
  {
    "area_name": "丰原区",
    "pinyin": "Fengyuan",
    "level": 3,
    "area_code": 4,
    "post_code": 420,
    "pid": 710400,
    "id": 3395,
    "area_id": 710409
  },
  {
    "area_name": "东势区",
    "pinyin": "Dongshi",
    "level": 3,
    "area_code": 4,
    "post_code": 423,
    "pid": 710400,
    "id": 3396,
    "area_id": 710410
  },
  {
    "area_name": "大甲区",
    "pinyin": "Dajia",
    "level": 3,
    "area_code": 4,
    "post_code": 437,
    "pid": 710400,
    "id": 3397,
    "area_id": 710411
  },
  {
    "area_name": "清水区",
    "pinyin": "Qingshui",
    "level": 3,
    "area_code": 4,
    "post_code": 436,
    "pid": 710400,
    "id": 3398,
    "area_id": 710412
  },
  {
    "area_name": "沙鹿区",
    "pinyin": "Shalu",
    "level": 3,
    "area_code": 4,
    "post_code": 433,
    "pid": 710400,
    "id": 3399,
    "area_id": 710413
  },
  {
    "area_name": "梧栖区",
    "pinyin": "Wuqi",
    "level": 3,
    "area_code": 4,
    "post_code": 435,
    "pid": 710400,
    "id": 3400,
    "area_id": 710414
  },
  {
    "area_name": "后里区",
    "pinyin": "Houli",
    "level": 3,
    "area_code": 4,
    "post_code": 421,
    "pid": 710400,
    "id": 3401,
    "area_id": 710415
  },
  {
    "area_name": "神冈区",
    "pinyin": "Shengang",
    "level": 3,
    "area_code": 4,
    "post_code": 429,
    "pid": 710400,
    "id": 3402,
    "area_id": 710416
  },
  {
    "area_name": "潭子区",
    "pinyin": "Tanzi",
    "level": 3,
    "area_code": 4,
    "post_code": 427,
    "pid": 710400,
    "id": 3403,
    "area_id": 710417
  },
  {
    "area_name": "大雅区",
    "pinyin": "Daya",
    "level": 3,
    "area_code": 4,
    "post_code": 428,
    "pid": 710400,
    "id": 3404,
    "area_id": 710418
  },
  {
    "area_name": "新社区",
    "pinyin": "Xinshe",
    "level": 3,
    "area_code": 4,
    "post_code": 426,
    "pid": 710400,
    "id": 3405,
    "area_id": 710419
  },
  {
    "area_name": "石冈区",
    "pinyin": "Shigang",
    "level": 3,
    "area_code": 4,
    "post_code": 422,
    "pid": 710400,
    "id": 3406,
    "area_id": 710420
  },
  {
    "area_name": "外埔区",
    "pinyin": "Waipu",
    "level": 3,
    "area_code": 4,
    "post_code": 438,
    "pid": 710400,
    "id": 3407,
    "area_id": 710421
  },
  {
    "area_name": "大安区",
    "pinyin": "Da'an",
    "level": 3,
    "area_code": 4,
    "post_code": 439,
    "pid": 710400,
    "id": 3408,
    "area_id": 710422
  },
  {
    "area_name": "乌日区",
    "pinyin": "Wuri",
    "level": 3,
    "area_code": 4,
    "post_code": 414,
    "pid": 710400,
    "id": 3409,
    "area_id": 710423
  },
  {
    "area_name": "大肚区",
    "pinyin": "Dadu",
    "level": 3,
    "area_code": 4,
    "post_code": 432,
    "pid": 710400,
    "id": 3410,
    "area_id": 710424
  },
  {
    "area_name": "龙井区",
    "pinyin": "Longjing",
    "level": 3,
    "area_code": 4,
    "post_code": 434,
    "pid": 710400,
    "id": 3411,
    "area_id": 710425
  },
  {
    "area_name": "雾峰区",
    "pinyin": "Wufeng",
    "level": 3,
    "area_code": 4,
    "post_code": 413,
    "pid": 710400,
    "id": 3412,
    "area_id": 710426
  },
  {
    "area_name": "太平区",
    "pinyin": "Taiping",
    "level": 3,
    "area_code": 4,
    "post_code": 411,
    "pid": 710400,
    "id": 3413,
    "area_id": 710427
  },
  {
    "area_name": "大里区",
    "pinyin": "Dali",
    "level": 3,
    "area_code": 4,
    "post_code": 412,
    "pid": 710400,
    "id": 3414,
    "area_id": 710428
  },
  {
    "area_name": "和平区",
    "pinyin": "Heping",
    "level": 3,
    "area_code": 4,
    "post_code": 424,
    "pid": 710400,
    "id": 3415,
    "area_id": 710429
  },
  {
    "area_name": "台南市",
    "pinyin": "Tainan",
    "level": 2,
    "area_code": 6,
    "post_code": 7,
    "pid": 710000,
    "id": 3416,
    "area_id": 710500
  },
  {
    "area_name": "东区",
    "pinyin": "Dongqu",
    "level": 3,
    "area_code": 6,
    "post_code": 701,
    "pid": 710500,
    "id": 3417,
    "area_id": 710501
  },
  {
    "area_name": "南区",
    "pinyin": "Nanqu",
    "level": 3,
    "area_code": 6,
    "post_code": 702,
    "pid": 710500,
    "id": 3418,
    "area_id": 710502
  },
  {
    "area_name": "北区",
    "pinyin": "Beiqu",
    "level": 3,
    "area_code": 6,
    "post_code": 704,
    "pid": 710500,
    "id": 3419,
    "area_id": 710504
  },
  {
    "area_name": "安南区",
    "pinyin": "Annan",
    "level": 3,
    "area_code": 6,
    "post_code": 709,
    "pid": 710500,
    "id": 3420,
    "area_id": 710506
  },
  {
    "area_name": "安平区",
    "pinyin": "Anping",
    "level": 3,
    "area_code": 6,
    "post_code": 708,
    "pid": 710500,
    "id": 3421,
    "area_id": 710507
  },
  {
    "area_name": "中西区",
    "pinyin": "Zhongxi",
    "level": 3,
    "area_code": 6,
    "post_code": 700,
    "pid": 710500,
    "id": 3422,
    "area_id": 710508
  },
  {
    "area_name": "新营区",
    "pinyin": "Xinying",
    "level": 3,
    "area_code": 6,
    "post_code": 730,
    "pid": 710500,
    "id": 3423,
    "area_id": 710509
  },
  {
    "area_name": "盐水区",
    "pinyin": "Yanshui",
    "level": 3,
    "area_code": 6,
    "post_code": 737,
    "pid": 710500,
    "id": 3424,
    "area_id": 710510
  },
  {
    "area_name": "白河区",
    "pinyin": "Baihe",
    "level": 3,
    "area_code": 6,
    "post_code": 732,
    "pid": 710500,
    "id": 3425,
    "area_id": 710511
  },
  {
    "area_name": "柳营区",
    "pinyin": "Liuying",
    "level": 3,
    "area_code": 6,
    "post_code": 736,
    "pid": 710500,
    "id": 3426,
    "area_id": 710512
  },
  {
    "area_name": "后壁区",
    "pinyin": "Houbi",
    "level": 3,
    "area_code": 6,
    "post_code": 731,
    "pid": 710500,
    "id": 3427,
    "area_id": 710513
  },
  {
    "area_name": "东山区",
    "pinyin": "Dongshan",
    "level": 3,
    "area_code": 6,
    "post_code": 733,
    "pid": 710500,
    "id": 3428,
    "area_id": 710514
  },
  {
    "area_name": "麻豆区",
    "pinyin": "Madou",
    "level": 3,
    "area_code": 6,
    "post_code": 721,
    "pid": 710500,
    "id": 3429,
    "area_id": 710515
  },
  {
    "area_name": "下营区",
    "pinyin": "Xiaying",
    "level": 3,
    "area_code": 6,
    "post_code": 735,
    "pid": 710500,
    "id": 3430,
    "area_id": 710516
  },
  {
    "area_name": "六甲区",
    "pinyin": "Liujia",
    "level": 3,
    "area_code": 6,
    "post_code": 734,
    "pid": 710500,
    "id": 3431,
    "area_id": 710517
  },
  {
    "area_name": "官田区",
    "pinyin": "Guantian",
    "level": 3,
    "area_code": 6,
    "post_code": 720,
    "pid": 710500,
    "id": 3432,
    "area_id": 710518
  },
  {
    "area_name": "大内区",
    "pinyin": "Dana",
    "level": 3,
    "area_code": 6,
    "post_code": 742,
    "pid": 710500,
    "id": 3433,
    "area_id": 710519
  },
  {
    "area_name": "佳里区",
    "pinyin": "Jiali",
    "level": 3,
    "area_code": 6,
    "post_code": 722,
    "pid": 710500,
    "id": 3434,
    "area_id": 710520
  },
  {
    "area_name": "学甲区",
    "pinyin": "Xuejia",
    "level": 3,
    "area_code": 6,
    "post_code": 726,
    "pid": 710500,
    "id": 3435,
    "area_id": 710521
  },
  {
    "area_name": "西港区",
    "pinyin": "Xigang",
    "level": 3,
    "area_code": 6,
    "post_code": 723,
    "pid": 710500,
    "id": 3436,
    "area_id": 710522
  },
  {
    "area_name": "七股区",
    "pinyin": "Qigu",
    "level": 3,
    "area_code": 6,
    "post_code": 724,
    "pid": 710500,
    "id": 3437,
    "area_id": 710523
  },
  {
    "area_name": "将军区",
    "pinyin": "Jiangjun",
    "level": 3,
    "area_code": 6,
    "post_code": 725,
    "pid": 710500,
    "id": 3438,
    "area_id": 710524
  },
  {
    "area_name": "北门区",
    "pinyin": "Beimen",
    "level": 3,
    "area_code": 6,
    "post_code": 727,
    "pid": 710500,
    "id": 3439,
    "area_id": 710525
  },
  {
    "area_name": "新化区",
    "pinyin": "Xinhua",
    "level": 3,
    "area_code": 6,
    "post_code": 712,
    "pid": 710500,
    "id": 3440,
    "area_id": 710526
  },
  {
    "area_name": "善化区",
    "pinyin": "Shanhua",
    "level": 3,
    "area_code": 6,
    "post_code": 741,
    "pid": 710500,
    "id": 3441,
    "area_id": 710527
  },
  {
    "area_name": "新市区",
    "pinyin": "Xinshi",
    "level": 3,
    "area_code": 6,
    "post_code": 744,
    "pid": 710500,
    "id": 3442,
    "area_id": 710528
  },
  {
    "area_name": "安定区",
    "pinyin": "Anding",
    "level": 3,
    "area_code": 6,
    "post_code": 745,
    "pid": 710500,
    "id": 3443,
    "area_id": 710529
  },
  {
    "area_name": "山上区",
    "pinyin": "Shanshang",
    "level": 3,
    "area_code": 6,
    "post_code": 743,
    "pid": 710500,
    "id": 3444,
    "area_id": 710530
  },
  {
    "area_name": "玉井区",
    "pinyin": "Yujing",
    "level": 3,
    "area_code": 6,
    "post_code": 714,
    "pid": 710500,
    "id": 3445,
    "area_id": 710531
  },
  {
    "area_name": "楠西区",
    "pinyin": "Nanxi",
    "level": 3,
    "area_code": 6,
    "post_code": 715,
    "pid": 710500,
    "id": 3446,
    "area_id": 710532
  },
  {
    "area_name": "南化区",
    "pinyin": "Nanhua",
    "level": 3,
    "area_code": 6,
    "post_code": 716,
    "pid": 710500,
    "id": 3447,
    "area_id": 710533
  },
  {
    "area_name": "左镇区",
    "pinyin": "Zuozhen",
    "level": 3,
    "area_code": 6,
    "post_code": 713,
    "pid": 710500,
    "id": 3448,
    "area_id": 710534
  },
  {
    "area_name": "仁德区",
    "pinyin": "Rende",
    "level": 3,
    "area_code": 6,
    "post_code": 717,
    "pid": 710500,
    "id": 3449,
    "area_id": 710535
  },
  {
    "area_name": "归仁区",
    "pinyin": "Guiren",
    "level": 3,
    "area_code": 6,
    "post_code": 711,
    "pid": 710500,
    "id": 3450,
    "area_id": 710536
  },
  {
    "area_name": "关庙区",
    "pinyin": "Guanmiao",
    "level": 3,
    "area_code": 6,
    "post_code": 718,
    "pid": 710500,
    "id": 3451,
    "area_id": 710537
  },
  {
    "area_name": "龙崎区",
    "pinyin": "Longqi",
    "level": 3,
    "area_code": 6,
    "post_code": 719,
    "pid": 710500,
    "id": 3452,
    "area_id": 710538
  },
  {
    "area_name": "永康区",
    "pinyin": "Yongkang",
    "level": 3,
    "area_code": 6,
    "post_code": 710,
    "pid": 710500,
    "id": 3453,
    "area_id": 710539
  },
  {
    "area_name": "新竹市",
    "pinyin": "Hsinchu",
    "level": 2,
    "area_code": 3,
    "post_code": 3,
    "pid": 710000,
    "id": 3454,
    "area_id": 710600
  },
  {
    "area_name": "东区",
    "pinyin": "Dongqu",
    "level": 3,
    "area_code": 3,
    "post_code": 300,
    "pid": 710600,
    "id": 3455,
    "area_id": 710601
  },
  {
    "area_name": "北区",
    "pinyin": "Beiqu",
    "level": 3,
    "area_code": 3,
    "post_code": null,
    "pid": 710600,
    "id": 3456,
    "area_id": 710602
  },
  {
    "area_name": "香山区",
    "pinyin": "Xiangshan",
    "level": 3,
    "area_code": 3,
    "post_code": null,
    "pid": 710600,
    "id": 3457,
    "area_id": 710603
  },
  {
    "area_name": "嘉义市",
    "pinyin": "Chiayi",
    "level": 2,
    "area_code": 5,
    "post_code": 6,
    "pid": 710000,
    "id": 3458,
    "area_id": 710700
  },
  {
    "area_name": "东区",
    "pinyin": "Dongqu",
    "level": 3,
    "area_code": 5,
    "post_code": 600,
    "pid": 710700,
    "id": 3459,
    "area_id": 710701
  },
  {
    "area_name": "西区",
    "pinyin": "Xiqu",
    "level": 3,
    "area_code": 5,
    "post_code": 600,
    "pid": 710700,
    "id": 3460,
    "area_id": 710702
  },
  {
    "area_name": "新北市",
    "pinyin": "New Taipei",
    "level": 2,
    "area_code": 2,
    "post_code": 2,
    "pid": 710000,
    "id": 3461,
    "area_id": 710800
  },
  {
    "area_name": "板桥区",
    "pinyin": "Banqiao",
    "level": 3,
    "area_code": 2,
    "post_code": 220,
    "pid": 710800,
    "id": 3462,
    "area_id": 710801
  },
  {
    "area_name": "三重区",
    "pinyin": "Sanzhong",
    "level": 3,
    "area_code": 2,
    "post_code": 241,
    "pid": 710800,
    "id": 3463,
    "area_id": 710802
  },
  {
    "area_name": "中和区",
    "pinyin": "Zhonghe",
    "level": 3,
    "area_code": 2,
    "post_code": 235,
    "pid": 710800,
    "id": 3464,
    "area_id": 710803
  },
  {
    "area_name": "永和区",
    "pinyin": "Yonghe",
    "level": 3,
    "area_code": 2,
    "post_code": 234,
    "pid": 710800,
    "id": 3465,
    "area_id": 710804
  },
  {
    "area_name": "新庄区",
    "pinyin": "Xinzhuang",
    "level": 3,
    "area_code": 2,
    "post_code": 242,
    "pid": 710800,
    "id": 3466,
    "area_id": 710805
  },
  {
    "area_name": "新店区",
    "pinyin": "Xindian",
    "level": 3,
    "area_code": 2,
    "post_code": 231,
    "pid": 710800,
    "id": 3467,
    "area_id": 710806
  },
  {
    "area_name": "树林区",
    "pinyin": "Shulin",
    "level": 3,
    "area_code": 2,
    "post_code": 238,
    "pid": 710800,
    "id": 3468,
    "area_id": 710807
  },
  {
    "area_name": "莺歌区",
    "pinyin": "Yingge",
    "level": 3,
    "area_code": 2,
    "post_code": 239,
    "pid": 710800,
    "id": 3469,
    "area_id": 710808
  },
  {
    "area_name": "三峡区",
    "pinyin": "Sanxia",
    "level": 3,
    "area_code": 2,
    "post_code": 237,
    "pid": 710800,
    "id": 3470,
    "area_id": 710809
  },
  {
    "area_name": "淡水区",
    "pinyin": "Danshui",
    "level": 3,
    "area_code": 2,
    "post_code": 251,
    "pid": 710800,
    "id": 3471,
    "area_id": 710810
  },
  {
    "area_name": "汐止区",
    "pinyin": "Xizhi",
    "level": 3,
    "area_code": 2,
    "post_code": 221,
    "pid": 710800,
    "id": 3472,
    "area_id": 710811
  },
  {
    "area_name": "瑞芳区",
    "pinyin": "Ruifang",
    "level": 3,
    "area_code": 2,
    "post_code": 224,
    "pid": 710800,
    "id": 3473,
    "area_id": 710812
  },
  {
    "area_name": "土城区",
    "pinyin": "Tucheng",
    "level": 3,
    "area_code": 2,
    "post_code": 236,
    "pid": 710800,
    "id": 3474,
    "area_id": 710813
  },
  {
    "area_name": "芦洲区",
    "pinyin": "Luzhou",
    "level": 3,
    "area_code": 2,
    "post_code": 247,
    "pid": 710800,
    "id": 3475,
    "area_id": 710814
  },
  {
    "area_name": "五股区",
    "pinyin": "Wugu",
    "level": 3,
    "area_code": 2,
    "post_code": 248,
    "pid": 710800,
    "id": 3476,
    "area_id": 710815
  },
  {
    "area_name": "泰山区",
    "pinyin": "Taishan",
    "level": 3,
    "area_code": 2,
    "post_code": 243,
    "pid": 710800,
    "id": 3477,
    "area_id": 710816
  },
  {
    "area_name": "林口区",
    "pinyin": "Linkou",
    "level": 3,
    "area_code": 2,
    "post_code": 244,
    "pid": 710800,
    "id": 3478,
    "area_id": 710817
  },
  {
    "area_name": "深坑区",
    "pinyin": "Shenkeng",
    "level": 3,
    "area_code": 2,
    "post_code": 222,
    "pid": 710800,
    "id": 3479,
    "area_id": 710818
  },
  {
    "area_name": "石碇区",
    "pinyin": "Shiding",
    "level": 3,
    "area_code": 2,
    "post_code": 223,
    "pid": 710800,
    "id": 3480,
    "area_id": 710819
  },
  {
    "area_name": "坪林区",
    "pinyin": "Pinglin",
    "level": 3,
    "area_code": 2,
    "post_code": 232,
    "pid": 710800,
    "id": 3481,
    "area_id": 710820
  },
  {
    "area_name": "三芝区",
    "pinyin": "Sanzhi",
    "level": 3,
    "area_code": 2,
    "post_code": 252,
    "pid": 710800,
    "id": 3482,
    "area_id": 710821
  },
  {
    "area_name": "石门区",
    "pinyin": "Shimen",
    "level": 3,
    "area_code": 2,
    "post_code": 253,
    "pid": 710800,
    "id": 3483,
    "area_id": 710822
  },
  {
    "area_name": "八里区",
    "pinyin": "Bali",
    "level": 3,
    "area_code": 2,
    "post_code": 249,
    "pid": 710800,
    "id": 3484,
    "area_id": 710823
  },
  {
    "area_name": "平溪区",
    "pinyin": "Pingxi",
    "level": 3,
    "area_code": 2,
    "post_code": 226,
    "pid": 710800,
    "id": 3485,
    "area_id": 710824
  },
  {
    "area_name": "双溪区",
    "pinyin": "Shuangxi",
    "level": 3,
    "area_code": 2,
    "post_code": 227,
    "pid": 710800,
    "id": 3486,
    "area_id": 710825
  },
  {
    "area_name": "贡寮区",
    "pinyin": "Gongliao",
    "level": 3,
    "area_code": 2,
    "post_code": 228,
    "pid": 710800,
    "id": 3487,
    "area_id": 710826
  },
  {
    "area_name": "金山区",
    "pinyin": "Jinshan",
    "level": 3,
    "area_code": 2,
    "post_code": 208,
    "pid": 710800,
    "id": 3488,
    "area_id": 710827
  },
  {
    "area_name": "万里区",
    "pinyin": "Wanli",
    "level": 3,
    "area_code": 2,
    "post_code": 207,
    "pid": 710800,
    "id": 3489,
    "area_id": 710828
  },
  {
    "area_name": "乌来区",
    "pinyin": "Wulai",
    "level": 3,
    "area_code": 2,
    "post_code": 233,
    "pid": 710800,
    "id": 3490,
    "area_id": 710829
  },
  {
    "area_name": "宜兰县",
    "pinyin": "Yilan",
    "level": 2,
    "area_code": 3,
    "post_code": 2,
    "pid": 710000,
    "id": 3491,
    "area_id": 712200
  },
  {
    "area_name": "宜兰市",
    "pinyin": "Yilan",
    "level": 3,
    "area_code": 3,
    "post_code": 260,
    "pid": 712200,
    "id": 3492,
    "area_id": 712201
  },
  {
    "area_name": "罗东镇",
    "pinyin": "Luodong",
    "level": 3,
    "area_code": 3,
    "post_code": 265,
    "pid": 712200,
    "id": 3493,
    "area_id": 712221
  },
  {
    "area_name": "苏澳镇",
    "pinyin": "Suao",
    "level": 3,
    "area_code": 3,
    "post_code": 270,
    "pid": 712200,
    "id": 3494,
    "area_id": 712222
  },
  {
    "area_name": "头城镇",
    "pinyin": "Toucheng",
    "level": 3,
    "area_code": 3,
    "post_code": 261,
    "pid": 712200,
    "id": 3495,
    "area_id": 712223
  },
  {
    "area_name": "礁溪乡",
    "pinyin": "Jiaoxi",
    "level": 3,
    "area_code": 3,
    "post_code": 262,
    "pid": 712200,
    "id": 3496,
    "area_id": 712224
  },
  {
    "area_name": "壮围乡",
    "pinyin": "Zhuangwei",
    "level": 3,
    "area_code": 3,
    "post_code": 263,
    "pid": 712200,
    "id": 3497,
    "area_id": 712225
  },
  {
    "area_name": "员山乡",
    "pinyin": "Yuanshan",
    "level": 3,
    "area_code": 3,
    "post_code": 264,
    "pid": 712200,
    "id": 3498,
    "area_id": 712226
  },
  {
    "area_name": "冬山乡",
    "pinyin": "Dongshan",
    "level": 3,
    "area_code": 3,
    "post_code": 269,
    "pid": 712200,
    "id": 3499,
    "area_id": 712227
  },
  {
    "area_name": "五结乡",
    "pinyin": "Wujie",
    "level": 3,
    "area_code": 3,
    "post_code": 268,
    "pid": 712200,
    "id": 3500,
    "area_id": 712228
  },
  {
    "area_name": "三星乡",
    "pinyin": "Sanxing",
    "level": 3,
    "area_code": 3,
    "post_code": 266,
    "pid": 712200,
    "id": 3501,
    "area_id": 712229
  },
  {
    "area_name": "大同乡",
    "pinyin": "Datong",
    "level": 3,
    "area_code": 3,
    "post_code": 267,
    "pid": 712200,
    "id": 3502,
    "area_id": 712230
  },
  {
    "area_name": "南澳乡",
    "pinyin": "Nanao",
    "level": 3,
    "area_code": 3,
    "post_code": 272,
    "pid": 712200,
    "id": 3503,
    "area_id": 712231
  },
  {
    "area_name": "桃园县",
    "pinyin": "Taoyuan",
    "level": 2,
    "area_code": 3,
    "post_code": 3,
    "pid": 710000,
    "id": 3504,
    "area_id": 712300
  },
  {
    "area_name": "桃园市",
    "pinyin": "Taoyuan",
    "level": 3,
    "area_code": 3,
    "post_code": 330,
    "pid": 712300,
    "id": 3505,
    "area_id": 712301
  },
  {
    "area_name": "中坜市",
    "pinyin": "Zhongli",
    "level": 3,
    "area_code": 3,
    "post_code": 320,
    "pid": 712300,
    "id": 3506,
    "area_id": 712302
  },
  {
    "area_name": "平镇市",
    "pinyin": "Pingzhen",
    "level": 3,
    "area_code": 3,
    "post_code": 324,
    "pid": 712300,
    "id": 3507,
    "area_id": 712303
  },
  {
    "area_name": "八德市",
    "pinyin": "Bade",
    "level": 3,
    "area_code": 3,
    "post_code": 334,
    "pid": 712300,
    "id": 3508,
    "area_id": 712304
  },
  {
    "area_name": "杨梅市",
    "pinyin": "Yangmei",
    "level": 3,
    "area_code": 3,
    "post_code": 326,
    "pid": 712300,
    "id": 3509,
    "area_id": 712305
  },
  {
    "area_name": "芦竹市",
    "pinyin": "Luzhu",
    "level": 3,
    "area_code": 3,
    "post_code": 338,
    "pid": 712300,
    "id": 3510,
    "area_id": 712306
  },
  {
    "area_name": "大溪镇",
    "pinyin": "Daxi",
    "level": 3,
    "area_code": 3,
    "post_code": 335,
    "pid": 712300,
    "id": 3511,
    "area_id": 712321
  },
  {
    "area_name": "大园乡",
    "pinyin": "Dayuan",
    "level": 3,
    "area_code": 3,
    "post_code": 337,
    "pid": 712300,
    "id": 3512,
    "area_id": 712324
  },
  {
    "area_name": "龟山乡",
    "pinyin": "Guishan",
    "level": 3,
    "area_code": 3,
    "post_code": 333,
    "pid": 712300,
    "id": 3513,
    "area_id": 712325
  },
  {
    "area_name": "龙潭乡",
    "pinyin": "Longtan",
    "level": 3,
    "area_code": 3,
    "post_code": 325,
    "pid": 712300,
    "id": 3514,
    "area_id": 712327
  },
  {
    "area_name": "新屋乡",
    "pinyin": "Xinwu",
    "level": 3,
    "area_code": 3,
    "post_code": 327,
    "pid": 712300,
    "id": 3515,
    "area_id": 712329
  },
  {
    "area_name": "观音乡",
    "pinyin": "Guanyin",
    "level": 3,
    "area_code": 3,
    "post_code": 328,
    "pid": 712300,
    "id": 3516,
    "area_id": 712330
  },
  {
    "area_name": "复兴乡",
    "pinyin": "Fuxing",
    "level": 3,
    "area_code": 3,
    "post_code": 336,
    "pid": 712300,
    "id": 3517,
    "area_id": 712331
  },
  {
    "area_name": "新竹县",
    "pinyin": "Hsinchu",
    "level": 2,
    "area_code": 3,
    "post_code": 3,
    "pid": 710000,
    "id": 3518,
    "area_id": 712400
  },
  {
    "area_name": "竹北市",
    "pinyin": "Zhubei",
    "level": 3,
    "area_code": 3,
    "post_code": 302,
    "pid": 712400,
    "id": 3519,
    "area_id": 712401
  },
  {
    "area_name": "竹东镇",
    "pinyin": "Zhudong",
    "level": 3,
    "area_code": 3,
    "post_code": 310,
    "pid": 712400,
    "id": 3520,
    "area_id": 712421
  },
  {
    "area_name": "新埔镇",
    "pinyin": "Xinpu",
    "level": 3,
    "area_code": 3,
    "post_code": 305,
    "pid": 712400,
    "id": 3521,
    "area_id": 712422
  },
  {
    "area_name": "关西镇",
    "pinyin": "Guanxi",
    "level": 3,
    "area_code": 3,
    "post_code": 306,
    "pid": 712400,
    "id": 3522,
    "area_id": 712423
  },
  {
    "area_name": "湖口乡",
    "pinyin": "Hukou",
    "level": 3,
    "area_code": 3,
    "post_code": 303,
    "pid": 712400,
    "id": 3523,
    "area_id": 712424
  },
  {
    "area_name": "新丰乡",
    "pinyin": "Xinfeng",
    "level": 3,
    "area_code": 3,
    "post_code": 304,
    "pid": 712400,
    "id": 3524,
    "area_id": 712425
  },
  {
    "area_name": "芎林乡",
    "pinyin": "Xionglin",
    "level": 3,
    "area_code": 3,
    "post_code": 307,
    "pid": 712400,
    "id": 3525,
    "area_id": 712426
  },
  {
    "area_name": "横山乡",
    "pinyin": "Hengshan",
    "level": 3,
    "area_code": 3,
    "post_code": 312,
    "pid": 712400,
    "id": 3526,
    "area_id": 712427
  },
  {
    "area_name": "北埔乡",
    "pinyin": "Beipu",
    "level": 3,
    "area_code": 3,
    "post_code": 314,
    "pid": 712400,
    "id": 3527,
    "area_id": 712428
  },
  {
    "area_name": "宝山乡",
    "pinyin": "Baoshan",
    "level": 3,
    "area_code": 3,
    "post_code": 308,
    "pid": 712400,
    "id": 3528,
    "area_id": 712429
  },
  {
    "area_name": "峨眉乡",
    "pinyin": "Emei",
    "level": 3,
    "area_code": 3,
    "post_code": 315,
    "pid": 712400,
    "id": 3529,
    "area_id": 712430
  },
  {
    "area_name": "尖石乡",
    "pinyin": "Jianshi",
    "level": 3,
    "area_code": 3,
    "post_code": 313,
    "pid": 712400,
    "id": 3530,
    "area_id": 712431
  },
  {
    "area_name": "五峰乡",
    "pinyin": "Wufeng",
    "level": 3,
    "area_code": 3,
    "post_code": 311,
    "pid": 712400,
    "id": 3531,
    "area_id": 712432
  },
  {
    "area_name": "苗栗县",
    "pinyin": "Miaoli",
    "level": 2,
    "area_code": 37,
    "post_code": 3,
    "pid": 710000,
    "id": 3532,
    "area_id": 712500
  },
  {
    "area_name": "苗栗市",
    "pinyin": "Miaoli",
    "level": 3,
    "area_code": 37,
    "post_code": 360,
    "pid": 712500,
    "id": 3533,
    "area_id": 712501
  },
  {
    "area_name": "苑里镇",
    "pinyin": "Yuanli",
    "level": 3,
    "area_code": 37,
    "post_code": 358,
    "pid": 712500,
    "id": 3534,
    "area_id": 712521
  },
  {
    "area_name": "通霄镇",
    "pinyin": "Tongxiao",
    "level": 3,
    "area_code": 37,
    "post_code": 357,
    "pid": 712500,
    "id": 3535,
    "area_id": 712522
  },
  {
    "area_name": "竹南镇",
    "pinyin": "Zhunan",
    "level": 3,
    "area_code": 37,
    "post_code": 350,
    "pid": 712500,
    "id": 3536,
    "area_id": 712523
  },
  {
    "area_name": "头份镇",
    "pinyin": "Toufen",
    "level": 3,
    "area_code": 37,
    "post_code": 351,
    "pid": 712500,
    "id": 3537,
    "area_id": 712524
  },
  {
    "area_name": "后龙镇",
    "pinyin": "Houlong",
    "level": 3,
    "area_code": 37,
    "post_code": 356,
    "pid": 712500,
    "id": 3538,
    "area_id": 712525
  },
  {
    "area_name": "卓兰镇",
    "pinyin": "Zhuolan",
    "level": 3,
    "area_code": 37,
    "post_code": 369,
    "pid": 712500,
    "id": 3539,
    "area_id": 712526
  },
  {
    "area_name": "大湖乡",
    "pinyin": "Dahu",
    "level": 3,
    "area_code": 37,
    "post_code": 364,
    "pid": 712500,
    "id": 3540,
    "area_id": 712527
  },
  {
    "area_name": "公馆乡",
    "pinyin": "Gongguan",
    "level": 3,
    "area_code": 37,
    "post_code": 363,
    "pid": 712500,
    "id": 3541,
    "area_id": 712528
  },
  {
    "area_name": "铜锣乡",
    "pinyin": "Tongluo",
    "level": 3,
    "area_code": 37,
    "post_code": 366,
    "pid": 712500,
    "id": 3542,
    "area_id": 712529
  },
  {
    "area_name": "南庄乡",
    "pinyin": "Nanzhuang",
    "level": 3,
    "area_code": 37,
    "post_code": 353,
    "pid": 712500,
    "id": 3543,
    "area_id": 712530
  },
  {
    "area_name": "头屋乡",
    "pinyin": "Touwu",
    "level": 3,
    "area_code": 37,
    "post_code": 362,
    "pid": 712500,
    "id": 3544,
    "area_id": 712531
  },
  {
    "area_name": "三义乡",
    "pinyin": "Sanyi",
    "level": 3,
    "area_code": 37,
    "post_code": 367,
    "pid": 712500,
    "id": 3545,
    "area_id": 712532
  },
  {
    "area_name": "西湖乡",
    "pinyin": "Xihu",
    "level": 3,
    "area_code": 37,
    "post_code": 368,
    "pid": 712500,
    "id": 3546,
    "area_id": 712533
  },
  {
    "area_name": "造桥乡",
    "pinyin": "Zaoqiao",
    "level": 3,
    "area_code": 37,
    "post_code": 361,
    "pid": 712500,
    "id": 3547,
    "area_id": 712534
  },
  {
    "area_name": "三湾乡",
    "pinyin": "Sanwan",
    "level": 3,
    "area_code": 37,
    "post_code": 352,
    "pid": 712500,
    "id": 3548,
    "area_id": 712535
  },
  {
    "area_name": "狮潭乡",
    "pinyin": "Shitan",
    "level": 3,
    "area_code": 37,
    "post_code": 354,
    "pid": 712500,
    "id": 3549,
    "area_id": 712536
  },
  {
    "area_name": "泰安乡",
    "pinyin": "Tai'an",
    "level": 3,
    "area_code": 37,
    "post_code": 365,
    "pid": 712500,
    "id": 3550,
    "area_id": 712537
  },
  {
    "area_name": "彰化县",
    "pinyin": "Changhua",
    "level": 2,
    "area_code": 4,
    "post_code": 5,
    "pid": 710000,
    "id": 3551,
    "area_id": 712700
  },
  {
    "area_name": "彰化市",
    "pinyin": "Zhanghuashi",
    "level": 3,
    "area_code": 4,
    "post_code": 500,
    "pid": 712700,
    "id": 3552,
    "area_id": 712701
  },
  {
    "area_name": "鹿港镇",
    "pinyin": "Lugang",
    "level": 3,
    "area_code": 4,
    "post_code": 505,
    "pid": 712700,
    "id": 3553,
    "area_id": 712721
  },
  {
    "area_name": "和美镇",
    "pinyin": "Hemei",
    "level": 3,
    "area_code": 4,
    "post_code": 508,
    "pid": 712700,
    "id": 3554,
    "area_id": 712722
  },
  {
    "area_name": "线西乡",
    "pinyin": "Xianxi",
    "level": 3,
    "area_code": 4,
    "post_code": 507,
    "pid": 712700,
    "id": 3555,
    "area_id": 712723
  },
  {
    "area_name": "伸港乡",
    "pinyin": "Shengang",
    "level": 3,
    "area_code": 4,
    "post_code": 509,
    "pid": 712700,
    "id": 3556,
    "area_id": 712724
  },
  {
    "area_name": "福兴乡",
    "pinyin": "Fuxing",
    "level": 3,
    "area_code": 4,
    "post_code": 506,
    "pid": 712700,
    "id": 3557,
    "area_id": 712725
  },
  {
    "area_name": "秀水乡",
    "pinyin": "Xiushui",
    "level": 3,
    "area_code": 4,
    "post_code": 504,
    "pid": 712700,
    "id": 3558,
    "area_id": 712726
  },
  {
    "area_name": "花坛乡",
    "pinyin": "Huatan",
    "level": 3,
    "area_code": 4,
    "post_code": 503,
    "pid": 712700,
    "id": 3559,
    "area_id": 712727
  },
  {
    "area_name": "芬园乡",
    "pinyin": "Fenyuan",
    "level": 3,
    "area_code": 4,
    "post_code": 502,
    "pid": 712700,
    "id": 3560,
    "area_id": 712728
  },
  {
    "area_name": "员林镇",
    "pinyin": "Yuanlin",
    "level": 3,
    "area_code": 4,
    "post_code": 510,
    "pid": 712700,
    "id": 3561,
    "area_id": 712729
  },
  {
    "area_name": "溪湖镇",
    "pinyin": "Xihu",
    "level": 3,
    "area_code": 4,
    "post_code": 514,
    "pid": 712700,
    "id": 3562,
    "area_id": 712730
  },
  {
    "area_name": "田中镇",
    "pinyin": "Tianzhong",
    "level": 3,
    "area_code": 4,
    "post_code": 520,
    "pid": 712700,
    "id": 3563,
    "area_id": 712731
  },
  {
    "area_name": "大村乡",
    "pinyin": "Dacun",
    "level": 3,
    "area_code": 4,
    "post_code": 515,
    "pid": 712700,
    "id": 3564,
    "area_id": 712732
  },
  {
    "area_name": "埔盐乡",
    "pinyin": "Puyan",
    "level": 3,
    "area_code": 4,
    "post_code": 516,
    "pid": 712700,
    "id": 3565,
    "area_id": 712733
  },
  {
    "area_name": "埔心乡",
    "pinyin": "Puxin",
    "level": 3,
    "area_code": 4,
    "post_code": 513,
    "pid": 712700,
    "id": 3566,
    "area_id": 712734
  },
  {
    "area_name": "永靖乡",
    "pinyin": "Yongjing",
    "level": 3,
    "area_code": 4,
    "post_code": 512,
    "pid": 712700,
    "id": 3567,
    "area_id": 712735
  },
  {
    "area_name": "社头乡",
    "pinyin": "Shetou",
    "level": 3,
    "area_code": 4,
    "post_code": 511,
    "pid": 712700,
    "id": 3568,
    "area_id": 712736
  },
  {
    "area_name": "二水乡",
    "pinyin": "Ershui",
    "level": 3,
    "area_code": 4,
    "post_code": 530,
    "pid": 712700,
    "id": 3569,
    "area_id": 712737
  },
  {
    "area_name": "北斗镇",
    "pinyin": "Beidou",
    "level": 3,
    "area_code": 4,
    "post_code": 521,
    "pid": 712700,
    "id": 3570,
    "area_id": 712738
  },
  {
    "area_name": "二林镇",
    "pinyin": "Erlin",
    "level": 3,
    "area_code": 4,
    "post_code": 526,
    "pid": 712700,
    "id": 3571,
    "area_id": 712739
  },
  {
    "area_name": "田尾乡",
    "pinyin": "Tianwei",
    "level": 3,
    "area_code": 4,
    "post_code": 522,
    "pid": 712700,
    "id": 3572,
    "area_id": 712740
  },
  {
    "area_name": "埤头乡",
    "pinyin": "Pitou",
    "level": 3,
    "area_code": 4,
    "post_code": 523,
    "pid": 712700,
    "id": 3573,
    "area_id": 712741
  },
  {
    "area_name": "芳苑乡",
    "pinyin": "Fangyuan",
    "level": 3,
    "area_code": 4,
    "post_code": 528,
    "pid": 712700,
    "id": 3574,
    "area_id": 712742
  },
  {
    "area_name": "大城乡",
    "pinyin": "Dacheng",
    "level": 3,
    "area_code": 4,
    "post_code": 527,
    "pid": 712700,
    "id": 3575,
    "area_id": 712743
  },
  {
    "area_name": "竹塘乡",
    "pinyin": "Zhutang",
    "level": 3,
    "area_code": 4,
    "post_code": 525,
    "pid": 712700,
    "id": 3576,
    "area_id": 712744
  },
  {
    "area_name": "溪州乡",
    "pinyin": "Xizhou",
    "level": 3,
    "area_code": 4,
    "post_code": 524,
    "pid": 712700,
    "id": 3577,
    "area_id": 712745
  },
  {
    "area_name": "南投县",
    "pinyin": "Nantou",
    "level": 2,
    "area_code": 49,
    "post_code": 5,
    "pid": 710000,
    "id": 3578,
    "area_id": 712800
  },
  {
    "area_name": "南投市",
    "pinyin": "Nantoushi",
    "level": 3,
    "area_code": 49,
    "post_code": 540,
    "pid": 712800,
    "id": 3579,
    "area_id": 712801
  },
  {
    "area_name": "埔里镇",
    "pinyin": "Puli",
    "level": 3,
    "area_code": 49,
    "post_code": 545,
    "pid": 712800,
    "id": 3580,
    "area_id": 712821
  },
  {
    "area_name": "草屯镇",
    "pinyin": "Caotun",
    "level": 3,
    "area_code": 49,
    "post_code": 542,
    "pid": 712800,
    "id": 3581,
    "area_id": 712822
  },
  {
    "area_name": "竹山镇",
    "pinyin": "Zhushan",
    "level": 3,
    "area_code": 49,
    "post_code": 557,
    "pid": 712800,
    "id": 3582,
    "area_id": 712823
  },
  {
    "area_name": "集集镇",
    "pinyin": "Jiji",
    "level": 3,
    "area_code": 49,
    "post_code": 552,
    "pid": 712800,
    "id": 3583,
    "area_id": 712824
  },
  {
    "area_name": "名间乡",
    "pinyin": "Mingjian",
    "level": 3,
    "area_code": 49,
    "post_code": 551,
    "pid": 712800,
    "id": 3584,
    "area_id": 712825
  },
  {
    "area_name": "鹿谷乡",
    "pinyin": "Lugu",
    "level": 3,
    "area_code": 49,
    "post_code": 558,
    "pid": 712800,
    "id": 3585,
    "area_id": 712826
  },
  {
    "area_name": "中寮乡",
    "pinyin": "Zhongliao",
    "level": 3,
    "area_code": 49,
    "post_code": 541,
    "pid": 712800,
    "id": 3586,
    "area_id": 712827
  },
  {
    "area_name": "鱼池乡",
    "pinyin": "Yuchi",
    "level": 3,
    "area_code": 49,
    "post_code": 555,
    "pid": 712800,
    "id": 3587,
    "area_id": 712828
  },
  {
    "area_name": "国姓乡",
    "pinyin": "Guoxing",
    "level": 3,
    "area_code": 49,
    "post_code": 544,
    "pid": 712800,
    "id": 3588,
    "area_id": 712829
  },
  {
    "area_name": "水里乡",
    "pinyin": "Shuili",
    "level": 3,
    "area_code": 49,
    "post_code": 553,
    "pid": 712800,
    "id": 3589,
    "area_id": 712830
  },
  {
    "area_name": "信义乡",
    "pinyin": "Xinyi",
    "level": 3,
    "area_code": 49,
    "post_code": 556,
    "pid": 712800,
    "id": 3590,
    "area_id": 712831
  },
  {
    "area_name": "仁爱乡",
    "pinyin": "Renai",
    "level": 3,
    "area_code": 49,
    "post_code": 546,
    "pid": 712800,
    "id": 3591,
    "area_id": 712832
  },
  {
    "area_name": "云林县",
    "pinyin": "Yunlin",
    "level": 2,
    "area_code": 5,
    "post_code": 6,
    "pid": 710000,
    "id": 3592,
    "area_id": 712900
  },
  {
    "area_name": "斗六市",
    "pinyin": "Douliu",
    "level": 3,
    "area_code": 5,
    "post_code": 640,
    "pid": 712900,
    "id": 3593,
    "area_id": 712901
  },
  {
    "area_name": "斗南镇",
    "pinyin": "Dounan",
    "level": 3,
    "area_code": 5,
    "post_code": 630,
    "pid": 712900,
    "id": 3594,
    "area_id": 712921
  },
  {
    "area_name": "虎尾镇",
    "pinyin": "Huwei",
    "level": 3,
    "area_code": 5,
    "post_code": 632,
    "pid": 712900,
    "id": 3595,
    "area_id": 712922
  },
  {
    "area_name": "西螺镇",
    "pinyin": "Xiluo",
    "level": 3,
    "area_code": 5,
    "post_code": 648,
    "pid": 712900,
    "id": 3596,
    "area_id": 712923
  },
  {
    "area_name": "土库镇",
    "pinyin": "Tuku",
    "level": 3,
    "area_code": 5,
    "post_code": 633,
    "pid": 712900,
    "id": 3597,
    "area_id": 712924
  },
  {
    "area_name": "北港镇",
    "pinyin": "Beigang",
    "level": 3,
    "area_code": 5,
    "post_code": 651,
    "pid": 712900,
    "id": 3598,
    "area_id": 712925
  },
  {
    "area_name": "古坑乡",
    "pinyin": "Gukeng",
    "level": 3,
    "area_code": 5,
    "post_code": 646,
    "pid": 712900,
    "id": 3599,
    "area_id": 712926
  },
  {
    "area_name": "大埤乡",
    "pinyin": "Dapi",
    "level": 3,
    "area_code": 5,
    "post_code": 631,
    "pid": 712900,
    "id": 3600,
    "area_id": 712927
  },
  {
    "area_name": "莿桐乡",
    "pinyin": "Citong",
    "level": 3,
    "area_code": 5,
    "post_code": 647,
    "pid": 712900,
    "id": 3601,
    "area_id": 712928
  },
  {
    "area_name": "林内乡",
    "pinyin": "Linna",
    "level": 3,
    "area_code": 5,
    "post_code": 643,
    "pid": 712900,
    "id": 3602,
    "area_id": 712929
  },
  {
    "area_name": "二仑乡",
    "pinyin": "Erlun",
    "level": 3,
    "area_code": 5,
    "post_code": 649,
    "pid": 712900,
    "id": 3603,
    "area_id": 712930
  },
  {
    "area_name": "仑背乡",
    "pinyin": "Lunbei",
    "level": 3,
    "area_code": 5,
    "post_code": 637,
    "pid": 712900,
    "id": 3604,
    "area_id": 712931
  },
  {
    "area_name": "麦寮乡",
    "pinyin": "Mailiao",
    "level": 3,
    "area_code": 5,
    "post_code": 638,
    "pid": 712900,
    "id": 3605,
    "area_id": 712932
  },
  {
    "area_name": "东势乡",
    "pinyin": "Dongshi",
    "level": 3,
    "area_code": 5,
    "post_code": 635,
    "pid": 712900,
    "id": 3606,
    "area_id": 712933
  },
  {
    "area_name": "褒忠乡",
    "pinyin": "Baozhong",
    "level": 3,
    "area_code": 5,
    "post_code": 634,
    "pid": 712900,
    "id": 3607,
    "area_id": 712934
  },
  {
    "area_name": "台西乡",
    "pinyin": "Taixi",
    "level": 3,
    "area_code": 5,
    "post_code": 636,
    "pid": 712900,
    "id": 3608,
    "area_id": 712935
  },
  {
    "area_name": "元长乡",
    "pinyin": "Yuanchang",
    "level": 3,
    "area_code": 5,
    "post_code": 655,
    "pid": 712900,
    "id": 3609,
    "area_id": 712936
  },
  {
    "area_name": "四湖乡",
    "pinyin": "Sihu",
    "level": 3,
    "area_code": 5,
    "post_code": 654,
    "pid": 712900,
    "id": 3610,
    "area_id": 712937
  },
  {
    "area_name": "口湖乡",
    "pinyin": "Kouhu",
    "level": 3,
    "area_code": 5,
    "post_code": 653,
    "pid": 712900,
    "id": 3611,
    "area_id": 712938
  },
  {
    "area_name": "水林乡",
    "pinyin": "Shuilin",
    "level": 3,
    "area_code": 5,
    "post_code": 652,
    "pid": 712900,
    "id": 3612,
    "area_id": 712939
  },
  {
    "area_name": "嘉义县",
    "pinyin": "Chiayi",
    "level": 2,
    "area_code": 5,
    "post_code": 6,
    "pid": 710000,
    "id": 3613,
    "area_id": 713000
  },
  {
    "area_name": "太保市",
    "pinyin": "Taibao",
    "level": 3,
    "area_code": 5,
    "post_code": 612,
    "pid": 713000,
    "id": 3614,
    "area_id": 713001
  },
  {
    "area_name": "朴子市",
    "pinyin": "Puzi",
    "level": 3,
    "area_code": 5,
    "post_code": 613,
    "pid": 713000,
    "id": 3615,
    "area_id": 713002
  },
  {
    "area_name": "布袋镇",
    "pinyin": "Budai",
    "level": 3,
    "area_code": 5,
    "post_code": 625,
    "pid": 713000,
    "id": 3616,
    "area_id": 713023
  },
  {
    "area_name": "大林镇",
    "pinyin": "Dalin",
    "level": 3,
    "area_code": 5,
    "post_code": 622,
    "pid": 713000,
    "id": 3617,
    "area_id": 713024
  },
  {
    "area_name": "民雄乡",
    "pinyin": "Minxiong",
    "level": 3,
    "area_code": 5,
    "post_code": 621,
    "pid": 713000,
    "id": 3618,
    "area_id": 713025
  },
  {
    "area_name": "溪口乡",
    "pinyin": "Xikou",
    "level": 3,
    "area_code": 5,
    "post_code": 623,
    "pid": 713000,
    "id": 3619,
    "area_id": 713026
  },
  {
    "area_name": "新港乡",
    "pinyin": "Xingang",
    "level": 3,
    "area_code": 5,
    "post_code": 616,
    "pid": 713000,
    "id": 3620,
    "area_id": 713027
  },
  {
    "area_name": "六脚乡",
    "pinyin": "Liujiao",
    "level": 3,
    "area_code": 5,
    "post_code": 615,
    "pid": 713000,
    "id": 3621,
    "area_id": 713028
  },
  {
    "area_name": "东石乡",
    "pinyin": "Dongshi",
    "level": 3,
    "area_code": 5,
    "post_code": 614,
    "pid": 713000,
    "id": 3622,
    "area_id": 713029
  },
  {
    "area_name": "义竹乡",
    "pinyin": "Yizhu",
    "level": 3,
    "area_code": 5,
    "post_code": 624,
    "pid": 713000,
    "id": 3623,
    "area_id": 713030
  },
  {
    "area_name": "鹿草乡",
    "pinyin": "Lucao",
    "level": 3,
    "area_code": 5,
    "post_code": 611,
    "pid": 713000,
    "id": 3624,
    "area_id": 713031
  },
  {
    "area_name": "水上乡",
    "pinyin": "Shuishang",
    "level": 3,
    "area_code": 5,
    "post_code": 608,
    "pid": 713000,
    "id": 3625,
    "area_id": 713032
  },
  {
    "area_name": "中埔乡",
    "pinyin": "Zhongpu",
    "level": 3,
    "area_code": 5,
    "post_code": 606,
    "pid": 713000,
    "id": 3626,
    "area_id": 713033
  },
  {
    "area_name": "竹崎乡",
    "pinyin": "Zhuqi",
    "level": 3,
    "area_code": 5,
    "post_code": 604,
    "pid": 713000,
    "id": 3627,
    "area_id": 713034
  },
  {
    "area_name": "梅山乡",
    "pinyin": "Meishan",
    "level": 3,
    "area_code": 5,
    "post_code": 603,
    "pid": 713000,
    "id": 3628,
    "area_id": 713035
  },
  {
    "area_name": "番路乡",
    "pinyin": "Fanlu",
    "level": 3,
    "area_code": 5,
    "post_code": 602,
    "pid": 713000,
    "id": 3629,
    "area_id": 713036
  },
  {
    "area_name": "大埔乡",
    "pinyin": "Dapu",
    "level": 3,
    "area_code": 5,
    "post_code": 607,
    "pid": 713000,
    "id": 3630,
    "area_id": 713037
  },
  {
    "area_name": "阿里山乡",
    "pinyin": "Alishan",
    "level": 3,
    "area_code": 5,
    "post_code": 605,
    "pid": 713000,
    "id": 3631,
    "area_id": 713038
  },
  {
    "area_name": "屏东县",
    "pinyin": "Pingtung",
    "level": 2,
    "area_code": 8,
    "post_code": 9,
    "pid": 710000,
    "id": 3632,
    "area_id": 713300
  },
  {
    "area_name": "屏东市",
    "pinyin": "Pingdong",
    "level": 3,
    "area_code": 8,
    "post_code": 900,
    "pid": 713300,
    "id": 3633,
    "area_id": 713301
  },
  {
    "area_name": "潮州镇",
    "pinyin": "Chaozhou",
    "level": 3,
    "area_code": 8,
    "post_code": 920,
    "pid": 713300,
    "id": 3634,
    "area_id": 713321
  },
  {
    "area_name": "东港镇",
    "pinyin": "Donggang",
    "level": 3,
    "area_code": 8,
    "post_code": 928,
    "pid": 713300,
    "id": 3635,
    "area_id": 713322
  },
  {
    "area_name": "恒春镇",
    "pinyin": "Hengchun",
    "level": 3,
    "area_code": 8,
    "post_code": 946,
    "pid": 713300,
    "id": 3636,
    "area_id": 713323
  },
  {
    "area_name": "万丹乡",
    "pinyin": "Wandan",
    "level": 3,
    "area_code": 8,
    "post_code": 913,
    "pid": 713300,
    "id": 3637,
    "area_id": 713324
  },
  {
    "area_name": "长治乡",
    "pinyin": "Changzhi",
    "level": 3,
    "area_code": 8,
    "post_code": 908,
    "pid": 713300,
    "id": 3638,
    "area_id": 713325
  },
  {
    "area_name": "麟洛乡",
    "pinyin": "Linluo",
    "level": 3,
    "area_code": 8,
    "post_code": 909,
    "pid": 713300,
    "id": 3639,
    "area_id": 713326
  },
  {
    "area_name": "九如乡",
    "pinyin": "Jiuru",
    "level": 3,
    "area_code": 8,
    "post_code": 904,
    "pid": 713300,
    "id": 3640,
    "area_id": 713327
  },
  {
    "area_name": "里港乡",
    "pinyin": "Ligang",
    "level": 3,
    "area_code": 8,
    "post_code": 905,
    "pid": 713300,
    "id": 3641,
    "area_id": 713328
  },
  {
    "area_name": "盐埔乡",
    "pinyin": "Yanpu",
    "level": 3,
    "area_code": 8,
    "post_code": 907,
    "pid": 713300,
    "id": 3642,
    "area_id": 713329
  },
  {
    "area_name": "高树乡",
    "pinyin": "Gaoshu",
    "level": 3,
    "area_code": 8,
    "post_code": 906,
    "pid": 713300,
    "id": 3643,
    "area_id": 713330
  },
  {
    "area_name": "万峦乡",
    "pinyin": "Wanluan",
    "level": 3,
    "area_code": 8,
    "post_code": 923,
    "pid": 713300,
    "id": 3644,
    "area_id": 713331
  },
  {
    "area_name": "内埔乡",
    "pinyin": "Napu",
    "level": 3,
    "area_code": 8,
    "post_code": 912,
    "pid": 713300,
    "id": 3645,
    "area_id": 713332
  },
  {
    "area_name": "竹田乡",
    "pinyin": "Zhutian",
    "level": 3,
    "area_code": 8,
    "post_code": 911,
    "pid": 713300,
    "id": 3646,
    "area_id": 713333
  },
  {
    "area_name": "新埤乡",
    "pinyin": "Xinpi",
    "level": 3,
    "area_code": 8,
    "post_code": 925,
    "pid": 713300,
    "id": 3647,
    "area_id": 713334
  },
  {
    "area_name": "枋寮乡",
    "pinyin": "Fangliao",
    "level": 3,
    "area_code": 8,
    "post_code": 940,
    "pid": 713300,
    "id": 3648,
    "area_id": 713335
  },
  {
    "area_name": "新园乡",
    "pinyin": "Xinyuan",
    "level": 3,
    "area_code": 8,
    "post_code": 932,
    "pid": 713300,
    "id": 3649,
    "area_id": 713336
  },
  {
    "area_name": "崁顶乡",
    "pinyin": "Kanding",
    "level": 3,
    "area_code": 8,
    "post_code": 924,
    "pid": 713300,
    "id": 3650,
    "area_id": 713337
  },
  {
    "area_name": "林边乡",
    "pinyin": "Linbian",
    "level": 3,
    "area_code": 8,
    "post_code": 927,
    "pid": 713300,
    "id": 3651,
    "area_id": 713338
  },
  {
    "area_name": "南州乡",
    "pinyin": "Nanzhou",
    "level": 3,
    "area_code": 8,
    "post_code": 926,
    "pid": 713300,
    "id": 3652,
    "area_id": 713339
  },
  {
    "area_name": "佳冬乡",
    "pinyin": "Jiadong",
    "level": 3,
    "area_code": 8,
    "post_code": 931,
    "pid": 713300,
    "id": 3653,
    "area_id": 713340
  },
  {
    "area_name": "琉球乡",
    "pinyin": "Liuqiu",
    "level": 3,
    "area_code": 8,
    "post_code": 929,
    "pid": 713300,
    "id": 3654,
    "area_id": 713341
  },
  {
    "area_name": "车城乡",
    "pinyin": "Checheng",
    "level": 3,
    "area_code": 8,
    "post_code": 944,
    "pid": 713300,
    "id": 3655,
    "area_id": 713342
  },
  {
    "area_name": "满州乡",
    "pinyin": "Manzhou",
    "level": 3,
    "area_code": 8,
    "post_code": 947,
    "pid": 713300,
    "id": 3656,
    "area_id": 713343
  },
  {
    "area_name": "枋山乡",
    "pinyin": "Fangshan",
    "level": 3,
    "area_code": 8,
    "post_code": 941,
    "pid": 713300,
    "id": 3657,
    "area_id": 713344
  },
  {
    "area_name": "三地门乡",
    "pinyin": "Sandimen",
    "level": 3,
    "area_code": 8,
    "post_code": 901,
    "pid": 713300,
    "id": 3658,
    "area_id": 713345
  },
  {
    "area_name": "雾台乡",
    "pinyin": "Wutai",
    "level": 3,
    "area_code": 8,
    "post_code": 902,
    "pid": 713300,
    "id": 3659,
    "area_id": 713346
  },
  {
    "area_name": "玛家乡",
    "pinyin": "Majia",
    "level": 3,
    "area_code": 8,
    "post_code": 903,
    "pid": 713300,
    "id": 3660,
    "area_id": 713347
  },
  {
    "area_name": "泰武乡",
    "pinyin": "Taiwu",
    "level": 3,
    "area_code": 8,
    "post_code": 921,
    "pid": 713300,
    "id": 3661,
    "area_id": 713348
  },
  {
    "area_name": "来义乡",
    "pinyin": "Laiyi",
    "level": 3,
    "area_code": 8,
    "post_code": 922,
    "pid": 713300,
    "id": 3662,
    "area_id": 713349
  },
  {
    "area_name": "春日乡",
    "pinyin": "Chunri",
    "level": 3,
    "area_code": 8,
    "post_code": 942,
    "pid": 713300,
    "id": 3663,
    "area_id": 713350
  },
  {
    "area_name": "狮子乡",
    "pinyin": "Shizi",
    "level": 3,
    "area_code": 8,
    "post_code": 943,
    "pid": 713300,
    "id": 3664,
    "area_id": 713351
  },
  {
    "area_name": "牡丹乡",
    "pinyin": "Mudan",
    "level": 3,
    "area_code": 8,
    "post_code": 945,
    "pid": 713300,
    "id": 3665,
    "area_id": 713352
  },
  {
    "area_name": "台东县",
    "pinyin": "Taitung",
    "level": 2,
    "area_code": 89,
    "post_code": 9,
    "pid": 710000,
    "id": 3666,
    "area_id": 713400
  },
  {
    "area_name": "台东市",
    "pinyin": "Taidong",
    "level": 3,
    "area_code": 89,
    "post_code": 950,
    "pid": 713400,
    "id": 3667,
    "area_id": 713401
  },
  {
    "area_name": "成功镇",
    "pinyin": "Chenggong",
    "level": 3,
    "area_code": 89,
    "post_code": 961,
    "pid": 713400,
    "id": 3668,
    "area_id": 713421
  },
  {
    "area_name": "关山镇",
    "pinyin": "Guanshan",
    "level": 3,
    "area_code": 89,
    "post_code": 956,
    "pid": 713400,
    "id": 3669,
    "area_id": 713422
  },
  {
    "area_name": "卑南乡",
    "pinyin": "Beinan",
    "level": 3,
    "area_code": 89,
    "post_code": 954,
    "pid": 713400,
    "id": 3670,
    "area_id": 713423
  },
  {
    "area_name": "鹿野乡",
    "pinyin": "Luye",
    "level": 3,
    "area_code": 89,
    "post_code": 955,
    "pid": 713400,
    "id": 3671,
    "area_id": 713424
  },
  {
    "area_name": "池上乡",
    "pinyin": "Chishang",
    "level": 3,
    "area_code": 89,
    "post_code": 958,
    "pid": 713400,
    "id": 3672,
    "area_id": 713425
  },
  {
    "area_name": "东河乡",
    "pinyin": "Donghe",
    "level": 3,
    "area_code": 89,
    "post_code": 959,
    "pid": 713400,
    "id": 3673,
    "area_id": 713426
  },
  {
    "area_name": "长滨乡",
    "pinyin": "Changbin",
    "level": 3,
    "area_code": 89,
    "post_code": 962,
    "pid": 713400,
    "id": 3674,
    "area_id": 713427
  },
  {
    "area_name": "太麻里乡",
    "pinyin": "Taimali",
    "level": 3,
    "area_code": 89,
    "post_code": 963,
    "pid": 713400,
    "id": 3675,
    "area_id": 713428
  },
  {
    "area_name": "大武乡",
    "pinyin": "Dawu",
    "level": 3,
    "area_code": 89,
    "post_code": 965,
    "pid": 713400,
    "id": 3676,
    "area_id": 713429
  },
  {
    "area_name": "绿岛乡",
    "pinyin": "Lvdao",
    "level": 3,
    "area_code": 89,
    "post_code": 951,
    "pid": 713400,
    "id": 3677,
    "area_id": 713430
  },
  {
    "area_name": "海端乡",
    "pinyin": "Haiduan",
    "level": 3,
    "area_code": 89,
    "post_code": 957,
    "pid": 713400,
    "id": 3678,
    "area_id": 713431
  },
  {
    "area_name": "延平乡",
    "pinyin": "Yanping",
    "level": 3,
    "area_code": 89,
    "post_code": 953,
    "pid": 713400,
    "id": 3679,
    "area_id": 713432
  },
  {
    "area_name": "金峰乡",
    "pinyin": "Jinfeng",
    "level": 3,
    "area_code": 89,
    "post_code": 964,
    "pid": 713400,
    "id": 3680,
    "area_id": 713433
  },
  {
    "area_name": "达仁乡",
    "pinyin": "Daren",
    "level": 3,
    "area_code": 89,
    "post_code": 966,
    "pid": 713400,
    "id": 3681,
    "area_id": 713434
  },
  {
    "area_name": "兰屿乡",
    "pinyin": "Lanyu",
    "level": 3,
    "area_code": 89,
    "post_code": 952,
    "pid": 713400,
    "id": 3682,
    "area_id": 713435
  },
  {
    "area_name": "花莲县",
    "pinyin": "Hualien",
    "level": 2,
    "area_code": 3,
    "post_code": 9,
    "pid": 710000,
    "id": 3683,
    "area_id": 713500
  },
  {
    "area_name": "花莲市",
    "pinyin": "Hualian",
    "level": 3,
    "area_code": 3,
    "post_code": 970,
    "pid": 713500,
    "id": 3684,
    "area_id": 713501
  },
  {
    "area_name": "凤林镇",
    "pinyin": "Fenglin",
    "level": 3,
    "area_code": 3,
    "post_code": 975,
    "pid": 713500,
    "id": 3685,
    "area_id": 713521
  },
  {
    "area_name": "玉里镇",
    "pinyin": "Yuli",
    "level": 3,
    "area_code": 3,
    "post_code": 981,
    "pid": 713500,
    "id": 3686,
    "area_id": 713522
  },
  {
    "area_name": "新城乡",
    "pinyin": "Xincheng",
    "level": 3,
    "area_code": 3,
    "post_code": 971,
    "pid": 713500,
    "id": 3687,
    "area_id": 713523
  },
  {
    "area_name": "吉安乡",
    "pinyin": "Ji'an",
    "level": 3,
    "area_code": 3,
    "post_code": 973,
    "pid": 713500,
    "id": 3688,
    "area_id": 713524
  },
  {
    "area_name": "寿丰乡",
    "pinyin": "Shoufeng",
    "level": 3,
    "area_code": 3,
    "post_code": 974,
    "pid": 713500,
    "id": 3689,
    "area_id": 713525
  },
  {
    "area_name": "光复乡",
    "pinyin": "Guangfu",
    "level": 3,
    "area_code": 3,
    "post_code": 976,
    "pid": 713500,
    "id": 3690,
    "area_id": 713526
  },
  {
    "area_name": "丰滨乡",
    "pinyin": "Fengbin",
    "level": 3,
    "area_code": 3,
    "post_code": 977,
    "pid": 713500,
    "id": 3691,
    "area_id": 713527
  },
  {
    "area_name": "瑞穗乡",
    "pinyin": "Ruisui",
    "level": 3,
    "area_code": 3,
    "post_code": 978,
    "pid": 713500,
    "id": 3692,
    "area_id": 713528
  },
  {
    "area_name": "富里乡",
    "pinyin": "Fuli",
    "level": 3,
    "area_code": 3,
    "post_code": 983,
    "pid": 713500,
    "id": 3693,
    "area_id": 713529
  },
  {
    "area_name": "秀林乡",
    "pinyin": "Xiulin",
    "level": 3,
    "area_code": 3,
    "post_code": 972,
    "pid": 713500,
    "id": 3694,
    "area_id": 713530
  },
  {
    "area_name": "万荣乡",
    "pinyin": "Wanrong",
    "level": 3,
    "area_code": 3,
    "post_code": 979,
    "pid": 713500,
    "id": 3695,
    "area_id": 713531
  },
  {
    "area_name": "卓溪乡",
    "pinyin": "Zhuoxi",
    "level": 3,
    "area_code": 3,
    "post_code": 982,
    "pid": 713500,
    "id": 3696,
    "area_id": 713532
  },
  {
    "area_name": "澎湖县",
    "pinyin": "Penghu",
    "level": 2,
    "area_code": 6,
    "post_code": 8,
    "pid": 710000,
    "id": 3697,
    "area_id": 713600
  },
  {
    "area_name": "马公市",
    "pinyin": "Magong",
    "level": 3,
    "area_code": 6,
    "post_code": 880,
    "pid": 713600,
    "id": 3698,
    "area_id": 713601
  },
  {
    "area_name": "湖西乡",
    "pinyin": "Huxi",
    "level": 3,
    "area_code": 6,
    "post_code": 885,
    "pid": 713600,
    "id": 3699,
    "area_id": 713621
  },
  {
    "area_name": "白沙乡",
    "pinyin": "Baisha",
    "level": 3,
    "area_code": 6,
    "post_code": 884,
    "pid": 713600,
    "id": 3700,
    "area_id": 713622
  },
  {
    "area_name": "西屿乡",
    "pinyin": "Xiyu",
    "level": 3,
    "area_code": 6,
    "post_code": 881,
    "pid": 713600,
    "id": 3701,
    "area_id": 713623
  },
  {
    "area_name": "望安乡",
    "pinyin": "Wang'an",
    "level": 3,
    "area_code": 6,
    "post_code": 882,
    "pid": 713600,
    "id": 3702,
    "area_id": 713624
  },
  {
    "area_name": "七美乡",
    "pinyin": "Qimei",
    "level": 3,
    "area_code": 6,
    "post_code": 883,
    "pid": 713600,
    "id": 3703,
    "area_id": 713625
  },
  {
    "area_name": "金门县",
    "pinyin": "Jinmen",
    "level": 2,
    "area_code": 82,
    "post_code": 8,
    "pid": 710000,
    "id": 3704,
    "area_id": 713700
  },
  {
    "area_name": "金城镇",
    "pinyin": "Jincheng",
    "level": 3,
    "area_code": 82,
    "post_code": 893,
    "pid": 713700,
    "id": 3705,
    "area_id": 713701
  },
  {
    "area_name": "金湖镇",
    "pinyin": "Jinhu",
    "level": 3,
    "area_code": 82,
    "post_code": 891,
    "pid": 713700,
    "id": 3706,
    "area_id": 713702
  },
  {
    "area_name": "金沙镇",
    "pinyin": "Jinsha",
    "level": 3,
    "area_code": 82,
    "post_code": 890,
    "pid": 713700,
    "id": 3707,
    "area_id": 713703
  },
  {
    "area_name": "金宁乡",
    "pinyin": "Jinning",
    "level": 3,
    "area_code": 82,
    "post_code": 892,
    "pid": 713700,
    "id": 3708,
    "area_id": 713704
  },
  {
    "area_name": "烈屿乡",
    "pinyin": "Lieyu",
    "level": 3,
    "area_code": 82,
    "post_code": 894,
    "pid": 713700,
    "id": 3709,
    "area_id": 713705
  },
  {
    "area_name": "乌丘乡",
    "pinyin": "Wuqiu",
    "level": 3,
    "area_code": 82,
    "post_code": 896,
    "pid": 713700,
    "id": 3710,
    "area_id": 713706
  },
  {
    "area_name": "连江县",
    "pinyin": "Lienchiang",
    "level": 2,
    "area_code": 836,
    "post_code": 2,
    "pid": 710000,
    "id": 3711,
    "area_id": 713800
  },
  {
    "area_name": "南竿乡",
    "pinyin": "Nangan",
    "level": 3,
    "area_code": 836,
    "post_code": 209,
    "pid": 713800,
    "id": 3712,
    "area_id": 713801
  },
  {
    "area_name": "北竿乡",
    "pinyin": "Beigan",
    "level": 3,
    "area_code": 836,
    "post_code": 210,
    "pid": 713800,
    "id": 3713,
    "area_id": 713802
  },
  {
    "area_name": "莒光乡",
    "pinyin": "Juguang",
    "level": 3,
    "area_code": 836,
    "post_code": 211,
    "pid": 713800,
    "id": 3714,
    "area_id": 713803
  },
  {
    "area_name": "东引乡",
    "pinyin": "Dongyin",
    "level": 3,
    "area_code": 836,
    "post_code": 212,
    "pid": 713800,
    "id": 3715,
    "area_id": 713804
  },
  {
    "area_name": "香港特别行政区",
    "pinyin": "Hong Kong",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3716,
    "area_id": 810000
  },
  {
    "area_name": "香港岛",
    "pinyin": "Hong Kong Island",
    "level": 2,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810000,
    "id": 3717,
    "area_id": 810100
  },
  {
    "area_name": "中西区",
    "pinyin": "Central and Western District",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810100,
    "id": 3718,
    "area_id": 810101
  },
  {
    "area_name": "湾仔区",
    "pinyin": "Wan Chai District",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810100,
    "id": 3719,
    "area_id": 810102
  },
  {
    "area_name": "东区",
    "pinyin": "Eastern District",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810100,
    "id": 3720,
    "area_id": 810103
  },
  {
    "area_name": "南区",
    "pinyin": "Southern District",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810100,
    "id": 3721,
    "area_id": 810104
  },
  {
    "area_name": "九龙",
    "pinyin": "Kowloon",
    "level": 2,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810000,
    "id": 3722,
    "area_id": 810200
  },
  {
    "area_name": "油尖旺区",
    "pinyin": "Yau Tsim Mong",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810200,
    "id": 3723,
    "area_id": 810201
  },
  {
    "area_name": "深水埗区",
    "pinyin": "Sham Shui Po",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810200,
    "id": 3724,
    "area_id": 810202
  },
  {
    "area_name": "九龙城区",
    "pinyin": "Jiulongcheng",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810200,
    "id": 3725,
    "area_id": 810203
  },
  {
    "area_name": "黄大仙区",
    "pinyin": "Wong Tai Sin",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810200,
    "id": 3726,
    "area_id": 810204
  },
  {
    "area_name": "观塘区",
    "pinyin": "Kwun Tong",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810200,
    "id": 3727,
    "area_id": 810205
  },
  {
    "area_name": "新界",
    "pinyin": "New Territories",
    "level": 2,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810000,
    "id": 3728,
    "area_id": 810300
  },
  {
    "area_name": "荃湾区",
    "pinyin": "Tsuen Wan",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3729,
    "area_id": 810301
  },
  {
    "area_name": "屯门区",
    "pinyin": "Tuen Mun",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3730,
    "area_id": 810302
  },
  {
    "area_name": "元朗区",
    "pinyin": "Yuen Long",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3731,
    "area_id": 810303
  },
  {
    "area_name": "北区",
    "pinyin": "North District",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3732,
    "area_id": 810304
  },
  {
    "area_name": "大埔区",
    "pinyin": "Tai Po",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3733,
    "area_id": 810305
  },
  {
    "area_name": "西贡区",
    "pinyin": "Sai Kung",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3734,
    "area_id": 810306
  },
  {
    "area_name": "沙田区",
    "pinyin": "Sha Tin",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3735,
    "area_id": 810307
  },
  {
    "area_name": "葵青区",
    "pinyin": "Kwai Tsing",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3736,
    "area_id": 810308
  },
  {
    "area_name": "离岛区",
    "pinyin": "Outlying Islands",
    "level": 3,
    "area_code": 852,
    "post_code": 999077,
    "pid": 810300,
    "id": 3737,
    "area_id": 810309
  },
  {
    "area_name": "澳门特别行政区",
    "pinyin": "Macau",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3738,
    "area_id": 820000
  },
  {
    "area_name": "澳门半岛",
    "pinyin": "MacauPeninsula",
    "level": 2,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820000,
    "id": 3739,
    "area_id": 820100
  },
  {
    "area_name": "花地玛堂区",
    "pinyin": "Nossa Senhora de Fatima",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820100,
    "id": 3740,
    "area_id": 820101
  },
  {
    "area_name": "圣安多尼堂区",
    "pinyin": "Santo Antonio",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820100,
    "id": 3741,
    "area_id": 820102
  },
  {
    "area_name": "大堂区",
    "pinyin": "S�",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820100,
    "id": 3742,
    "area_id": 820103
  },
  {
    "area_name": "望德堂区",
    "pinyin": "Sao Lazaro",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820100,
    "id": 3743,
    "area_id": 820104
  },
  {
    "area_name": "风顺堂区",
    "pinyin": "Sao Lourenco",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820100,
    "id": 3744,
    "area_id": 820105
  },
  {
    "area_name": "氹仔岛",
    "pinyin": "Taipa",
    "level": 2,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820000,
    "id": 3745,
    "area_id": 820200
  },
  {
    "area_name": "嘉模堂区",
    "pinyin": "Our Lady Of Carmel's Parish",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820200,
    "id": 3746,
    "area_id": 820201
  },
  {
    "area_name": "路环岛",
    "pinyin": "Coloane",
    "level": 2,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820000,
    "id": 3747,
    "area_id": 820300
  },
  {
    "area_name": "圣方济各堂区",
    "pinyin": "St Francis Xavier's Parish",
    "level": 3,
    "area_code": 853,
    "post_code": 999078,
    "pid": 820300,
    "id": 3748,
    "area_id": 820301
  },
  {
    "area_name": "钓鱼岛",
    "pinyin": "DiaoyuDao",
    "level": 1,
    "area_code": null,
    "post_code": null,
    "pid": null,
    "id": 3749,
    "area_id": 900000
  }
]

let cityDataTree = setTreeDataUtil(cityAllData, 'pid', 'area_id')

let provinceData = {}
let cityData = {}
let districtData = {}
let province = []
let city = {}
let district = {}
cityDataTree.forEach((item) => {
  let provinceObj = {
    area_name: item.area_name,
    pinyin: item.pinyin,
    level: item.level,
    area_code: item.area_code,
    post_code: item.post_code,
    pid: item.pid,
    id: item.id,
    area_id: item.area_id,
    leaf: false,
  }
  city[item.area_id] = []
  if (item.children) {
    item.children.forEach((cityItem) => {
      let cityObj = {
        area_name: cityItem.area_name,
        pinyin: cityItem.pinyin,
        level: cityItem.level,
        area_code: cityItem.area_code,
        post_code: cityItem.post_code,
        pid: cityItem.pid,
        id: cityItem.id,
        area_id: cityItem.area_id,
        leaf: false,
      }
      district[cityItem.area_id] = []
      if (cityItem.children) {
        cityItem.children.forEach((districtItem) => {
          let districtObj = {
            area_name: districtItem.area_name,
            pinyin: districtItem.pinyin,
            level: districtItem.level,
            area_code: districtItem.area_code,
            post_code: districtItem.post_code,
            pid: districtItem.pid,
            id: districtItem.id,
            area_id: districtItem.area_id,
            leaf: true,
          }
          districtData[districtObj.area_id] = districtObj
          district[cityItem.area_id].push(districtObj)
        })
      } else {
        cityObj.leaf = true
      }
      cityData[cityObj.area_id] = cityObj
      city[item.area_id].push(cityObj)
    })
  } else {
    provinceObj.leaf = true
  }
  provinceData[provinceObj.area_id] = provinceObj
  province.push(provinceObj)
})

export const cityObj = {
  all: cityDataTree,
  provinceData,
  cityData,
  districtData,
  province,
  city,
  district,
  nullTreeData:cityAllData,
}