using System;
using static PlaceMeOrder.MyProg;

namespace PlaceMeOrder
{
    public class ExecuteMyOrder
    {
        public static MyProduct getType(string[] typ)
        {
            Products type;

            try
            {
                type = (Products)Enum.Parse(typeof(Products), typ[0], ignoreCase: true);
            }
            catch (Exception e)
            {
                type = Products.Other;
            }

            MyProduct prd = null;

            string name = typ.Length > 1 ? string.Join(';', typ, 1, typ.Length - 1) : string.Empty;

            if (type == Products.Membership)
                prd = new Membership();
            if (type == Products.Upgrade)
                prd = new Upgrade();
            if (type == Products.Video)
                prd = new Video(name);
            if (type == Products.Book)
                prd = new Book(name);
            else if (type == Products.Other)
                prd = new Other(name);

            return prd;
        }
    }
}
