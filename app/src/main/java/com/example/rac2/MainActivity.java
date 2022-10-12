package com.example.rac2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TableLayout tbLayout;
    public static TextView tView;
    Button bTton;
    CircleImageView circleImageView;
    int soBiMat;
    public static int score = 3;
    List<Button> list = new ArrayList<>();
    public static Dialog dialog;
    ShopAdapter shopAdapter;
    ArrayList<Shop> shopArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSoBiMat();
        showToast("Bạn có 3 lượt chọn nếu trong 3 lượt mà chọn đúng con số bí mật bạn sẽ thắng");
        init();
    }

    public void init() {

        // Đặt các biến tạo ngoài class thành findViewById
        tbLayout = findViewById(R.id.tbLayout);
        tView = findViewById(R.id.textView);
        circleImageView = findViewById(R.id.img_shop);

        // Gán giá trị cho textView = score;
        tView.setText("Lượt chơi: " + score);
        bTton = findViewById(R.id.btnOne);

        // Gán cho Button phương thức setOnClickListener và đặt vào trong phương thức biến this
        // Để khi click vào Button thì sẽ gọi đến phương thức onClick
        bTton.setOnClickListener(this);

        showShop();
        circleImageView.setOnClickListener(v -> {
            dialog.show();
        });

        // Gọi phương thức taoTableRow_Button ở dưới
        taoTableRow_Button();

    }

    RecyclerView recyclerView;

    private void showShop() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setCanceledOnTouchOutside(false);
        recyclerView = dialog.findViewById(R.id.rcv_shop_dialog);
        shopArrayList = new ArrayList<>();
        shopAdapter = new ShopAdapter(MainActivity.this, shopArrayList);
        addList();
        Log.d("ddd", shopArrayList.size() + "");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(shopAdapter);
        dialog.findViewById(R.id.exit_dialog).setOnClickListener(v -> {
            dialog.dismiss();
        });
    }

    private void addList() {
        shopArrayList.add(new Shop(5, 25000));
        shopArrayList.add(new Shop(10, 50000));
        shopArrayList.add(new Shop(15, 72000));
        shopArrayList.add(new Shop(20, 90000));
        shopArrayList.add(new Shop(25, 114999));
        shopArrayList.add(new Shop(30, 133000));
        shopArrayList.add(new Shop(35, 145000));
        shopArrayList.add(new Shop(40, 164500));
        shopArrayList.add(new Shop(45, 187000));
        shopArrayList.add(new Shop(50, 202213));
        shopArrayList.add(new Shop(55, 221833));
        shopArrayList.add(new Shop(60, 243578));
        shopArrayList.add(new Shop(65, 267321));
        shopArrayList.add(new Shop(70, 298333));
        shopArrayList.add(new Shop(75, 320485));
        shopArrayList.add(new Shop(80, 345232));
        shopArrayList.add(new Shop(85, 372183));
        shopArrayList.add(new Shop(90, 409421));
    }

    // Phương thức tạo ra các TableRow và Button
    public void taoTableRow_Button() {

        // Tạo ra 1 phương thức View.OnClickListener
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện ép kiểu cho biến kiểu View là v
                Button btn = (Button) v;

                // Phương thức này để khi click vào 1 Button nào đó thì
                // Sẽ show lên text của button đó
//                showToast(btn.getText().toString());

                // Tạo ra 1 biến int để chuyển đổi giá trị của Button thành kiểu int
                int numberValue = Integer.parseInt((String) btn.getText());

                // So sánh các số bí mật với biến int vừa tạo
                if (numberValue > soBiMat) {

                    // Nếu click vào Button mà Button đó lớn hơn số bí mật
                    // Thì sẽ vô hiệu hóa các Button lớn hơn số bí mật
                    for (int i = numberValue - 1; i < list.size(); i++) {
                        voHieuHoaButton(list.get(i));
                    }
                }
                // Nếu click vào Button mà Button đó bé hơn số bí mật
                // Thì sẽ vô hiệu hóa các Button bé hơn số bí mật
                else if (numberValue < soBiMat) {
                    for (int i = numberValue - 1; i >= 0; i--) {
                        voHieuHoaButton(list.get(i));
                    }
                }
                // Nếu click vào Button mà Button đó là số bí mật
                // Thì sẽ vô hiệu hóa tất cả các Button
                else {
                    for (int i = 0; i < list.size(); i++) {
                        voHieuHoaButton(list.get(i));
                    }
                    btn.setTextColor(Color.parseColor("#0f9ea8"));
                    Toast.makeText(MainActivity.this, "Chúc mừng bạn đã đoán đúng số bí mật là: " + soBiMat, Toast.LENGTH_SHORT).show();
                }

                // Cứ mỗi lần click vào 1 Button thì số điểm sẽ giảm đi 1
                score -= 1;

                // Gán giá trị score cho Text
                tView.setText("Lượt chơi: " + score);

                // Nếu trừ hết điểm thì gọi đến phương thức gameOver ở dưới
                if (score == 0) {
                    gameOver();

                    // Check xem số bí mật nào nằm trong list hoặc bằng giá trị của 1 Button trong list
                    // Thì cập nhật màu cho Button = số bí mật đó
                    // Hoặc Button nào chứa số bí mật đó
                    list.get(soBiMat - 1).setTextColor(Color.parseColor("#0f9ea8"));

                    // Nếu hết điểm mà lần click cuối cùng là Button số bí mật thì sẽ hiển thị số điểm
                    if (numberValue == soBiMat) {
                        tView.setText("Lượt chơi: " + score);
                    }
                }
            }
        };

        // Dùng vòng lặp for để tạo ra các Button và TableRow

        // Vòng lặp for này dùng để tạo các TableRow
        for (int i = 1; i <= 8; i++) {
            // Tạo ra 1 biến kiểu TableRow và truyền vào this
            TableRow tbRow = new TableRow(this);

            // Vòng lặp này dùng để tạo ra các Button
            for (int j = 1; j <= 5; j++) {
                // Tạo ra 1 biến kiểu Button và truyền vào this
                Button btnTwo = new Button(this);

                // Add các Button vào TableRow vừa tạo bằng phương thức addView
                tbRow.addView(btnTwo);

                // Gán giá trị cho các Buton vừa tạo
                // Công thức ( số Button * (i-1)+j )
                btnTwo.setText(5 * (i - 1) + j + "");

                // Set màu cho các giá trị vừa set vào Button
                // Bằng phương thức setTextColor.(Color.parseColor( mã màu ));
                btnTwo.setTextColor(Color.parseColor("#FF66FF"));

                // Đặt Button bằng kiểu setOnClickListener
                // Và truyền vào tên của phương thức View.OnClickListenr đã tạo bên trên
                // Để khi click vào từng Button thì sẽ gọi đến phương thức View.OnClickListenr
                btnTwo.setOnClickListener(btnClick);

//                int button = Integer.parseInt((String) btnTwo.getText());

                // Add tất cả Button vừa tạo vào list
                list.add(btnTwo);
            }

            // Thêm các TableRow vừa tạo bằng vòng lặp for
            // Vào TableLayout bằng phương thức addView
            tbLayout.addView(tbRow);
        }
    }

    // Phương thức để các số bí mật được sinh ra ngẫu nhiên
    public void setSoBiMat() {

        // Gọi phương thức Random để phát sinh ngẫu nhiên các số
        Random random = new Random();

        // Số  bí mật sẽ chạy từ 1 -> 40
        soBiMat = 1 + random.nextInt(40 - 1 + 1);
        Log.d("dapan", soBiMat + "");
    }

    // Phương thức để hiện thị các chuỗi được gán vào Toast
    public void showToast(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    // Phương thức onClick được implements từ class View.OnClickListener
    // Khi click vào Button New Game thì sẽ gọi đến phương thức này
    @Override
    public void onClick(View v) {
        // Gọi đến phương thức newGame đã tạo ở dưới
        newGame();
    }

    // Phương thức dùng để thực hiện các chức năng khi click vào Button New Game
    public void newGame() {
        bTton.setText("Play Again");
        // Gọi đến phương thức setSoBiMat ở trên để khi click vào Button New game
        // Thì các số bí mật sẽ được phát sinh ngẫu nhiên lại
        setSoBiMat();

        // Đặt số điểm bằng 3
        score = 3;

        // Gán giá trị cho TextView bằng score
        tView.setText("Lượt chơi: " + score);

        // Dùng vòng lặp hoặc phương thức Iterator để khi click vào New Game
        // Thì các Button và màu của các giá trị Button sẽ được làm mới lại

        // CÁCH 1:
        // Dùng vòng lặp for để cập nhật lại màu cho tất cả các button

//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).setTextColor(Color.parseColor("#E8B7D4"));
//        }

        //CÁCH 2:
        // Dùng phương thức Iterator để cập nhật lại màu cho các button

        // Tạo ra 1 phương thức Iterator và truyền vào biến là Button
        // Vì phương thức này sẽ làm mới lại màu và các Button
        Iterator<Button> itr = list.iterator();

        // Tạo 1 vòng lặp while và truyền vào giá trị là itr
        // Đặt cho itr 1 biến là hasNext() để kiểm tra
        // Nếu đúng thì sẽ chạy các Code trong vòng while
        while (itr.hasNext()) {

            // Tạo 1 biến kiểu Button và cho biến này bằng itr.next()
            Button btn = itr.next();

            // Gọi ra biến Button vừa tạo và set lại màu cho các giá trị của Button
            btn.setTextColor(Color.parseColor("#FF66FF"));

            // Khi làm mới lại các Button thì các Button sẽ được mở khóa click
            // Bằng phương thức setClickable và truyền vào giá trị là true
            // Để mở khóa click cho các Button
            btn.setClickable(true);
        }

        showToast("Bạn có 3 lượt chọn nếu trong 3 lượt mà chọn đúng con số bí mật bạn sẽ thắng");
    }

    // Phương thức này được dùng để vô hiệu hóa các Button
    public void voHieuHoaButton(Button btn) {
        // Bằng phương thức setClickable và truyền vào giá trị là false
        // Để vô hiệu hóa click các Button
        btn.setClickable(false);

        // Khi vô hiệu hóa các Button thì sẽ đặt lại màu cho các giá trị trong Button
        btn.setTextColor(Color.parseColor("#CCCCCC"));
    }

    // Phương thức này dùng để vô hiệu hóa các Button khi hết điểm
    public void gameOver() {
        tView.setText("Game Over");
        for (int i = 0; i < list.size(); i++) {
            voHieuHoaButton(list.get(i));
        }
        Log.d("sizeL", list.size() + "");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}