package com.lx.phonerecycle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lx.phonerecycle.gsonbean.N2A3_UserLogin;
import com.lx.phonerecycle.gsonbean.N2A4_ModifyUserHeaderInfo;
import com.lx.phonerecycle.gsonbean.N2A4_ModifyUserInfo;
import com.lx.phonerecycle.helper.LoginInfoHelper;
import com.lx.phonerecycle.request.UrlBuilder;
import com.lx.phonerecycle.tools.BitmapTools;
import com.lx.phonerecycle.tools.Tools;
import com.lx.phonerecycle.tools.XLog;
import com.lx.phonerecycle.volley.ext.GsonRequest;
import com.lx.phonerecycle.volley.ext.MultipartRequest;
import com.lx.phonerecycle.widget.CircularImage;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by princeBB on 2014/7/16.
 *
 * 个人中心
 */

@ContentView(R.layout.activity_user_center)
public class UserCenterActivity extends BaseActivity {

    @InjectView(R.id.avatar)                private CircularImage avatarImg;

    @InjectView(R.id.user_name_group)       private ViewGroup userNameGroup;
    @InjectView(R.id.phone_group)           private ViewGroup phoneGroup;
    @InjectView(R.id.sex_group)             private ViewGroup sexGroup;
    @InjectView(R.id.birthday_group)        private ViewGroup birthdayGroup;
    @InjectView(R.id.email_group)           private ViewGroup emailGroup;
    @InjectView(R.id.address_group)         private ViewGroup addressGroup;
    @InjectView(R.id.modify_password_group) private ViewGroup modifyPasswordGroup;

    @InjectView(R.id.exit_btn)              private Button exitBtn;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleName("商户中心");

        hideItemArrow(userNameGroup);
        hideItemArrow(phoneGroup);

        setItemName(userNameGroup, "商户名称");
        setItemName(phoneGroup, "电话");
        setItemName(sexGroup, "性别");
        setItemName(birthdayGroup, "生日");
        setItemName(emailGroup, "邮箱");
        setItemName(addressGroup, "收件地址");
        setItemName(modifyPasswordGroup, "修改密码");

        avatarImg.getLayoutParams().width = screenWidth / 4;
        avatarImg.getLayoutParams().height = screenWidth / 4;

        N2A3_UserLogin loginInfo = LoginInfoHelper.read();
        if (loginInfo != null) {
            showImageView(avatarImg, loginInfo.header, R.drawable.default_head_icon);
            setItemValue(userNameGroup, loginInfo.name);
            setItemValue(phoneGroup, loginInfo.phone);
            String sex = "";
            if (loginInfo.sex.equals("F")) {
                sex = "男";
            } else if (loginInfo.sex.equals("M")) {
                sex = "女";
            }
            setItemValue(sexGroup, sex);
            setItemValue(birthdayGroup, loginInfo.birthday);
            setItemValue(emailGroup, loginInfo.email);
            setItemValue(addressGroup, loginInfo.address);
        }

        avatarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] items = {"相册", "拍照"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent photo = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(photo, PHOTO_REQUEST_CODE);
                                } else if (which == 1) {
                                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(camera, CAMERA_REQUEST_CODE);
                                }
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        sexGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] items = {"男", "女"};
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity)
                        .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    modifyItemValue(sexGroup, "男");
                                } else if (which == 1) {
                                    modifyItemValue(sexGroup, "女");
                                } else {
                                    showToastHint("错误性别");
                                }
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        });

        birthdayGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Calendar calendar = Calendar.getInstance();
        String birthday = getItemValue(birthdayGroup);
        try {
            Date date = sdf.parse(birthday);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        datePickerDialog = new DatePickerDialog(mActivity, new DatePickerDialog.OnDateSetListener() {

            boolean canModify = true;

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                if (canModify) {
                    Calendar c = new GregorianCalendar(year, month, dayOfMonth);
                    String date = sdf.format(c.getTime());
                    modifyItemValue(birthdayGroup, date);
                    canModify = false;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            canModify = true;
                        }
                    }, 500);
                }
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        emailGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText emailEdit = new EditText(mActivity);
                emailEdit.setSingleLine();
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("请输入邮箱")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(emailEdit)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String email = emailEdit.getText().toString();
                                if (Tools.isLegitimateEmail(email)) {
                                    modifyItemValue(emailGroup, email);
                                } else {
                                    showToastHint("邮箱地址错误");
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
        addressGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(mActivity, DeliveryAddressActivity.class);
            }
        });
        modifyPasswordGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tools.jumpActivity(mActivity, ModifyPasswordActivity.class);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("确认要退出登陆？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoginInfoHelper.clear();
                                Tools.jumpActivity(mActivity, LoginActivity.class);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

    }

    private DatePickerDialog datePickerDialog;

    private void setItemName(ViewGroup viewGroup, String name) {
        TextView textView = (TextView) viewGroup.findViewById(R.id.item_name);
        textView.setText(name);
    }

    private void setItemValue(ViewGroup viewGroup, String value) {
        TextView textView = (TextView) viewGroup.findViewById(R.id.item_value);
        textView.setText(value);
    }

    private void modifyItemValue(final ViewGroup viewGroup, final String value) {
        String displaySex = getItemValue(sexGroup);
        String birthday = getItemValue(birthdayGroup);
        String email = getItemValue(emailGroup);
        String address = getItemValue(addressGroup);
        if (viewGroup == sexGroup) {
            displaySex = value;
        } else if (viewGroup == birthdayGroup) {
            birthday = value;
        } else if (viewGroup == emailGroup) {
            email = value;
        } else {
            showToastHint("error ViewGroup");
        }
        final String sex;
        if (displaySex.equals("男")) {
            sex = "F";
        } else if (displaySex.equals("女")) {
            sex = "M";
        } else {
            sex = "";
        }
        final String birthdayF = birthday;
        final String emailF = email;
        showLoading();
        String url = UrlBuilder.getN2A4_ModifyUserInfo(sex, birthday, email, address);
        GsonRequest request = new GsonRequest<N2A4_ModifyUserInfo>(url, N2A4_ModifyUserInfo.class,
                new Response.Listener<N2A4_ModifyUserInfo>() {
                    @Override
                    public void onResponse(N2A4_ModifyUserInfo response) {
                        XLog.w(TAG, "N2A4 success, response=%s", response);
                        dismissLoading();
                        showToastHint(response.msg);
                        if (response.status == RESPONSE_OK) {
                            N2A3_UserLogin loginResult = LoginInfoHelper.read();
                            if (viewGroup == sexGroup) {
                                loginResult.sex = sex;
                            } else if (viewGroup == birthdayGroup) {
                                loginResult.birthday = birthdayF;
                            } else if (viewGroup == emailGroup) {
                                loginResult.email = emailF;
                            } else {
                                showToastHint("error ViewGroup");
                            }
                            LoginInfoHelper.update(loginResult);
                            TextView textView = (TextView) viewGroup.findViewById(R.id.item_value);
                            textView.setText(value);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A4 error, " + error);
                        showToastHint("上传异常");
                        dismissLoading();
                    }
                }
        );
        requestQueue.add(request);
    }

    private String getItemValue(ViewGroup viewGroup) {
        TextView textView = (TextView) viewGroup.findViewById(R.id.item_value);
        return textView.getText().toString();
    }

    private void hideItemArrow(ViewGroup viewGroup) {
        ImageView arrow = (ImageView) viewGroup.findViewById(R.id.arrow);
        arrow.setVisibility(View.GONE);
    }

    private static final int CAMERA_REQUEST_CODE = 5;
    private static final int PHOTO_REQUEST_CODE = 6;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Bitmap bitmap = data.getParcelableExtra("data");
            modifyAvatarToServer(bitmap);
        } else if (requestCode == PHOTO_REQUEST_CODE) {
            if (data == null) {
                return;
            }
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();

            Bitmap bmp = BitmapTools.decodeFile(picturePath, null, avatarImg.getWidth(), avatarImg.getHeight());
            XLog.d(TAG, "bmp w=%s, h=%s", bmp.getWidth(), bmp.getHeight());

            modifyAvatarToServer(bmp);
        }
    }

    private void modifyAvatarToServer(final Bitmap bitmap) {
        final Bitmap thumbnail = Tools.getCroppedBitmap(ThumbnailUtils.extractThumbnail(bitmap, 200, 200));
//        final Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
//        final Bitmap circleBitmap = Tools.getCroppedBitmap(thumbnail);
        bitmap.recycle();
        showLoading();
        String sex = getItemValue(sexGroup);
        if (sex.equals("男")) {
            sex = "F";
        } else if (sex.equals("女")) {
            sex = "M";
        } else {
            showToastHint("error sex");
        }
        String birthday = getItemValue(birthdayGroup);
        String email = getItemValue(emailGroup);
        String address = getItemValue(addressGroup);
        String url = UrlBuilder.getN2A4_ModifyUserInfo(sex, birthday, email, address);
        MultipartRequest request = new MultipartRequest<N2A4_ModifyUserHeaderInfo>(url, N2A4_ModifyUserHeaderInfo.class,
                new Response.Listener<N2A4_ModifyUserHeaderInfo>() {
                    @Override
                    public void onResponse(N2A4_ModifyUserHeaderInfo response) {
                        XLog.w(TAG, "N2A4 success, response=%s", response);
                        dismissLoading();
                        showToastHint(response.msg);
                        if (response.status == RESPONSE_OK) {
                            avatarImg.setImageBitmap(thumbnail);
                            N2A3_UserLogin loginResult = LoginInfoHelper.read();
                            loginResult.header = response.header;
                            LoginInfoHelper.update(loginResult);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        XLog.e(TAG, "N2A4 error, " + error);
                        showToastHint("上传异常");
                        dismissLoading();
                    }
                },
                Tools.convertBmpToFile(mActivity, thumbnail)
        );
        requestQueue.add(request);
    }

}
