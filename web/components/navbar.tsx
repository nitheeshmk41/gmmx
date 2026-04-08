import Link from "next/link";
import { ThemeToggle } from "@/components/theme-toggle";
import { siteConfig } from "@/lib/site";

const navItems = [
  { href: "#features", label: "Features" },
  { href: "/pricing", label: "Pricing" },
  { href: "#website", label: "Gym Website" },
  { href: "#faq", label: "FAQ" }
];

export function Navbar() {
  return (
    <header className="sticky top-0 z-40 border-b border-slate-200/70 bg-white/85 backdrop-blur-md dark:border-slate-800 dark:bg-slate-950/80">
      <div className="mx-auto flex w-full max-w-7xl items-center justify-between px-4 py-3 sm:px-6 lg:px-8">
        <Link href="/" className="flex items-center gap-2">
          <span className="grid h-9 w-9 place-items-center rounded-xl bg-gradient-to-br from-rose-400 to-rose-600 text-sm font-black text-white shadow-lg shadow-rose-400/30">
            GX
          </span>
          <div>
            <p className="text-sm font-extrabold text-slate-900 dark:text-slate-100">GMMX</p>
            <p className="text-xs text-slate-500 dark:text-slate-400">Gym Growth OS</p>
          </div>
        </Link>

        <nav className="hidden items-center gap-6 md:flex">
          {navItems.map((item) => (
            <a
              key={item.href}
              href={item.href}
              className="text-sm font-medium text-slate-600 transition hover:text-rose-500 dark:text-slate-300"
            >
              {item.label}
            </a>
          ))}
        </nav>

        <div className="flex items-center gap-2">
          <ThemeToggle />
          <a
            href={`tel:${siteConfig.phone}`}
            className="hidden rounded-xl border border-slate-300 px-3 py-2 text-sm font-semibold text-slate-700 transition hover:border-rose-400 hover:text-rose-500 dark:border-slate-700 dark:text-slate-200 sm:inline-flex"
          >
            Call sales
          </a>
          <Link
            href="/pricing"
            className="rounded-xl bg-gradient-to-r from-rose-500 to-rose-600 px-4 py-2 text-sm font-semibold text-white shadow-lg shadow-rose-500/25 transition hover:-translate-y-0.5"
          >
            Book demo
          </Link>
        </div>
      </div>
    </header>
  );
}
