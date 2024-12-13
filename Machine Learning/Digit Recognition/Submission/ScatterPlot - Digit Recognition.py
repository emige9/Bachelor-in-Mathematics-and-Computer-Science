# -*- coding: utf-8 -*-
"""
Created on Mon Dec  2 11:09:30 2024

@author: Usuario
"""

import matplotlib.pyplot as plt

# Datos
modelos = ['rpart', 'Random Forest (5 árboles)', 'Random Forest (50 árboles)', 
           'Random Forest (100 árboles)', 'SVM', 'Bagging']
accuracy = [0.6333, 0.9104, 0.9632, 0.9663, 0.9748, 0.8594]
tiempo = [53.24, 24.9980, 4.4706 * 60, 16.855 * 60, 35.1554 * 60, 29.1157 * 60]  # Convertimos minutos a segundos

# Crear el gráfico
plt.figure(figsize=(10, 6))
plt.scatter(tiempo, accuracy, color='blue', s=100, label='Modelos')  # Puntos del scatter
for i, modelo in enumerate(modelos):
    plt.text(tiempo[i], accuracy[i], modelo, fontsize=10, ha='right')  # Etiquetas

# Personalizar el gráfico
plt.title('Comparación de Modelos de Machine Learning', fontsize=14)
plt.xlabel('Tiempo de Entrenamiento (segundos)', fontsize=12)
plt.ylabel('Accuracy', fontsize=12)
plt.grid(True, linestyle='--', alpha=0.6)
plt.axhline(y=max(accuracy), color='red', linestyle='--', label='Mejor Precisión')
plt.legend()
plt.tight_layout()

# Mostrar el gráfico
plt.show()
