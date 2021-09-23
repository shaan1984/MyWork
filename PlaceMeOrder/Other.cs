using System;
using System.Collections.Generic;

namespace PlaceMeOrder
{
    class Other : ActualProduct
    {        public Other(string name)
        {
            ItemName = name;
            Operations = new List<string>();
            base.GetMySlip();
            base.AgentComm();
        }
    }
    class Membership : AbstractProduct
    {
        public Membership()
        {
            Operations = new List<string>();
            base.GetMySlip();
            Operations.Add("Activate that membership");
            Console.WriteLine("Activate that membership");
            base.DropMail();
        }
    }
    class Book : ActualProduct
    {
        public Book(string itemName)
        {
            ItemName = itemName;
            Operations = new List<string>();
            base.GetMySlip();
            base.AgentComm();
            GetMySlip();
        }
        public override void GetMySlip()
        {
            Operations.Add("Create a duplicate packing slip for the royalty department.");
            Console.WriteLine("Create a duplicate packing slip for the royalty department.");
        }
    }
    class Upgrade : AbstractProduct
    {
        public Upgrade()
        {
            Operations = new List<string>();
            base.GetMySlip();
            Operations.Add("Apply the upgrade");
            Console.WriteLine("Apply the upgrade");
            base.DropMail();
        }
    }
    class Video : AbstractProduct
    {
        public Video(string videoName)
        {
            Operations = new List<string>();
            ItemName = videoName;

            GetMySlip();
        }
        public override void GetMySlip()
        {
            base.GetMySlip();
            if (ItemName.ToLower().Equals("learning to ski"))
            {
                Operations.Add("Your 'First Aid' video is added to the packing slip");
                Console.WriteLine("Your 'First Aid' video is added to the packing slip");
            }
        }
    }
}
