package com.campandroid.sildemenutest;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;

// asset에서 파일을 읽는다.
public class PageReader {
    static public  String readPage(Context ctx, String sPageFile) throws IOException {
	    
    	InputStream is = ctx.getAssets().open(sPageFile);
		int size = is.available();
		byte[] buffer = new byte[size];
		is.read(buffer);
		is.close();
		
		// 파일 인코딩이 어떤 상태인지 반드시 알아야 합니다.
		// 설정안하면 괴상한 문자를 만나게 될 것입니다.
		String sText = new String(buffer, "euc-kr");
		
		return sText;
	}
}
