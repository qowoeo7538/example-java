package org.lucas.example.framework.web.spring.enums;

/**
 * 第三方CPS商品库渠道类型枚举
 *
 * @author: ganxiaoqiang@wljs.com
 * Date: 2020/3/16
 * Time: 21:39
 */

/**
 * 第三方CPS商品库对接进入本系统的第三方类型标示编码
 *
 * @author: ganxiaoqiang@wljs.com
 * @version: v1.0
 * Date: 2020/3/16 21:39
 * <p>
 * 注：商详是支持id传参商品编号打开，商品id传参需要避开商品编号数据范围（100000000000 ~ 999999999999)
 * <p>
 * <p>
 * int类型第三方商品id数据格式：共计使用54位二级制【0~53】
 * <p>
 * NO.53 ~ NO.48   【共 6位】   ==>     标示第三方渠道id（淘宝联盟、京东、拼多多等 枚举值 code）  取值范围：1~63 共计最多支持63个第三方渠道平台
 * NO.47 ~ NO.44   【共 4位】   ==>     待保留位（预留可能做版本号等扩展保留）                  取值范围：1~15
 * NO.43 ~ NO.0    【共44位】   ==>     第三方商品在第三方平台的商品id                        取值范围：1~17,592,186,044,415（17.59兆）
 */

public enum ProductCpsChannelEnum {
    /**
     * 淘客联盟商品
     */
    tao_bao(1, "淘宝联盟商品库【淘宝+天猫】");

    /**
     * 第三方平台在本系统中的编码
     */
    private int code;
    /**
     * 第三方平台的信息描述
     */
    private String desc;

    ProductCpsChannelEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 将第三方商品库的原始id编码成为系统可识别编码
     *
     * @param cpsOriginalProductId 第三方原始商品id
     * @return 编码后可识别的商品id
     */
    public long encodeCpsProductId(long cpsOriginalProductId) {
        if (cpsOriginalProductId > THIRD_PARTY_MAX_ORGINAL_PRODUCT_NUMBER) {
            // log.error("第三方商品id编码异常,id超过最大可支持范围,入参商品id:{}", cpsOriginalProductId);
            // throw new BusinessException(ErrorCodeEnum.ERR_INVALID_PARAM, "第三方商品id超过最大可支持范围：" + cpsOriginalProductId);
        } else if (cpsOriginalProductId < 0) {
            // log.error("第三方商品id编码异常,id超过最小可支持范围0,入参商品id:{}", cpsOriginalProductId);
            // throw new BusinessException(ErrorCodeEnum.ERR_INVALID_PARAM, "第三方商品id超过最小可支持范围0,入参商品id:" + cpsOriginalProductId);
        }
        return ((long) getCode() << Indexer.INDEX_CHANEL_PAIR.start) | cpsOriginalProductId;
    }

    /**
     * 将编码后的第三方商品id进行解码，查询其在第三方商品库中的原始商品id【不符合预期会抛出"请求参数无效"异常】
     *
     * @param encodedProductId 编码后的第三方商品id
     * @return 第三方商品的原始商品id（在第三方商品库中的id）
     */
    public long decodeCpsProductId(long encodedProductId) {
        if (encodedProductId > THIRD_PARTY_MAX_ENCODED_PRODUCT_NUMBER) {
            // log.error("第三方商品id解码异常,id超过最大可解码支持范围,入参商品id:{}", encodedProductId);
            // throw new BusinessException(ErrorCodeEnum.ERR_INVALID_PARAM, "第三方商品id解码异常,超过最大可支持范围：" + encodedProductId);
        } else if (encodedProductId < THIRD_PARTY_MIN_ENCODED_PRODUCT_NUMBER) {
            // log.error("第三方商品id解码异常,id超过最小可解码支持范围,入参商品id:{}", encodedProductId);
            // throw new BusinessException(ErrorCodeEnum.ERR_INVALID_PARAM, "第三方商品id解码异常，超过最小可支持范围：" + encodedProductId);
        }
        byte offset = (byte) (MAX_BYTE_LENGTH - Indexer.INDEX_CHANEL_PAIR.start);
        return encodedProductId << offset >> offset;
    }

    /**
     * 将编码后的第三方商品id进行解码，查询其在第三方商品库中的原始商品id【不会抛出异常】
     *
     * @param encodedProductId 编码后的第三方商品id
     * @return 第三方商品的原始商品id（在第三方商品库中的id）
     */
    public Long decodeCpsProductIdSafety(long encodedProductId) {
        if (encodedProductId > THIRD_PARTY_MAX_ENCODED_PRODUCT_NUMBER) {
            return null;
        } else if (encodedProductId < THIRD_PARTY_MIN_ENCODED_PRODUCT_NUMBER) {
            return null;
        }
        byte offset = (byte) (MAX_BYTE_LENGTH - Indexer.INDEX_CHANEL_PAIR.start);
        return encodedProductId << offset >> offset;
    }

