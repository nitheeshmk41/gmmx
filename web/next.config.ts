import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  output: "standalone",
  experimental: {
    typedRoutes: true
  },
  images: {
    formats: ["image/avif", "image/webp"]
  }
};

export default nextConfig;
