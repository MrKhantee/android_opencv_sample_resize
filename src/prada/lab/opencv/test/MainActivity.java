package prada.lab.opencv.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.androidquery.AQuery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends Activity {
	static {
    	System.loadLibrary("opencv_java");
    }
	
	private AQuery aq;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img);
		this.aq = new AQuery(this);
		aq.id(R.id.btn_taken).clicked(this, "scalingImage");
	}
    
	 
	private void procImg(int imageId,int alg, Mat srcMat,Mat dstMat){
		Imgproc.resize(srcMat, dstMat, new org.opencv.core.Size(srcMat.width()*2,srcMat.height()*2)
			, 2,2, alg );
		Bitmap dstBitmap = Bitmap.createBitmap(dstMat.width(), dstMat.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(dstMat, dstBitmap);
		aq.id(imageId).image(dstBitmap);
	 }
	
	public void scalingImage(View view){
		try {
			Bitmap bitmap = BitmapFactory.decodeStream(this.getAssets().open("test_opencv.png"));
			Mat srcMat = new Mat();
			Mat dstMat = new Mat();
			Utils.bitmapToMat(bitmap,srcMat);
		
			procImg(R.id.img_snapshot0,Imgproc.INTER_NEAREST ,srcMat,dstMat);
			procImg(R.id.img_snapshot1,Imgproc.INTER_LINEAR,srcMat,dstMat);
			procImg(R.id.img_snapshot2,Imgproc.INTER_AREA ,srcMat,dstMat);
			procImg(R.id.img_snapshot3,Imgproc.INTER_CUBIC,srcMat,dstMat);
			procImg(R.id.img_snapshot4,Imgproc.INTER_LANCZOS4,srcMat,dstMat);
			
			
			// test DFT alg 
			/*
			  List<Mat> planes = new ArrayList<Mat>() ;
			    Mat padded=new Mat();
			    //Imgproc.copyMakeBorder(input, padded, 0, M-input.rows(), 0, N-input.cols(), Imgproc.BORDER_CONSTANT);
			    planes.add(padded);
			    Mat complexMat = new Mat();
			   Core.merge(planes, complexMat);
			dstMat.convertTo(dstMat, CvType.CV_64F);
			srcMat.convertTo(srcMat, CvType.CV_64F);
			Log.d("test", " srcMat type" + srcMat.type());
			//Core.dft(srcMat, dstMat);
			Core.dft(srcMat, complexMat,0,srcMat.rows());
			//dm2.convertTo(dm2, CvType.)
			Bitmap dstBitmap = Bitmap.createBitmap(dstMat.width(), dstMat.height(), Bitmap.Config.ARGB_8888);
			Utils.matToBitmap(dstMat, dstBitmap);
			aq.id(R.id.img_snapshot4).image(dstBitmap);
			 */
			/*
			Mat mGray = new Mat();
			Imgproc.cvtColor(srcMat, mGray, Imgproc.COLOR_RGBA2GRAY);
			Imgproc.Sobel(mGray, dstMat, mGray.depth(), 1,1);
			
			
			Bitmap dstBitmap = Bitmap.createBitmap(dstMat.width(), dstMat.height(), Bitmap.Config.ARGB_8888);
			Utils.matToBitmap(dstMat, dstBitmap);
			aq.id(R.id.img_snapshot0).image(dstBitmap);
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
