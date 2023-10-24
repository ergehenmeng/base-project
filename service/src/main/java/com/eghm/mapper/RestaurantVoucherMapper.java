package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.restaurant.voucher.VoucherQueryDTO;
import com.eghm.model.RestaurantVoucher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.vo.business.restaurant.VoucherVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 餐饮代金券 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
public interface RestaurantVoucherMapper extends BaseMapper<RestaurantVoucher> {

    /**
     * 更新库存
     * @param id id
     * @param num 数量 负数-库存 正数+库存
     * @return 1
     */
    int updateStock(@Param("id") Long id, @Param("num")int num);

    /**
     * 分页查询餐饮券列表
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<VoucherVO> getList(Page<VoucherVO> page, VoucherQueryDTO dto);
}
