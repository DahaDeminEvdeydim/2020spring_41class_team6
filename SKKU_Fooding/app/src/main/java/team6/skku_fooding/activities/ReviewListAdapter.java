package team6.skku_fooding.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends ArrayAdapter<Review> {

    private Context context;
    private ArrayList<Review> revlist=new ArrayList<>() ;


    public ReviewListAdapter( Context context, ArrayList<Review>mReviewlist){

        super(context,0,mReviewlist);
        this.context=context;
        this.revlist=mReviewlist;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        System.out.println("llllllllllllllllll");
        View v=convertView;
        Review review=getItem(position);
            v = LayoutInflater.from(context).inflate(R.layout.reviewlist,parent,false);


            TextView userid = (TextView) v.findViewById(R.id.userid);
            TextView description = (TextView) v.findViewById(R.id.description);
            TextView modifiedDate = (TextView) v.findViewById(R.id.modifieddate);
            TextView title = (TextView) v.findViewById(R.id.title);
            TextView score = (TextView) v.findViewById(R.id.score);
            System.out.println(review.title);
            userid.setText(review.reviewId);
            description.setText(review.description);
            modifiedDate.setText(review.modifiedDate.toString());
            title.setText(review.title);
            score.setText(review.score);


        return  v;
    }
}
