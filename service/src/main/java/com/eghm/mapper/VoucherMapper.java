package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryRequest;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.model.Voucher;
import com.eghm.vo.business.restaurant.VoucherResponse;
import com.eghm.vo.business.restaurant.VoucherVO;
import com.eghm.vo.business.statistics.ProductStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 餐饮代金券 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
public interface VoucherMapper extends BaseMapper<Voucher> {

    /**
     * 分页查询餐饮券列表
     *
     * @param page    分页参数
     * @param request 查询条件
     * @return 列表
     */
    Page<VoucherResponse> listPage(Page<VoucherResponse> page, @Param("param") VoucherQueryRequest request);

    /**
     * 更新库存
     *
     * @param id  id
     * @param num 数量 负数-库存 正数+库存
     * @return 1
     */
    int updateStock(@Param("id") Long id, @Param("num") int num);

    /**
     * 分页查询餐饮券列表
     *
     * @param page 分页
     * @param dto  查询条件
     * @return 列表
     */
    Page<VoucherVO> getList(Page<VoucherVO> page, @Param("param") VoucherQueryDTO dto);

    /**
     * 更新评分
     *
     * @param id    id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 新增商品列表
     *
     * @param request 查询条件
     * @return 列表
     */
    List<ProductStatisticsVO> dayAppend(ProductRequest request);
}
