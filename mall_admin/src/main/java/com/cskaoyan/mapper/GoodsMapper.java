package com.cskaoyan.mapper;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.GoodsExample;
import java.util.List;

import com.cskaoyan.bean.goods.*;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {
    long countByExample(GoodsExample example);

    int deleteByExample(GoodsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    List<Goods> selectByExample(GoodsExample example);

    Goods selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAllGoods(@Param("sort") String sort,@Param("order") String order,@Param("goodsSn") Integer goodsSn,@Param("name") String name);

    void addGoods(@Param("myAddGoods") MyAddGoods myAddGoods);

    void addGoodsSpecification(@Param("goodsId") int goods_id, @Param("specList") List<AddGoodsSpecification> addGoodsSpecifications);

    void addProducts(@Param("goodsId")int goods_id, @Param("products")List<AddProducts> products);

    void addAttribute(@Param("goodsId")int goods_id,@Param("attributes") List<AddAttribute> attributes);

    void deleteObjectOfGoods(@Param("goodId") int goodId);

    void deleteSpecificationsOfGoods(@Param("goodId")int goodId);

    void deleteProductsOfGoods(@Param("goodId")int goodId);

    void deleteAttributeOfGoods(@Param("goodId")int goodId);

    Integer getL2CategoryIdByGoodsId(@Param("id") Integer id);

    Integer getL1CategoryIdByL2Id(@Param("l2_id") Integer l2_id);

    DetailGoods getDetailOfGoods(@Param("id") Integer id);

    List<AttributeDetail> getDetailOfAttributes(@Param("id") Integer id);

    List<SpecificationDetail> getDetailOfSpecifications(@Param("id")Integer id);

    List<ProductDetail> getDetailOfProducts(@Param("id")Integer id);

    void updateObjectOfGoods(@Param("goods") MyAddGoods goods);

    void deleteGoodsSpecifications(@Param("goodsId")int goodsId,@Param("undeletedSpecsList") List<AddGoodsSpecification> undeletedSpecsList);

    void deleteAttributes(@Param("goodsId")int goodsId,@Param("undeletedAttributesList") List<AddAttribute> undeletedAttributesList);


    void deleteProductsByGoodsId(@Param("goodsId") int goodsId);

    void addProductsByGoodsId(@Param("goodsId") int goodsId, @Param("products") List<AddProducts> products);

    List<Comment> getCommentList(@Param("sort") String sort, @Param("order") String order,@Param("userId") Integer userId,@Param("valueId") Integer valueId);

    void deleteComment(@Param("comment") Comment comment);

    void addReply(@Param("goodsReply") GoodsReply goodsReply);

    Comment getCommentById(@Param("commentId") Integer commentId);
}
