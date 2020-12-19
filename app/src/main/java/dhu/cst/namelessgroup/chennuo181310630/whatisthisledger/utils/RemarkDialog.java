package dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import dhu.cst.namelessgroup.chennuo181310630.whatisthisledger.R;

public class RemarkDialog extends Dialog implements View.OnClickListener {
    EditText et;
    ImageView iv_mic;
    Button cancelBtn,ensureBtn;
    OnEnsureListener onEnsureListener;

//    语音识别相关    先提前打开麦克风权限
    private static String TAG="speech";
    private String engineType="cloud";
    private String resultType="plain";

    private StringBuffer buffer = new StringBuffer();
    private Toast mToast;
    int ret=0;
    SpeechRecognizer mIat;
    private RecognizerDialog mIatDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_remark);//对话框布局
        initView();
        initSpeechRecognition();
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buffer.setLength(0);
                et.setText(null);
                setParam();
                mIatDialog.setListener(mRecognizerDialogListener);
                mIatDialog.show();
                showTip("请开始说话");
            }
        });
    }

    private void initView() {
        et=findViewById(R.id.dialog_remark_et);
        cancelBtn=findViewById(R.id.dialog_remark_btn_cancel);
        ensureBtn=findViewById(R.id.dialog_remark_btn_ensure);
        iv_mic=findViewById(R.id.dialog_remark_iv_mic);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
        iv_mic.setOnClickListener(this);
        mToast=Toast.makeText(getContext(),"",Toast.LENGTH_SHORT);
    }

//    初始化语音识别功能
    private void initSpeechRecognition() {
        SpeechUtility.createUtility(getContext(), SpeechConstant.APPID+"=5fabd34c");
        mIat=SpeechRecognizer.createRecognizer(getContext(),mInitListener);
        mIatDialog=new RecognizerDialog(getContext(),mInitListener);
    }
//    初始化监听器。
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，请联系作者查询解决方案");
            }
        }
    };
//    设置识别的监听事件
    private RecognizerDialogListener mRecognizerDialogListener=new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            if(resultType.equals("plain")){
                buffer.append(results.getResultString());
                et.setText(buffer.toString());
                et.setSelection(et.length());
            }
        }

        @Override
        public void onError(SpeechError speechError) {

        }
    };
//    设置点击提示
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
//    设置参数
    public void setParam(){
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR,null);
        mIat.setParameter(SpeechConstant.SUBJECT,null);
        mIat.setParameter(SpeechConstant.RESULT_TYPE,resultType);
        mIat.setParameter(SpeechConstant.ENGINE_TYPE,engineType);
        mIat.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT,"mandarin");
        mIat.setParameter(SpeechConstant.VAD_BOS,"4000");
        mIat.setParameter(SpeechConstant.VAD_EOS,"1000");
        mIat.setParameter(SpeechConstant.ASR_PTT,"0");
    }

    protected void onDestroy() {
        if( null != mIat ){
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

    //设定回调接口的方法
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public RemarkDialog(@NonNull Context context) {
        super(context);
    }

    public interface OnEnsureListener{
        public void onEnsure();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_remark_btn_cancel:
                cancel();
                break;
            case R.id.dialog_remark_btn_ensure:
                if (onEnsureListener!=null) {
                    onEnsureListener.onEnsure();
                }
                break;
        }
    }

//   获取输入数据
    public String getEditText(){
        return et.getText().toString().trim();
    }
//    设置Dialog尺寸与屏幕尺寸一致
    public void setDialogSize(){
//        获取当前窗口对象(对话框)
        Window window = getWindow();
//        获取窗口参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width=(int)(d.getWidth());
        wlp.gravity= Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
        handler.sendEmptyMessageDelayed(1,100);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //延时，自动弹出软键盘
            InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };
}
