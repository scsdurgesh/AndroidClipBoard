package com.example.textviewexam;

import android.os.Bundle;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView text2,text3;
	WebView wb1;
	ClipboardManager myclip;
	ClipData data;
	Button btn1;
	
	SharedPreferences sharepref;
	public static final String copydata="data";
	ClipboardManager.OnPrimaryClipChangedListener mprimary= new OnPrimaryClipChangedListener() {
		
		@Override
		public void onPrimaryClipChanged() {
			// TODO Auto-generated method stub
			updateClipData();
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		wb1=(WebView) findViewById(R.id.webview);
		text2=(TextView) findViewById(R.id.txt_data);
		text3=(TextView) findViewById(R.id.txt_Shfdata);
		wb1.loadUrl("file:///android_asset/DK2.3.0.0.html");
		wb1.getSettings().setJavaScriptEnabled(true);
		btn1=(Button) findViewById(R.id.btn_load);
		myclip= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		myclip.addPrimaryClipChangedListener(mprimary);
		updateClipData();
		btn1.setOnClickListener(this);
		
		}
	
	


	private void updateClipData() {
		// TODO Auto-generated method stub
		try
		{
			ClipData abc=myclip.getPrimaryClip();
			Item item=abc.getItemAt(0);
			text2.setText(item.getText().toString());
			sharepref= getSharedPreferences("CopyClip",Context.MODE_PRIVATE); 
			SharedPreferences.Editor editor= sharepref.edit();
			editor.putString(copydata,""+item.getText().toString());
			editor.commit();	
			wb1.loadUrl("file:///android_asset/DK2.3.0.0.html");
		}catch(NullPointerException e)
		{
			sharepref= getSharedPreferences("CopyClip",Context.MODE_PRIVATE); 
			SharedPreferences.Editor editor= sharepref.edit();
			editor.putString(copydata," ");
			editor.commit();	
			
		}
	}




	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		sharepref= getSharedPreferences("CopyClip",Context.MODE_PRIVATE);
		String strs=sharepref.getString(copydata, "");
		text3.setText(strs);
		
		wb1.loadUrl("javascript:letSearch('"+strs+"')");
	}

}