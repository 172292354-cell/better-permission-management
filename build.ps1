# ============================================================
# Better Permission Management - 一键构建脚本
# 编译并打包 mod jar 到 build/libs/ 并部署到 .minecraft/mods/
#
# 说明：
#   1. 源码中的 Minecraft API 使用 SRG(混淆) 名称（见各文件注释），
#      因为当前环境无法联网执行 Gradle reobfJar。
#   2. 依赖类库从 .minecraft 安装目录中读取。
#   3. 若 forge/minecraft 版本路径不同，请修改下方变量。
# ============================================================

$ErrorActionPreference = "Stop"

# ---------- 配置 ----------
$McDir      = "$env:APPDATA\.minecraft"
$McVersion  = "1.20.1-20230612.114412"
$ForgeVer   = "1.20.1-47.4.0"
$JavaExe    = "C:\Users\17229\.mcreator\gradle\toolchains\jdk-17.0.20.1+1\bin"
$ModId      = "better_permission_management"
$ModVersion = "1.5"
$SrcDir     = Join-Path $PSScriptRoot "src\main\java"
$ResDir     = Join-Path $PSScriptRoot "src\main\resources"
$OutDir     = Join-Path $PSScriptRoot "build\classes\java\main"
$LibsDir    = Join-Path $PSScriptRoot "build\libs"

# ---------- classpath ----------
$libs = @(
    "$McDir\libraries\net\minecraft\server\$McVersion\server-$McVersion-srg.jar",
    "$McDir\libraries\net\minecraftforge\forge\$ForgeVer\forge-$ForgeVer-universal.jar",
    "$McDir\libraries\net\minecraftforge\eventbus\6.0.5\eventbus-6.0.5.jar",
    "$McDir\libraries\net\minecraftforge\fmlcore\$ForgeVer\fmlcore-$ForgeVer.jar",
    "$McDir\libraries\net\minecraftforge\javafmllanguage\$ForgeVer\javafmllanguage-$ForgeVer.jar",
    "$McDir\libraries\net\minecraftforge\mergetool\1.1.5\mergetool-1.1.5-api.jar",
    "$McDir\libraries\org\apache\logging\log4j\log4j-api\2.19.0\log4j-api-2.19.0.jar",
    "$McDir\libraries\com\mojang\brigadier\1.1.8\brigadier-1.1.8.jar"
)
foreach ($l in $libs) {
    if (-not (Test-Path $l)) { Write-Host "缺少依赖: $l" -ForegroundColor Red; exit 1 }
}
$classpath = $libs -join ";"

# ---------- 清理 & 编译 ----------
if (Test-Path $OutDir) { Remove-Item $OutDir -Recurse -Force }
New-Item -ItemType Directory -Force -Path $OutDir, $LibsDir | Out-Null

$sources = Get-ChildItem $SrcDir -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
Write-Host "编译源码 ..."
& "$JavaExe\javac.exe" -source 17 -target 17 -d $OutDir -cp $classpath -encoding UTF-8 $sources
if ($LASTEXITCODE -ne 0) { Write-Host "编译失败" -ForegroundColor Red; exit 1 }

# ---------- 打包 jar ----------
New-Item -ItemType Directory -Force -Path "$OutDir\META-INF" | Out-Null
Set-Content -Path "$OutDir\META-INF\MANIFEST.MF" -Value "Manifest-Version: 1.0`r`nModSide: BOTH`r`n" -Encoding ASCII
$jar = Join-Path $LibsDir "$ModId-$ModVersion.jar"
& "$JavaExe\jar.exe" cfm $jar "$OutDir\META-INF\MANIFEST.MF" -C $OutDir . -C $ResDir .
Write-Host "生成 jar: $jar"

# ---------- 部署 ----------
$modsDir = "$McDir\mods"
New-Item -ItemType Directory -Force -Path $modsDir | Out-Null
Copy-Item $jar "$modsDir\$ModId-$ModVersion.jar" -Force
Write-Host "已部署到: $modsDir" -ForegroundColor Green
Write-Host "构建完成。" -ForegroundColor Green