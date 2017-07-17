package com.cniao5.cniao5play.common.exception;

import android.content.Context;

import com.cniao5.cniao5play.R;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.common.exception
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class ErrorMessageFactory {

    public static  String create(Context context, int code){




        String errorMsg = null ;


        switch (code){

            case BaseException.HTTP_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_http);

                break;

            case BaseException.SOCKET_TIMEOUT_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_socket_timeout);

                break;
            case BaseException.SOCKET_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_socket_unreachable);

                break;


            case BaseException.ERROR_HTTP_400:

                errorMsg =  context.getResources().getString(R.string.error_http_400);

                break;


            case BaseException.ERROR_HTTP_404:

                errorMsg =  context.getResources().getString(R.string.error_http_404);

                break;

            case BaseException.ERROR_HTTP_500:

                errorMsg =  context.getResources().getString(R.string.error_http_500);

                break;



            case ApiException.ERROR_API_SYSTEM:
                errorMsg = context.getResources().getString(R.string.error_system);
                break;

            case ApiException.ERROR_API_ACCOUNT_FREEZE:
                errorMsg = context.getResources().getString(R.string.error_account_freeze);
                break;


            case ApiException.ERROR_API_NO_PERMISSION:
                errorMsg = context.getResources().getString(R.string.error_api_no_perission);
                break;

            case ApiException.ERROR_API_LOGIN:
                errorMsg = context.getResources().getString(R.string.error_login);
                break;

            case ApiException.ERROR_TOKEN:
                errorMsg = context.getResources().getString(R.string.error_token);
                break;

            case NoDataException.NO_DATA:
                errorMsg = context.getResources().getString(R.string.no_data);
                break;

            default:
                errorMsg=context.getResources().getString(R.string.error_unkown);
                break;


        }


        return  errorMsg;


    }
}
