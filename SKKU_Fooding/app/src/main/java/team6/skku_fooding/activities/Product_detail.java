package team6.skku_fooding.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;


public class Product_detail extends AppCompatActivity {
TextView company,name,ingredient,price,uploadeddate;
TextView specificavg,generalavg;
//ImageView image;
int specificaverage;
int generalaverage;
int countspecific;
int countgeneral;

DatabaseReference reff;
    String productid;
    String userid;
    int categoryid;
    ArrayList<Review> rews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetail);
        FirebaseApp.initializeApp(this);

        //The previous intent need to give below info
        //then manually written numbers in if statement need to be changed according to that
        //Intent myIntent = getIntent(); // gets the previously created intent
        //productid = myIntent.getStringExtra("productid");
        //userid= myIntent.getStringExtra("userid");
        //categoryid= Integer.parseInt(myIntent.getStringExtra("categoryid"));


        company=findViewById(R.id.company);
        //image=findViewById(R.id.image);
        name=findViewById(R.id.name);
        ingredient=findViewById(R.id.ingredient);
        price= findViewById(R.id.price);
        uploadeddate=findViewById(R.id.uploaded_date);
        reff= FirebaseDatabase.getInstance().getReference().child("product").child("200");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String comp=dataSnapshot.child("company").getValue().toString();
                // Image img=dataSnapshot.child("image").getValue()
                String ing=dataSnapshot.child("ingredient").getValue().toString();
                String nm=dataSnapshot.child("name").getValue().toString();
                String prc=dataSnapshot.child("price").getValue().toString();
                String up_date=dataSnapshot.child("uploaded_date").getValue().toString();
                company.setText(comp);
                name.setText(nm);
                ingredient.setText(ing);
                price.setText(prc);
                uploadeddate.setText(up_date);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      reviews();


    }


    public void reviews(){
        System.out.println("baslingic");
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reviewsRef = rootRef.child("review");
        System.out.println("devam");

        reviewsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("girdik");
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Review forproductreview=new Review();
                    //Reviews going tobe set here and then add to list
                    int ratespecific=0;
                    int rategeneral=0;

                    if(Integer.parseInt(ds.child("category_id").getValue().toString())==2 && Integer.parseInt(ds.child("product_id").getValue().toString())==250){
                        System.out.println("varmis");
                        forproductreview.reviewId=Integer.parseInt(ds.child("review_id").getValue().toString());
                        System.out.println(Integer.parseInt(ds.child("review_id").getValue().toString()));
                        forproductreview.score=Integer.parseInt(ds.child("rate").getValue().toString());
                        forproductreview.modifiedDate=(Date)(ds.child("modifieddate").getValue());
                        forproductreview.title=ds.child("title").getValue().toString();
                        forproductreview.description=ds.child("description") .getValue().toString();

                        ratespecific=Integer.parseInt(ds.child("rate").getValue().toString());
                        specificaverage=specificaverage+ratespecific;
                        rews.add(forproductreview);

                        System.out.println(rews);
                        countspecific++;
                    }
                    if(Integer.parseInt(ds.child("product_id").getValue().toString())==250) {
                        rategeneral = Integer.parseInt(ds.child("rate").getValue().toString());
                        generalaverage = generalaverage + rategeneral;
                        countgeneral++;
                    }


                    System.out.println("bakbakalim");
                }
                calculations(rews);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void order(View view){
        //The one who have the activity of order will fill here.
       Intent ordernow = new Intent();
       startActivity(ordernow);


    }

    public void shoppingcart(View view){

    }

    public void calculations(ArrayList<Review>reviewlist){
        System.out.println(specificaverage);
        System.out.println(generalaverage);
        System.out.println(countgeneral);
        System.out.println(countspecific);
        specificavg=findViewById(R.id.s);
        generalavg=findViewById(R.id.g);
        generalavg.setText(String.valueOf(generalaverage/countgeneral));
        specificavg.setText(String.valueOf(specificaverage/countspecific));
        generalaverage=0;
        specificaverage=0;
        countgeneral=0;
        countspecific=0;
        System.out.println("yazdirma");
        System.out.println(reviewlist);
        ListView mListView=(ListView) findViewById(R.id.listview);

        ReviewListAdapter adapter = new ReviewListAdapter(this,reviewlist);
        mListView.setAdapter(adapter);
        System.out.println("ppppppp");
        rews.clear();
    }



}
