<template>
    <AppLayout >
         <main class="ml-32 mt-10">
            <h1 class="font-size-title">LIST COURSES IN CART</h1>
            <div v-if="cart === {}">
                <div class="p-4 mb-4 text-sm text-red-800 rounded-lg bg-red-50 dark:bg-gray-800 dark:text-red-400" role="alert">
                    <span class="font-medium">Không có sản phẩm nào trong giỏ hàng</span>
                </div>
            </div>
                    
            <div class="flex mt-10" v-else>
                <div >
                    <table id="default-table">
                        <thead>
                            <tr>
                                <th>
                                    <span class="mr-80 flex items-center">
                                        Course
                                    </span>
                                </th>
                                <th>
                                    <span class="mr-56 flex items-center">
                                        Price
                                    </span>
                                </th>
                                <th>
                                    <span class="mr-20 flex items-center">
                                        old price
                                    </span>
                                </th>
                                <th>
                                    <span class="mr-10 flex items-center">
                                        Action
                                    </span>
                                </th>
                                <!-- <th><th> -->
                            </tr>
                           <tr>
                                <td colspan="4" class="pt-5 pb-5">
                                    <hr class="custom-hr" />
                                </td>
                            </tr>
                        </thead>
                        
                    <tbody>
                        <template v-for="(item, id) in cart" :key="id">
                            <tr>
                                <td>
                                    <div style="width: 350px;">
                                        {{ item.name }}
                                    </div>
                                </td>
                                <td style="color: red">{{formatCurrencyWithRounding(item.price * (1 - item.discount / 100))}}</td>
                                <td>
                                    <del>{{formatCurrencyWithRounding(item.price)}} VND
                                    </del>
                                    </td>
                                <td>
                                    <svg
                                        @click="removeFromCart(item.id)"
                                        class="w-6 h-6 text-gray-800 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                                        <path fill-rule="evenodd" d="M2 12C2 6.477 6.477 2 12 2s10 4.477 10 10-4.477 10-10 10S2 17.523 2 12Zm7.707-3.707a1 1 0 0 0-1.414 1.414L10.586 12l-2.293 2.293a1 1 0 1 0 1.414 1.414L12 13.414l2.293 2.293a1 1 0 0 0 1.414-1.414L13.414 12l2.293-2.293a1 1 0 0 0-1.414-1.414L12 10.586 9.707 8.293Z" clip-rule="evenodd"/>
                                    </svg>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" class="pt-5 pb-5">
                                    <hr class="custom-hr" />
                                </td>
                            </tr>
                        </template>
                    </tbody>
                    </table>
                </div>
                <div class="w-1/3 div-border ml-10">
                    <p class="ml-5 pt-5 pb-5" style="font-weight: bold; font-size: large">
                        ORDER SUMMARY
                    </p>
                    <div class="ml-5 pt-5 pb-5">
                        <div class="flex">
                            <span>Subtotal</span>
                            <span class="ml-80">{{formatCurrencyWithRounding(subtotal)}} VND</span>
                        </div>
                        <hr class="my-3 mr-16" />
                        <div>
                            <span>Discount</span>
                            <span class="ml-80">{{formatCurrencyWithRounding(subtotal - totalPrice)}} VND</span>
                        </div>
                        <hr class="my-3 mr-16" />
                        <div>
                            <span>Total</span>
                            <span style="margin-left: 345px; color: red">{{formatCurrencyWithRounding(totalPrice)}} <span>VND</span></span>
                        </div>
                        <div>
                            <button @click="checkout" style="width: 400px; border-radius: 10px; font-size: large" type="button" class="text-white my-8 mx-8 bg-gradient-to-r from-purple-500 to-pink-500 hover:bg-gradient-to-l focus:ring-4 focus:outline-none focus:ring-purple-200 dark:focus:ring-purple-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center me-2 mb-2">
                                Checkout
                            </button>

                        </div>
                        <div>
                            <button @click="notify" class="ml-8 mt-8 relative inline-flex items-center justify-center p-0.5 mb-2 me-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-purple-500 to-pink-500 group-hover:from-purple-500 group-hover:to-pink-500 hover:text-white dark:text-white focus:ring-4 focus:outline-none focus:ring-purple-200 dark:focus:ring-purple-800">
                                <span style="width: 400px; border-radius: 10px; font-size: large" class="relative px-5 py-2.5 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                                    Continue shopping
                                </span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            
        </main>
    </AppLayout>
