import com.pivotgroup.pivotlib.core.Pivot;
import com.pivotgroup.pivotlib.infrastructure.action.AddCalculationAction;
import org.junit.jupiter.api.Test;

/**
 *
 *
 * @author xinliu
 * @email 3536370493@qq.com
 */

public class PivotTest {

    @Test
    public void test() {
        Pivot pivot = new Pivot();
        pivot.addAction(new AddCalculationAction(1, 1));

        pivot.startActions();
    }

}
