# PivotLib 路线图

> 更新时间：2026-11-10。每次改动的前提是 `gradlew test` 全绿。

## 一、现在有什么（已验证）

| 层 | 能力 | 位置 |
|---|---|---|
| 输入 | Pointer、InputDevice/InputButton、Tap/DoubleTap/Swipe/Hold | `input`、`input.gesture` |
| 识别 | TemplateLocator（ROI / 钉死尺度 / 复用帧 / exists）、MatMatchUtils（1.0 先探、阈值门控） | `vision`、`util` |
| 流程 | Workflow、Step、ActionStep、WaitStep、WaitForStep、WorkflowContext | `workflow` |
| 截图 | Capturer、ScreenCapturer、Frame（帧 + 时间戳 + 序号） | `capture` |
| 入口 | Pivot（createWorkflow、grabFrame） | `pivotlib` |

实测成本（1707×1067 屏幕）：

| 场景 | 耗时 |
|---|---|
| 整屏抓帧 | 70 ms |
| 整屏单尺度匹配 | 128 ms |
| 1080×720 抓帧 + 匹配 | 75 ms |
| ROI 300×300 抓帧 + 匹配 | 约 9 ms |

## 二、定下来的三条原则

1. **ROI 优先**。能圈小就圈小，抓帧和匹配都按面积下降，4 倍级收益。
2. **失败驱动升级**。常驻判定便宜（每步 2~10ms），失败立刻升级一次全屏扫描；不靠固定间隔巡检，因为定时扫描有盲区。
3. **不开独立监控线程**。检测已降到 10ms 级，主循环按 ≤100ms 切片、切片之间检查一次即可，比多一条线程加跨线程事件便宜一个数量级。

## 三、四阶段

### 阶段 1：底座（已完成）

- `Frame` 带时间戳和序号，一个 tick 内多处识别共用
- `Capturer.grab()/grab(Rect)`、`Pivot.grabFrame()`
- `TemplateLocator.exists()/exists(Mat)`：低于阈值一律 false，不给坐标
- `WaitForStep`：等目标出现，超时判失败并带上名字
- `Hold`：按住不放，按 100ms 切片，可中断，中断必松手

验收：`StepBehaviorTest` 7 个用例全过，`WorkflowTest` 2 个用例全过。

### 阶段 2：让脚本能"看局面"（下一步）

- `Scene`：名字 + 一组检测器 + 进入/退出动作
- `Detector`：ROI + 模板 + 阈值，只回答"在不在"
- `Pivot.tick()`：取帧（≤100ms 才重抓）→ 跑检测 → 有事件先处理并回到 tick 顶部 → 无事件则执行一个 ≤100ms 的动作切片
- 边沿触发：同一个弹窗连续命中只算一次，否则处理器会被反复触发

验收：弹窗出现时能在 300ms 内打断当前动作并回到原流程；处理完不重复触发。

### 阶段 3：失败恢复与分支

- `ActionStep.OnFailure` 增加 `RETRY(n, intervalMs)`：识别类动作失败太常见
- 具名块跳转（不要按索引），支持"结算后重新开一局"的循环
- 验收：任一步失败 → 重试 → 仍失败则升级全屏扫描 → 命中弹窗就处理 → 恢复原流程

### 阶段 4：迁移与清理

- `ZmxyAutoLevel` 切到新 API：`Hold` 替代 `Tap(...,5000)`、`exists` 判胜负、ROI 收窄、`try-with-resources` 释放模板
- 删除死代码：`MatchInfo`、`Pair`、`TapType`、`BiExecutor`、`Executor`
- 验收：整局挂机跑通，连续跑 30 分钟无泄漏

## 四、暂不做

- 独立监控线程：等出现"单次检查 > tick 预算一半"或"需要 >5Hz 连续判定"再谈
- 全面转 C++：匹配本来就在 native 层，换语言只省抓帧的固定成本，ROI 越小越不划算
- 窗口区域截图（`WindowCapturer`）：工作量最大，等窗口化场景真的需要时再做
