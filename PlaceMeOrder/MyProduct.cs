using System;
using System.Collections.Generic;

namespace PlaceMeOrder
{
    public abstract class MyProduct
    {
        public string ItemName { get; set; }
        public List<string> Operations { get; set; }
        public abstract void GetMySlip();
    }

    public abstract class ActualProduct : MyProduct
    {
        public override void GetMySlip()
        {
            Operations.Add("Generated a packing slip for shipping.");
            Console.WriteLine("Generated a packing slip for shipping.");
        }
        public virtual void AgentComm()
        {
            Operations.Add("Agent Commission added");
            Console.WriteLine("Agent Commission added");
        }
    }
    public abstract class AbstractProduct : MyProduct
    {
        public override void GetMySlip()
        {
            Operations.Add("Generated a packing slip for shipping.");
            Console.WriteLine("Generated a packing slip for shipping.");
        }
        public virtual void DropMail()
        {
            Operations.Add("Mail is Sent successfully");
            Console.WriteLine("Mail is Sent successfully");
        }

    }
}