    /**
     * 是否是商品编号（非商品id）【自营商品约定的商品编号是12位的字符串】
     */
    public static boolean isNativeProductNumber(long product) {
        return product >= 100000000000L && product <= 999999999999L;
    }

    /**
     * 根据已经encode过的商品id，查询其隶属于对应的第三方平台类型枚举
     *
     * @param encodedProductId 已经encode过的商品id
     * @return 编码后的第三方商品id对应的第三方渠道枚举
     */
    public static ProductCpsChannelEnum getThirdPartyChannelByEncodedProductId(long encodedProductId) {
        if (encodedProductId > THIRD_PARTY_MAX_ENCODED_PRODUCT_NUMBER) {
            // log.error("第三方商品id编码异常,解码渠道,id超过最大解码支持范围,入参商品id:{}", encodedProductId);
            // throw new BusinessException(ErrorCodeEnum.ERR_INVALID_PARAM, "第三方商品encodedId不合法，超过最大可支持范围：" + encodedProductId);
        } else if (encodedProductId < THIRD_PARTY_MIN_ENCODED_PRODUCT_NUMBER) {
            // log.error("第三方商品id编码异常,解码渠道,id超过最小解码支持范围,入参商品id:{}", encodedProductId);
            // throw new BusinessException(ErrorCodeEnum.ERR_INVALID_PARAM, "第三方商品encodedId不合法，超过最小可支持范围：" + encodedProductId);
        }
        int channelCode = (int) (encodedProductId >> Indexer.INDEX_CHANEL_PAIR.start);
        if (channelCode > 0) {
            for (ProductCpsChannelEnum channelTypeEnum : ProductCpsChannelEnum.values()) {
                if (channelTypeEnum.code == channelCode) {
                    return channelTypeEnum;
                }
            }
        }
        return null;
    }

    /**
     * 第三方商品原始支持的最大id
     */
    public static final long THIRD_PARTY_MAX_ORGINAL_PRODUCT_NUMBER = ((long) 1 << (Indexer.INDEX_PRODUCTID_PAIR.end + 1)) - 1;

    /**
     * 第三方商品id encode后的最大值（可以等于）
     */
    public static final long THIRD_PARTY_MAX_ENCODED_PRODUCT_NUMBER = ((long) 1 << (Indexer.INDEX_CHANEL_PAIR.end + 1)) - 1;
    /**
     * 第三方商品id encode后的最小值（可以等于）
     */
    public static final long THIRD_PARTY_MIN_ENCODED_PRODUCT_NUMBER = ((long) 1 << Indexer.INDEX_CHANEL_PAIR.start);

    /**
     * 运算最大支持位数
     */
    private static final byte MAX_BYTE_LENGTH = 64;

    public static void main(String[] args) {
        System.out.println("第三方商品原始最大支持id=" + THIRD_PARTY_MAX_ORGINAL_PRODUCT_NUMBER);
        System.out.println("第三方商品econde后最大支持id=" + THIRD_PARTY_MAX_ENCODED_PRODUCT_NUMBER);
        System.out.println("第三方商品econde后最小支持id=" + THIRD_PARTY_MIN_ENCODED_PRODUCT_NUMBER);
        int thirdpartyId = 999353592;
        ProductCpsChannelEnum channelTypeEnum = ProductCpsChannelEnum.tao_bao;
        long encodedProductId = channelTypeEnum.encodeCpsProductId(thirdpartyId);
        System.out.println("encode后商品id= " + encodedProductId);
        ProductCpsChannelEnum channel = ProductCpsChannelEnum.getThirdPartyChannelByEncodedProductId(encodedProductId);
        System.out.println("解析渠道id信息=" + channel);
        long originalProductId = channelTypeEnum.decodeCpsProductId(encodedProductId);
        // Preconditions.checkState(thirdpartyId == originalProductId, "过滤加密解密后无法对称 :" + originalProductId);
        System.out.println("最大id16进制：" + Long.toString(ProductCpsChannelEnum.THIRD_PARTY_MAX_ENCODED_PRODUCT_NUMBER, 16).length());
    }

    /**
     * 枚举编码
     */

    public Integer getCode() {
        return code;
    }

    /**
     * 枚举描述
     */

    public String getDesc() {
        return desc;
    }
}

class Indexer {
    /**
     * 渠道位起止位置
     */
    static final BytePair INDEX_CHANEL_PAIR = new BytePair((byte) 48, (byte) 53);
    /**
     * 商品id位起止位置
     */
    static final BytePair INDEX_PRODUCTID_PAIR = new BytePair((byte) 0, (byte) 43);
}

class BytePair {
    byte start;
    byte end;

    public BytePair(byte start, byte end) {
        this.start = start;
        this.end = end;
    }
}
