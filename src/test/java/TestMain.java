import com.pivotgroup.privotlib.Pivot;
import com.pivotgroup.privotlib.workflow.Workflow;
import org.junit.jupiter.api.Test;

public class TestMain {

    @Test
    public void main(){
        // 1.创建中枢，自动化脚本的核心
        Pivot pivot = new Pivot();

        // 2.添加工作流
        pivot.addWorkflow(new Workflow());

        // 确定当前的状态。可以由主机自由判断
        // 前提：当前内存中没有运行主程序
        // 3.执行进入游戏主体操作
        pivot.executeWorkflow("goto_game");

        // 确定当前的状态。可以由主机自由判断
        // 前提：识别到当前已经进入游戏界面，但还未登录状态。
        // 4.1.执行登录操作
        pivot.executeWorkflow("login");

        // 确定当前的状态。可以由主机自由判断
        // 前提：识别到当前已经进入游戏界面, 已经登录（自动）状态。
        // 4.2.执行开始戏操作
        pivot.executeWorkflow("start_game");

        // 检查当前状态操作可以包装到执行操作中
        // 包装到工作流中。开始的状态，和结束的状态。
        // 若没有达到期望，则直接返回状态码


    }


}
