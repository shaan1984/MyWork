using Microsoft.VisualStudio.TestTools.UnitTesting;
using PlaceMeOrder;

namespace PlaceMeOrder_TestIT
{
    [TestClass]
    public class PlaceMeOrder_TestIT
    {
        [TestMethod]
        public void JustReturnMyVideoSlip()
        {
            var result = ExecuteMyOrder.getType(new string[] { "video", "MyVideo" });
            Assert.AreEqual("MyVideo", result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual(1, result.Operations.Count);

        }

        [TestMethod]
        public void JustVideoLearningToSkiSlip()
        {
            var result = ExecuteMyOrder.getType(new string[] { "video", "Learning To Ski" });
            Assert.AreEqual("Learning To Ski", result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual("Your 'First Aid' video is added to the packing slip", result.Operations[1]);
            Assert.AreEqual(2, result.Operations.Count);
        }

        [TestMethod]
        public void JustReturnUpgradeSlip()
        {
            var result = ExecuteMyOrder.getType(new string[] { "Upgrade", "MyUpgrade" });
            Assert.IsNull(result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual("Apply the upgrade", result.Operations[1]);
            Assert.AreEqual("Mail is Sent successfully", result.Operations[2]);
            Assert.AreEqual(3, result.Operations.Count);

        }

        [TestMethod]
        public void JustReturnMembershipSlip()
        {
            var result = ExecuteMyOrder.getType(new string[] { "Membership", "MyMembership" });
            Assert.IsNull(result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual("Activate that membership", result.Operations[1]);
            Assert.AreEqual("Mail is Sent successfully", result.Operations[2]);
            Assert.AreEqual(3, result.Operations.Count);

        }

        [TestMethod]
        public void JustReturnBookSlip()
        {
            var result = ExecuteMyOrder.getType(new string[] { "Book", "MyBook" });
            Assert.AreEqual("MyBook", result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual("Agent Commission added", result.Operations[1]);
            Assert.AreEqual("Create a duplicate packing slip for the royalty department.", result.Operations[2]);
            Assert.AreEqual(3, result.Operations.Count);

        }

        [TestMethod]
        public void JustReturnOther()
        {
            var result = ExecuteMyOrder.getType(new string[] { "other", "Random" });
            Assert.AreEqual("Random", result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual("Agent Commission added", result.Operations[1]);
            Assert.AreEqual(2, result.Operations.Count);

            result = ExecuteMyOrder.getType(new string[] { "random", "Random" });
            Assert.AreEqual("Random", result.ItemName);
            Assert.AreEqual("Generated a packing slip for shipping.", result.Operations[0]);
            Assert.AreEqual("Agent Commission added", result.Operations[1]);
            Assert.AreEqual(2, result.Operations.Count);
        }
    }
}
