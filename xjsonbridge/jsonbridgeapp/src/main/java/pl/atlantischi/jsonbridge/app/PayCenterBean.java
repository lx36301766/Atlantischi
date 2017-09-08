package pl.atlantischi.jsonbridge.app;

import java.util.List;

import com.squareup.moshi.Json;

/**
 * Created by admin on 2017/8/26.
 */

@SuppressWarnings("all")
public class PayCenterBean {

    public int code;
    @XJsonBridgeRename(value = "sub_code")
    @Json(name = "sub_code")
    private int sub2Code;
    public String action;
    @XJsonBridgeRename(value = "message")
    @Json(name = "message")
    public String smessssasge1233;
    public DataBean data;
    public List<String> extra;

    public static class DataBean {
        public String confirm_type_info;
        public TitleBean head_info;
        public TitleBean submit_info;
        public DeliveryUnreachableBean delivery_unreachable;
        public AddressBean address;
        public InvoiceBean invoice;
        public SummaryBean summary;
        public IdVerifyInfoBean id_verify_info;
        public LeaveToastInfoBean leave_toast_info;
        public List<OrdersBean> orders;
        public List<DeliveryDayBean> delivery_day;
    }

    public static class TitleBean {
        public String title;
    }

    public static class DeliveryUnreachableBean {
        public String label;
        public String win_title;
        public String link_label;
        public String link;
        public String action;
    }

    public static class AddressBean {

        public int need_id_num;
        public int is_disable_edit;
        public AddressInfoBean address_info;

        public static class AddressInfoBean {
            public String address_id;
            public String receiver_name;
            public String hp;
            public String structured_address;
            public String address_raw;
            public int is_default;
        }

    }

    public static class InvoiceBean {

        public int allow_choose;
        public LastInfoBean last_info;
        public TipsBean tips;
        public String notice;
        public List<MediumMapBean> medium_map;
        public List<String> message;

        public static class LastInfoBean {
            public int is_choosed;
            public int invoice_type;
            public String invoice_company_name;
            public int invoice_medium;
            public String invoice_code;
            public String invoice_email;
        }

        public static class TipsBean {

            public String message;
            public int show_type;
            public String url;
            public NotInvoiceInfoBean not_invoice_info;

            public static class NotInvoiceInfoBean {
                public String title;
                public List<ListsBean> lists;
            }

            public static class ListsBean {
                public String item_short_name;
                public String image;
                public String attr_desc;
            }

        }

        public static class MediumMapBean {
            public int invoice_medium;
            public String name;
            public int useable;
            public int need_verify;
        }

    }

    public static class SummaryBean {
        public int quantity;
        public double rule_amount;
        public double total_amount;
        public int delivery_amount;
        public double promo_card_amount;
        public int red_envelope_amount;
        public int all_orders_items_amount;
        public int show_tax_price;
        public int goods_price_amount;
        public int tax_price_amount;
        public int real_discount_tax_price_amount;
    }

    public static class IdVerifyInfoBean {
        public String name;
        public String id_num;
        public String cert_id;
        public String notice;
        public String name_key;
        public String id_num_key;
        public String name_default;
        public String id_num_default;
        public int is_show;
        public int is_payer_auth;
    }

    public static class LeaveToastInfoBean {

        public int is_show;
        public String message;
        public CancelButtonBean cancel_button;
        public QuitButtonBean quit_button;

        public static class CancelButtonBean {
            public String title;
        }

        public static class QuitButtonBean {
            public String title;
            public String redirect;
        }

    }

    public static class OrdersBean {
        public String order_key;
        public String order_type;
        public String seller_desc;
        public String order_amount;
        public String order_pay_amount;
        public String order_discount_price;
        public int need_express;
        public PromoCardBean promo_card;
        public RedEnvelopeBean red_envelope;
        public SimplePromoCardAndRedEnvelopeBean simple_promo_card_and_red_envelope;
        public DiscountInfoBean discount_info;
        public int order_tax_fee_amount;
        public List<ItemsBean> items;
        public List<DeliveryInfoBean> delivery_info;
        public List<MatchRuleDescBean> match_rule_desc;

        public static class PromoCardBean {
            public String usable_num_desc;
            public String discount_desc;
            public String disable_desc;
            public int allow_use;
            public int usable_num;
            public int has_used;
        }

        public static class RedEnvelopeBean {
            public String usable_num_desc;
            public String discount_desc;
            public String disable_desc;
            public int allow_use;
            public int usable_num;
            public int has_used;
        }

        public static class SimplePromoCardAndRedEnvelopeBean {
            public String title;
            public String discount_desc;
            public String more_desc;
            public String price_desc;
            public String price_desc_mode;
            public int list_show;
        }

        public static class DiscountInfoBean {
            public String discount_title;
            public DiscountViewBean discount_view;
        }

        public static class DiscountViewBean {
            public String title;
            public String footer;
            public List<ItemsBean> items;

            public static class ItemsBean {
                public String item_short_name;
                public String image;
                public String attr_desc;
                public String item_price;
                public String item_discount_price;
                public int quantity;
                public String real_desc;
                public String real_title;
            }
        }

        public static class ItemsBean {
            public String item_key;
            public String item_type;
            public String item_short_name;
            public String item_price;
            public int quantity;
            public String item_amount;
            public String label;
            public String price_desc;
            public String item_buy_price;
            public String attr_desc;
            public int low_inventory;
            public String user_purchase_limit;
            public int item_limit;
            public String item_limit_desc;
            public String sale_status;
            public String add_key;
            public String type;
            public String image;
            public int show_vcb;
            public int is_presale;
            public int allow_modify_qty;
            public int end_time;
            public String item_url;
            public String star_shop_name;
            public int show_tax_price;
            public String product_id;
            public int sale_type;
            public String sku;
            public String deal_hash_id;
            public String item_id;
            public String category_ids;
            public String item_discount_price;
            public String image_tips;
            public String image_tips_color;
            public int delivery_unreachable;
            public List<String> promo_sale;
        }

        public static class DeliveryInfoBean {
            public String type;
            public String title;
            public String fee;
            public String reduction;
            public String reduction_desc;
            public String order_amount;
            public int is_default;
            public String real_fee;
            public String order_pay_amount;
            public String desc;
        }

        public static class MatchRuleDescBean {
            public String display_name;
            public String rule_desc;
            public String short_desc;
            public String rule_tag;
            public String rule_id;
            public String item_value;
        }

    }

    public static class DeliveryDayBean {
        public String type;
        public String desc;
        public int is_default;
        public int is_disable_edit;
    }

}
