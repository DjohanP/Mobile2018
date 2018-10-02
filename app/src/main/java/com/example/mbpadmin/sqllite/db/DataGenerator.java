package com.example.mbpadmin.sqllite.db;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    private static final String[] CAPTION = new String[]{
            "1. Normal Tegak Lurus Kamera",
            "2. Normal tegak tegak lurus kamera tidak berkacamata",
            "3. Tersenyum tegak lurus kamera",
            "4. Sedih tegak lurus kamera",
            "5. Mengantuk tegak lurus kamera",
            "6. Normal menoleh ke kanan 30 derajat",
            "7. Normal menoleh ke kanan 30 derajat tidak berkacamata",
            "8. Tersenyum menoleh ke kanan 30 derajat",
            "9. Sedih menoleh ke kanan 30 derajat",
            "10. Mengantuk menoleh ke kanan 30 derajat",
            "11. Normal menoleh ke kiri 30 derajat",
            "12. Normal menoleh ke kiri 30 derajat tidak berkacamata",
            "13. Tersenyum menoleh ke kiri 30 derajat",
            "14. Sedih menoleh ke kiri 30 derajat",
            "15. Mengantuk menoleh ke kiri 30 derajat",
            "16. Normal tegak lurus kamera muka basah",
            "17. Normal tegak lurus kamera tidak berkacamata muka basah",
            "18. Tersenyum tegak lurus kamera muka basah",
            "19. Sedih tegak lurus kamera muka basah.",
            "20. Mengantuk tegak lurus kamera muka basah",
            "21. Normal menoleh ke kanan 30 derajat muka basah",
            "22. Normal menoleh ke kanan 30 derajat tidak berkacamata muka basah",
            "23. Tersenyum menoleh ke kanan 30 derajat muka basah",
            "24. Sedih menoleh ke kanan 30 derajat muka basah",
            "25. Mengantuk menoleh ke kanan 30 derajat muka basah",
            "26. Normal menoleh ke kiri 30 derajat muka basah",
            "27. Normal menoleh ke kiri 30 derajat tidak berkacamata muka basah",
            "28. Tersenyum menoleh ke kiri 30 derajat muka basah",
            "29. Sedih menoleh ke kiri 30 derajat muka basah",
            "30. Mengantuk menoleh ke kiri 30 derajat muka basah",};

    public static List<FotooEntity> generateProducts()
    {
        List<FotooEntity> fotos=new ArrayList<>(30);
        for(int i=0;i<CAPTION.length;i++)
        {
            FotooEntity fotooEntity=new FotooEntity(CAPTION[i],"0","0");
            fotos.add(fotooEntity);
        }
        return fotos;
    }
}