</template>
<script>
import AppLayout from "@/layouts/default.vue";
import { v4 as uuidv4 } from 'uuid';
import { computed } from "vue";
import { useCookies } from "vue3-cookies";
import { useStore } from "vuex";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';
import { authAPIs, endpoints } from "@/configs/APIs";
// import { useRoute } from "vue-router";

export default ({
    components: {
    AppLayout,
  },
  setup() {
    const { cookies } = useCookies();
    const store = useStore();

    const notify = () => {
      toast("Checkout successul!!", {
        "theme": "auto",
        "type": "success","autoClose": 2000,
        "dangerouslyHTMLString": true
        })
    }

    let cart = cookies.get("cart") || {};  // Load cart from cookies or initialize as an empty object
    console.log(cart);

    const totalPrice = computed(() => {
            return cart
                ? Object.values(cart).reduce(
                    (sum, c) => sum + c.price * (1 - c.discount / 100) * c.quantity,
                    0
                )
                : 0;
        });

    const subtotal = computed(() => {
        return cart ? Object.values(cart).reduce(
            (sum, c) => sum + c.price, 0
        ) : 0;
    })

    const formatCurrencyWithRounding = (value) => {
		const roundedValue = Math.floor(value) + (value % 1 >= 0.5 ? 1 : 0);
		return roundedValue.toLocaleString().replace(/\B(?=(\d{3})+(?!))/g, ",");
	};

    const total = cookies.get("totalQuantity");

    const removeFromCart = (courseId) => {
        // console.log("bla bla delete");
        const updated = { ...cart };
        delete updated[courseId];
        cart = updated; // Cập nhật giá trị cart
        cookies.set("cart", updated);
        cookies.set("totalQuantity", total);
        store.commit('updateCart', cart);  // Lưu giỏ hàng đã cập nhật vào cookie
    };
    
    const checkout = async () => {
  try {
    // Tạo một ID đơn hàng mới
    const newOrderId = uuidv4();
    const token = store.getters.token;

    // Thực hiện yêu cầu API để tạo thanh toán
    const res = await authAPIs().post(endpoints["checkout"], {
      orderId: newOrderId,
      amount: totalPrice.value, // Sử dụng .value nếu đây là ref
      returnUrl: "http://localhost:3000/cart", // URL trở về sau khi thanh toán
    }, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    // Log dữ liệu phản hồi
    console.log(res.data);

    // Lấy URL thanh toán
    const { payUrl } = res.data;

    // Nếu payUrl tồn tại, chuyển hướng người dùng đến trang thanh toán
    if (payUrl) {
      // Chuyển hướng tới trang thanh toán
      window.location.href = payUrl;
        cookies.remove('cart'); // Xóa cookie 'cart'
      cookies.set('totalQuantity', 0);
        store.dispatch("paid");

        updateCartAfterPayment();
    } else {
      console.error("payUrl không tồn tại");
    }

  } catch (error) {
    console.error("Tạo thanh toán thất bại!!!", error);
  }
};

// Sau khi quay lại từ trang thanh toán, xóa giỏ hàng và cập nhật thông tin
const updateCartAfterPayment = async () => {
  try {
    const token = store.getters.token;
    await authAPIs().post(endpoints["updatePayment"], Object.values(cart), {
      headers: {
        Authorization: `Bearer ${token}`, // Đảm bảo rằng bạn đang gửi token
      },
    });
    // toast.success("Thanh toán thành công!")

  } catch (error) {
    // Log thông tin chi tiết hơn về lỗi
    console.error("Cập nhật thanh toán thất bại", error.response?.data || error.message);
  }
};


// Gọi hàm updateCartAfterPayment sau khi người dùng quay lại từ MoMo
// if (window.location.href.includes('/cart')) {
//   updateCartAfterPayment();
// }

    
    return {
        notify,
        cart,
        formatCurrencyWithRounding,
        removeFromCart,
        totalPrice,
        subtotal,
        checkout
    }

  },
})
</script>
<style>
    .font-size-title {
        font-size: xx-large;
        font-weight: bold;
    }
    .custom-hr {
    width: 100%; /* Đặt chiều rộng bằng 100% */
    border: none; /* Xóa border mặc định */
    border-top: 3px solid black; /* Đặt độ dày và màu cho đường */
    margin: 0; /* Xóa margin */
    }
    .div-border {
        border: 1px solid grey;
        border-radius: 10px;
    }

</style>