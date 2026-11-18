
import com.mycompany.examination2.ProductCalculations;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductCalculationsTest {

    // UNIT Test 1 (VAT calculation)
    @Test
    void GetVat_WithValidData_ReturnsCorrectValue() {

        ProductCalculations calculations = new ProductCalculations(3500.00);

        assertEquals(525.00,calculations.GetVat(),0.001);
    }


    // UNIT Test 2 (Total calculation)
    @Test
    void GetTotal_WithValidData_ReturnsCorrectValue() {

        ProductCalculations calculations = new ProductCalculations(3500.00);

        assertEquals(4200.00,calculations.GetTotal(),0.001);
    }
}
