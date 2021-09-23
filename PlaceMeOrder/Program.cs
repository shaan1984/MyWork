using System;

namespace PlaceMeOrder
{
    partial class MyProg
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Please enter product type (Only Video, Book, Membership, Upgrade, Others) and name as applicable seperated by Colon(:)");
            var typ = Console.ReadLine().Split(':');
            var display = ExecuteMyOrder.getType(typ);
            Console.WriteLine("Item Name : {0} Operations : {1}", display.ItemName, string.Join(' ', display.Operations));
            Console.ReadLine();
        }
    }
}
