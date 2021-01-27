using System.Web;
using System.Web.Mvc;

namespace Pokemon_4.Taldea
{
    public class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
        }
    }
}
