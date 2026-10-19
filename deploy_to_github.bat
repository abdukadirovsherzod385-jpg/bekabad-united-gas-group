@echo off
chcp 65001 > nul
title BEKABAD UNITED GAS GROUP MCHJ - GitHub Pages Deployer
cd /d "%~dp0"
echo ============================================================
echo   BEKABAD UNITED GAS GROUP MCHJ - GitHub Pages Deployer
echo ============================================================
echo.
echo [1/3] GitHub hisobiga ulanish...
"C:\Program Files\GitHub CLI\gh.exe" auth status >nul 2>&1
if %errorlevel% neq 0 (
    echo Iltimos, GitHub hisobingizga kiring. Brauzer orqali avtorizatsiyadan o'ting:
    "C:\Program Files\GitHub CLI\gh.exe" auth login -w -p https
)

echo.
echo [2/3] GitHub-da 'bekabad_united_gas_group_bot' repozitoriysi ochilib sayt yuklanmoqda...
"C:\Program Files\Git\cmd\git.exe" branch -M main
"C:\Program Files\GitHub CLI\gh.exe" repo create bekabad_united_gas_group_bot --public --source=. --push

echo.
echo [3/3] GitHub Pages (bepul veb-server) yoqilmoqda...
"C:\Program Files\GitHub CLI\gh.exe" api -X POST "repos/{owner}/bekabad_united_gas_group_bot/pages" -F "source[branch]=main" -F "source[path]=/" >nul 2>&1

echo.
echo ============================================================
echo   ✅ TABRIKLAYMIZ! Saytingiz GitHub Pages da ishga tushdi!
echo   Havola: https://{sizning_username}.github.io/bekabad_united_gas_group_bot/
echo ============================================================
echo.
pause
