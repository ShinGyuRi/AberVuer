package com.aber.abervuer.library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.aber.abervuer.model.SignInResult;
import com.aber.abervuer.model.User;


/**
 * Created by kimjeonghwi on 2016. 7. 14..
 */
public class WonderpleLib
{
    private static WonderpleLib sharedWonderpleLibObj;
    private SharedPreferences.Editor shardPrefEd;
    SharedPreferences sharedPref;

    SignInResult currentUser;





    public static WonderpleLib getInstance()
    {
        if(sharedWonderpleLibObj == null)
        {
            sharedWonderpleLibObj = new WonderpleLib();
        }

        return sharedWonderpleLibObj;
    }


    public void runAfterDelay(Runnable runnable, long delayTime)
    {
        Handler mHandler = new Handler();
        mHandler.postDelayed(runnable,delayTime);
    }



    /// 사용자의 데이터를 파일로 저장한다
    public void saveUserDataToFile(Context context, SignInResult signInResult)
    {

        SharedPreferences.Editor editor = getSharedPrefEdit(context);
        if(signInResult != null)
        {


            if(signInResult.user.email != null)
            {
                editor.putString("email",signInResult.user.email);
            }


            editor.commit();



        }
        else
        {
            Log.d("JIN"," func01_saveUserData --> 잘못된 API 호출입니다.");
            return ;
        }
    }


    public SignInResult loadUserDataToFile(Context context)
    {
        SignInResult makedSignInResult = new SignInResult();

        SharedPreferences pref = getSharedPref(context);

        String email = pref.getString("email","");
        String username = pref.getString("username","");
        String image = pref.getString("image","");
        String snsid = pref.getString("snsid","");
        String snstype = pref.getString("snstype","");
        String udid = pref.getString("udid","");
        String password = pref.getString("password","");



        makedSignInResult.user = new User();
        makedSignInResult.user.email = email;


        Log.d("JIN","func01_loadUserDataToFile 확인 0002 : udid --> "+udid);

        currentUser = makedSignInResult;
        return currentUser;


    }


    public SignInResult loadUserDataFromMemory()
    {
        return currentUser;
    }





    public void userLogoutRemoveUserData(Context context)
    {
        currentUser = null;
        getSharedPrefEdit(context).putString("sessionToken","");
        getSharedPrefEdit(context).commit();


    }



    public boolean isUserKeppSignInMode(Context context)
    {
        return getSharedPref(context).getBoolean("isUserKeepSignInMode",false);

    }

    public void saveIsUserKeepSignInMode(Context context, boolean isKeep)
    {
        getSharedPrefEdit(context).putBoolean("isUserKeepSignInMode",isKeep);
        getSharedPrefEdit(context).commit();

    }










    public String convertDateFromServerToApp(String dateString)
    {
        if(dateString != null && !"".equals(dateString) && dateString.contains("."))
        {
            String makedString = dateString.replace(".","/");
            return makedString;
        }

        return "";
    }





    public void showSimplDialog(Context context, String message, String buttonMessage, DialogInterface.OnClickListener btnListener)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage(message);
        alertBuilder.setNegativeButton(buttonMessage,btnListener);
        alertBuilder.setCancelable(false);
        alertBuilder.create().show();

    }


    public void showSimplSelect2Dialog(Context context, String message, String yesBtn, String noBtn, DialogInterface.OnClickListener yesBtnListener, DialogInterface.OnClickListener noBtnListener)
    {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton(yesBtn,yesBtnListener);
        alertBuilder.setNegativeButton(noBtn,noBtnListener);
        alertBuilder.setCancelable(false);
        alertBuilder.create().show();

    }












    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }











    SharedPreferences getSharedPref(Context context)
    {
        if(sharedPref == null)
        {
            sharedPref = context.getSharedPreferences("WonderpleLib" , context.MODE_PRIVATE);
        }

        return sharedPref;

    }

    SharedPreferences.Editor getSharedPrefEdit(Context context)
    {
        if(shardPrefEd == null)
        {
            shardPrefEd = context.getSharedPreferences("WonderpleLib" , context.MODE_PRIVATE).edit();
        }

        return shardPrefEd;
    }
}
